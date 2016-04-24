package de.egore911.capacity.ui;

import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.test.DeploymentContext;
import org.glassfish.jersey.test.JerseyTest;
import org.glassfish.jersey.test.ServletDeploymentContext;
import org.glassfish.jersey.test.grizzly.GrizzlyWebTestContainerFactory;
import org.glassfish.jersey.test.spi.TestContainerFactory;
import org.junit.BeforeClass;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.jaxrs.json.JacksonJaxbJsonProvider;

import de.egore911.capacity.OnceInit;
import de.egore911.capacity.ui.filter.EntityManagerFilter;
import de.egore911.capacity.ui.rest.JerseyConfig;
import de.egore911.capacity.ui.rest.JavaTimeParameterConverterProvider;

public abstract class AbstractUiTest extends JerseyTest {

	@BeforeClass
	public static void beforeClass() {
		System.setProperty("java.naming.factory.initial", "de.egore911.capacity.JndiFactory");
		OnceInit.init();
	}

	@Override
	protected JerseyConfig configure() {
		// In case we need to see the parameters again:
		//enable(TestProperties.LOG_TRAFFIC);
		//enable(TestProperties.DUMP_ENTITY);

		// Use default configuration, but manually register the provider as
		// @Provider is not picked up by JerseyTest
		JerseyConfig application = new JerseyConfig();
		application.register(JavaTimeParameterConverterProvider.class);
		return application;
	}
	
	@Override
	protected void configureClient(ClientConfig config) {
		ObjectMapper mapper = new ObjectMapper();
		mapper.registerModule(new JavaTimeModule());
		mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
		JacksonJaxbJsonProvider provider = new JacksonJaxbJsonProvider(mapper, JacksonJaxbJsonProvider.DEFAULT_ANNOTATIONS);
		config.register(provider);
	}

	@Override
	protected DeploymentContext configureDeployment() {
		// Use same config as done in web.xml
		return ServletDeploymentContext
				.builder(configure())
				.addFilter(EntityManagerFilter.class, "EntityManagerFilter")
				.initParam("jersey.config.server.provider.packages", "de.egore911.capacity.ui.rest")
				.initParam("javax.ws.rs.Application", "de.egore911.capacity.ui.rest.JerseyConfig")
				.build();
	}

	@Override
	protected TestContainerFactory getTestContainerFactory() {
		return new GrizzlyWebTestContainerFactory();
	}
}
