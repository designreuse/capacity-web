package de.egore911.capacity.ui;

import org.glassfish.jersey.test.DeploymentContext;
import org.glassfish.jersey.test.JerseyTest;
import org.glassfish.jersey.test.ServletDeploymentContext;
import org.junit.BeforeClass;

import de.egore911.capacity.OnceInit;
import de.egore911.capacity.ui.filter.EntityManagerFilter;
import de.egore911.capacity.ui.rest.JerseyConfig;
import de.egore911.capacity.ui.rest.JodaParameterConverterProvider;

public abstract class AbstractUiTest extends JerseyTest {

	@BeforeClass
	public static void beforeClass() {
		System.setProperty("java.naming.factory.initial", "de.egore911.capacity.JndiFactory");
		OnceInit.init();
	}

	@Override
	protected JerseyConfig configure() {
		JerseyConfig application = new JerseyConfig();
		application.register(JodaParameterConverterProvider.class);
		return application;
	}

	@Override
	protected DeploymentContext configureDeployment() {
		return ServletDeploymentContext.builder(configure()).addFilter(EntityManagerFilter.class, "EntityManagerFilter").build();
	}

}
