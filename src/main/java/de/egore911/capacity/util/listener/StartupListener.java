/*
 * Copyright 2013  Christoph Brill <egore911@gmail.com>
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package de.egore911.capacity.util.listener;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.sql.DataSource;

import org.flywaydb.core.Flyway;
import org.joda.time.DateTime;
import org.joda.time.Hours;
import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
import org.joda.time.LocalTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.bridge.SLF4JBridgeHandler;

import de.egore911.capacity.persistence.dao.EmployeeDao;
import de.egore911.capacity.persistence.model.AbsenceEntity;
import de.egore911.capacity.persistence.model.ContractEntity;
import de.egore911.capacity.persistence.model.EmployeeEpisodeEntity;
import de.egore911.capacity.persistence.model.EpisodeEntity;
import de.egore911.capacity.persistence.model.WorkingHoursEntity;
import de.egore911.capacity.ui.dto.Absence;
import de.egore911.capacity.ui.dto.Contract;
import de.egore911.capacity.ui.dto.Episode;
import ma.glasnost.orika.CustomMapper;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.MappingContext;
import ma.glasnost.orika.converter.builtin.PassThroughConverter;
import ma.glasnost.orika.impl.DefaultMapperFactory;

/**
 * Listener executed during startup, responsible for setting up the
 * java.util.Logging->SLF4J bridge and updating the database.
 *
 * @author Christoph Brill &lt;egore911@gmail.com&gt;
 */
public class StartupListener implements ServletContextListener {

	private static final Logger log = LoggerFactory.getLogger(StartupListener.class);

	public static ScheduledExecutorService SCHEDULE_EXECUTOR;
	public static ExecutorService EXECUTOR;
	public static final MapperFactory MAPPER_FACTORY = new DefaultMapperFactory.Builder().build();

	static {
		MAPPER_FACTORY
			.classMap(ContractEntity.class, Contract.class)
			.byDefault()
			.customize(
				new CustomMapper<ContractEntity, Contract>() {
					@Override
					public void mapAtoB(ContractEntity a, Contract b, MappingContext context) {
						if (a.getWorkingHours() != null) {
							int workingHoursPerWeek = 0;
							for (WorkingHoursEntity workingHoursEntity : a.getWorkingHours()) {
								workingHoursPerWeek += Hours.hoursBetween(workingHoursEntity.getStart(), workingHoursEntity.getEnd()).getHours();
							}
							b.setWorkingHoursPerWeek(workingHoursPerWeek);
						}
					}
				})
			.register();

		MAPPER_FACTORY
			.classMap(Episode.class, EpisodeEntity.class)
			.byDefault()
			.customize(
				new CustomMapper<Episode, EpisodeEntity>() {
					@Override
					public void mapAtoB(Episode a, EpisodeEntity b, MappingContext context) {
						if (b.getEmployeeEpisodes() != null) {
							for (EmployeeEpisodeEntity employeeEpisodeEntity : b.getEmployeeEpisodes()) {
								employeeEpisodeEntity.setEpisode(b);
							}
						}
					}
				})
			.register();

		MAPPER_FACTORY
			.classMap(Absence.class, AbsenceEntity.class)
			.byDefault()
			.customize(
				new CustomMapper<Absence, AbsenceEntity>() {
					@Override
					public void mapAtoB(Absence a, AbsenceEntity b, MappingContext context) {
						b.setEmployee(new EmployeeDao().findById(a.getEmployeeId()));
					}
					@Override
					public void mapBtoA(AbsenceEntity a, Absence b, MappingContext context) {
						b.setEmployeeId(a.getEmployee().getId());
					}
				})
			.register();

		MAPPER_FACTORY
			.getConverterFactory()
			.registerConverter(new PassThroughConverter(DateTime.class));

		MAPPER_FACTORY
			.getConverterFactory()
			.registerConverter(new PassThroughConverter(LocalDate.class));

		MAPPER_FACTORY
			.getConverterFactory()
			.registerConverter(new PassThroughConverter(LocalTime.class));

		MAPPER_FACTORY
			.getConverterFactory()
			.registerConverter(new PassThroughConverter(LocalDateTime.class));
	}

