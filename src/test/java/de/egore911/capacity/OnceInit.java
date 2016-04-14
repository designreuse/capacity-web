package de.egore911.capacity;

import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.hibernate.tool.hbm2ddl.SimpleSchemaExport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.egore911.capacity.util.listener.StartupListener;

public class OnceInit {

	private static final Logger log = LoggerFactory.getLogger(OnceInit.class);

	private static boolean once = false;

	public static void init() {
		if (!once) {
			new StartupListener().contextInitialized(null);

			try {
				InitialContext initialContext = new InitialContext();
				DataSource dataSource = (DataSource) initialContext.lookup(JndiFactory.DATASOURCE_NAME);
				try (Connection connection = dataSource.getConnection()) {
					new SimpleSchemaExport().importScript(connection, "/import.sql");
					// MetadataImplementor metadata = null;
					// SchemaExport schemaExport = new SchemaExport(metadata,
					// connection);
					// schemaExport.execute(Target.EXPORT, Type.CREATE);
				}
			} catch (NamingException | SQLException e) {
				log.error(e.getMessage(), e);
			}

			once = true;
		}
	}
}
