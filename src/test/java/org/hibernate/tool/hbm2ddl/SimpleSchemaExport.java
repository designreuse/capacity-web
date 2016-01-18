/*
 * Hibernate, Relational Persistence for Idiomatic Java
 *
 * Copyright (c) 2008-2011, Red Hat Inc. or third-party contributors as
 * indicated by the @author tags or express copyright attribution
 * statements applied by the authors.  All third-party contributions are
 * distributed under license by Red Hat Inc.
 *
 * This copyrighted material is made available to anyone wishing to use, modify,
 * copy, or redistribute it subject to the terms and conditions of the GNU
 * Lesser General Public License, as published by the Free Software Foundation.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY
 * or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser General Public License
 * for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this distribution; if not, write to:
 * Free Software Foundation, Inc.
 * 51 Franklin Street, Fifth Floor
 * Boston, MA  02110-1301  USA
 */
package org.hibernate.tool.hbm2ddl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

import org.hibernate.engine.jdbc.spi.SqlExceptionHelper;
import org.hibernate.internal.util.ConfigHelper;
import org.hibernate.internal.util.StringHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Stripped copy of SchemaExport
 * 
 * @author Daniel Bradby
 * @author Gavin King
 * @author Steve Ebersole
 * @author Christoph Brill &lt;egore911@gmail.com&gt;
 */
@SuppressWarnings("deprecation")
public class SimpleSchemaExport {

	private static final Logger log = LoggerFactory
			.getLogger(SimpleSchemaExport.class);

	public void importScript(Connection connection, String currentFile)
			throws SQLException {

		ConnectionHelper connectionHelper = new SuppliedConnectionHelper(
				connection);
		SqlExceptionHelper sqlExceptionHelper = new SqlExceptionHelper();
		List<DatabaseExporter> exporters = Collections
				.singletonList(new DatabaseExporter(connectionHelper,
						sqlExceptionHelper));

		String resourceName = currentFile.trim();
		InputStream stream = ConfigHelper.getResourceAsStream(resourceName);
		try (InputStreamReader namedReader = new InputStreamReader(stream);
				BufferedReader reader = new BufferedReader(namedReader)) {
			String[] statements = new SingleLineSqlCommandExtractor()
					.extractCommands(reader);
			if (statements != null) {
				for (String statement : statements) {
					if (statement != null) {
						String trimmedSql = statement.trim();
						if (trimmedSql.endsWith(";")) {
							trimmedSql = trimmedSql.substring(0,
									statement.length() - 1);
						}
						if (!StringHelper.isEmpty(trimmedSql)) {
							try {
								for (Exporter exporter : exporters) {
									if (exporter.acceptsImportScripts()) {
										exporter.export(trimmedSql);
									}
								}
							} catch (Exception e) {
								throw new ImportScriptException(
										"Error during statement execution (file: '"
												+ resourceName + "'): "
												+ trimmedSql, e);
							}
						}
					}
				}
			}
		} catch (IOException e1) {
			log.error(e1.getMessage(), e1);
		}
	}

}
