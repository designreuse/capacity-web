/*
 * Copyright 2013-2015  Christoph Brill <egore911@gmail.com>
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
package de.egore911.capacity;

import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.sql.DataSource;

import org.hibernate.tool.hbm2ddl.SimpleSchemaExport;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.egore911.persistence.util.EntityManagerUtil;

/**
 * @author Christoph Brill &lt;egore911@gmail.com&gt;
 */
public abstract class AbstractDatabaseTest {

	private static final Logger log = LoggerFactory.getLogger(AbstractDatabaseTest.class);

	private static EntityManagerFactory emf;

	@BeforeClass
	public static void beforeClass() {
		System.setProperty("java.naming.factory.initial", "de.egore911.capacity.JndiFactory");
		OnceInit.init();
		try {
			InitialContext initialContext = new InitialContext();
			DataSource dataSource = (DataSource) initialContext.lookup(JndiFactory.DATASOURCE_NAME);
			try (Connection connection = dataSource.getConnection()) {
				new SimpleSchemaExport().importScript(connection, "/import.sql");
			}
		} catch (NamingException | SQLException e) {
			log.error(e.getMessage(), e);
		}

		emf = Persistence.createEntityManagerFactory("capacity");
	}

	@Before
	public void before() {
		EntityManager em = emf.createEntityManager();
		EntityManagerUtil.setEntityManager(em);
	}

	@After
	public void after() {
		EntityManager em = EntityManagerUtil.getEntityManager();
		EntityManagerUtil.clearEntityManager();
		em.close();
	}

	@AfterClass
	public static void afterClass() {
		emf.close();
	}

}
