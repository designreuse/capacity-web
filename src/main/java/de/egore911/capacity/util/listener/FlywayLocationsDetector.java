package de.egore911.capacity.util.listener;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.flywaydb.core.Flyway;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FlywayLocationsDetector {

	private static final Logger LOG = LoggerFactory.getLogger(FlywayLocationsDetector.class);

	public static void detect(Flyway flyway, DataSource dataSource) {
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
				LOG.error("Error opening connection :{}", e.getMessage(), e);
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
				LOG.error("Error opening connection :{}", e.getMessage(), e);
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
				LOG.error("Error opening connection :{}", e.getMessage(), e);
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
	}

}
