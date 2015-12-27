package de.egore911.capacity.ui.rest;

import org.glassfish.jersey.server.ResourceConfig;

import com.fasterxml.jackson.datatype.joda.JodaMapper;
import com.fasterxml.jackson.jaxrs.json.JacksonJaxbJsonProvider;

public class JerseyConfig extends ResourceConfig {

	public JerseyConfig() {
		JacksonJaxbJsonProvider provider = new JacksonJaxbJsonProvider();
		JodaMapper jodaMapper = new JodaMapper();
		jodaMapper.setWriteDatesAsTimestamps(false);
		provider.setMapper(jodaMapper);
		register(provider);

	}

}