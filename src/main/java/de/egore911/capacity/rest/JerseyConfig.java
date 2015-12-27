package de.egore911.capacity.rest;

import org.glassfish.jersey.server.ResourceConfig;

import com.fasterxml.jackson.datatype.joda.JodaMapper;
import com.fasterxml.jackson.jaxrs.json.JacksonJaxbJsonProvider;

public class JerseyConfig extends ResourceConfig {

	public JerseyConfig() {
		JacksonJaxbJsonProvider provider = new JacksonJaxbJsonProvider();
		provider.setMapper(new JodaMapper());
		register(provider);

		packages("jersey.config.server.provider.packages");
	}

}