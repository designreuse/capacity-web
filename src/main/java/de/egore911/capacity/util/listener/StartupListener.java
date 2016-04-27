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

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.sql.DataSource;

import org.flywaydb.core.Flyway;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.bridge.SLF4JBridgeHandler;

import de.egore911.capacity.persistence.dao.EmployeeDao;
import de.egore911.capacity.persistence.model.AbsenceEntity;
import de.egore911.capacity.persistence.model.ContractEntity;
import de.egore911.capacity.persistence.model.EmployeeEpisodeEntity;
import de.egore911.capacity.persistence.model.EpisodeEntity;
import de.egore911.capacity.persistence.model.UserEntity;
import de.egore911.capacity.persistence.model.WorkingHoursEntity;
import de.egore911.capacity.ui.dto.Absence;
import de.egore911.capacity.ui.dto.Contract;
import de.egore911.capacity.ui.dto.Episode;
import de.egore911.capacity.ui.dto.User;
import ma.glasnost.orika.CustomMapper;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.MappingContext;
import ma.glasnost.orika.converter.BidirectionalConverter;
import ma.glasnost.orika.converter.builtin.PassThroughConverter;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import ma.glasnost.orika.metadata.Type;

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
								workingHoursPerWeek += workingHoursEntity.getStart().until(workingHoursEntity.getEnd(), ChronoUnit.HOURS);
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
			.classMap(User.class, UserEntity.class)
			.byDefault()
			.customize(
				new CustomMapper<User, UserEntity>() {
					@Override
					public void mapBtoA(UserEntity a, User b, MappingContext context) {
						b.setPassword(null);
					}
				})
			.register();

		MAPPER_FACTORY
			.getConverterFactory()
			.registerConverter(new PassThroughConverter(ZonedDateTime.class));

		MAPPER_FACTORY
			.getConverterFactory()
			.registerConverter(new PassThroughConverter(LocalDate.class));

		MAPPER_FACTORY
			.getConverterFactory()
			.registerConverter(new PassThroughConverter(LocalTime.class));

		MAPPER_FACTORY
			.getConverterFactory()
			.registerConverter(new PassThroughConverter(LocalDateTime.class));

		MAPPER_FACTORY
			.getConverterFactory()
			.registerConverter(new BidirectionalConverter<DayOfWeek, Integer>() {
				@Override
				public Integer convertTo(DayOfWeek source, Type<Integer> destinationType) {
					return source == null ? null : source.getValue();
				}

				@Override
				public DayOfWeek convertFrom(Integer source, Type<DayOfWeek> destinationType) {
					return source == null ? null : DayOfWeek.of(source);
				}

			});
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

			FlywayLocationsDetector.detect(flyway, dataSource);

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