	@Override
	public void contextInitialized(ServletContextEvent sce) {

		SLF4JBridgeHandler.removeHandlersForRootLogger();
		SLF4JBridgeHandler.install();

		try {
			InitialContext initialContext = new InitialContext();
			DataSource dataSource = (DataSource) initialContext.lookup("java:/comp/env/jdbc/capacityDS");
			Flyway flyway = new Flyway();
			flyway.setDataSource(dataSource);

			switch (dataSource.getClass().getName()) {
			case "org.hsqldb.jdbc.JDBCDataSource":
				// Plain datasource for HSQLDB
				flyway.setLocations("db/migration/hsqldb");
				break;
			case "com.mysql.jdbc.jdbc2.optional.MysqlDataSource":
			case "com.mysql.jdbc.jdbc2.optional.MysqlXADataSource":
				// Plain datasource for MySQL/MariaDB
				flyway.setLocations("db/migration/mysql");
				break;
			case "net.sourceforge.jtds.jdbcx.JtdsDataSource":
			case "com.microsoft.sqlserver.jdbc.SQLServerDatabaseMetaData":
				// Plain datasource for MSSQL
				flyway.setLocations("db/migration/mssql");
				break;
			case "org.postgresql.jdbc2.optional.PoolingDataSource":
			case "org.postgresql.jdbc2.optional.SimpleDataSource":
			case "org.postgresql.ds.PGPoolingDataSource":
			case "org.postgresql.ds.PGSimpleDataSource":
				flyway.setLocations("db/migration/pgsql");
				break;
			case "org.apache.tomcat.dbcp.dbcp.BasicDataSource":
				// Wrapped by Tomcat, get a connection to identify it
				try (Connection connection = dataSource.getConnection()) {
					if (connection.toString().startsWith("jdbc:mysql://")) {
						flyway.setLocations("db/migration/mysql");
					} else if (connection.toString().startsWith("jdbc:hsqldb")) {
						flyway.setLocations("db/migration/hsqldb");
					} else if (connection.toString().startsWith("jdbc:jtds:sqlserver")
							|| connection.toString().startsWith("jdbc:sqlserver")) {
						flyway.setLocations("db/migration/mssql");
					} else if (connection.toString().startsWith("jdbc:postgresql")) {
						flyway.setLocations("db/migration/pgsql");
					} else {
						throw new RuntimeException(
								"Unsupported database detected, please report this: " + connection.toString());
					}
				} catch (SQLException e) {
					log.error("Error opening connection :{}", e.getMessage(), e);
					return;
				}
				break;
			case "org.apache.tomcat.dbcp.dbcp2.BasicDataSource":
				// Wrapped by Tomcat, get a connection to identify it
				try (Connection connection = dataSource.getConnection()) {
					if (connection.toString().contains("URL=jdbc:mysql://")) {
						flyway.setLocations("db/migration/mysql");
					} else if (connection.toString().contains("URL=jdbc:hsqldb")) {
						flyway.setLocations("db/migration/hsqldb");
					} else if (connection.toString().contains("URL=jdbc:jtds:sqlserver")
							|| connection.toString().contains("URL=jdbc:sqlserver")) {
						flyway.setLocations("db/migration/mssql");
					} else if (connection.toString().contains("URL=jdbc:postgresql")) {
						flyway.setLocations("db/migration/pgsql");
					} else {
						throw new RuntimeException(
								"Unsupported database detected, please report this: " + connection.toString());
					}
				} catch (SQLException e) {
					log.error("Error opening connection :{}", e.getMessage(), e);
					return;
				}
				break;
			case "org.jboss.jca.adapters.jdbc.WrapperDataSource":
				// Wrapped by JBoss
				try (Connection connection = dataSource.getConnection()) {
					if (connection.getMetaData().getClass().getName().startsWith("com.mysql.jdbc.")) {
						flyway.setLocations("db/migration/mysql");
					} else if (connection.getMetaData().getClass().getName()
							.equals("org.hsqldb.jdbc.JDBCDatabaseMetaData")) {
						flyway.setLocations("db/migration/hsqldb");
					} else if (connection.getMetaData().getClass().getName()
							.equals("net.sourceforge.jtds.jdbc.JtdsDatabaseMetaData")
							|| connection.getMetaData().getClass().getName()
									.equals("com.microsoft.sqlserver.jdbc.SQLServerDatabaseMetaData")) {
						flyway.setLocations("db/migration/mssql");
					} else {
						throw new RuntimeException("Unsupported database detected, please report this: "
								+ connection.getMetaData().getClass().getName());
					}
				} catch (SQLException e) {
					log.error("Error opening connection :{}", e.getMessage(), e);
					return;
				}

				if (flyway.getLocations().length == 0) {
					throw new RuntimeException(
							"Unsupported database detected, please report this: " + dataSource.getClass().getName());
				}

				break;
			default:
				throw new RuntimeException(
						"Unsupported database detected, please report this: " + dataSource.getClass().getName());
			}

			flyway.migrate();
		} catch (NamingException e) {
			log.error(e.getMessage(), e);
		}

		SCHEDULE_EXECUTOR = Executors.newSingleThreadScheduledExecutor();
		EXECUTOR = Executors.newSingleThreadExecutor();
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		if (SCHEDULE_EXECUTOR != null) {
			SCHEDULE_EXECUTOR.shutdown();
		}
		if (EXECUTOR != null) {
			EXECUTOR.shutdown();
		}
	}

}
