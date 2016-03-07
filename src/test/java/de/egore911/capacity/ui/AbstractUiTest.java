package de.egore911.capacity.ui;

import org.glassfish.jersey.test.JerseyTest;
import org.junit.BeforeClass;

import de.egore911.capacity.OnceInit;
import de.egore911.capacity.ui.rest.JerseyConfig;

public abstract class AbstractUiTest extends JerseyTest {

	@BeforeClass
	public static void beforeClass() {
		System.setProperty("java.naming.factory.initial", "de.egore911.capacity.JndiFactory");
		OnceInit.init();
	}

	@Override
	protected JerseyConfig configure() {
		return new JerseyConfig();
	}

}
