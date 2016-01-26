package de.egore911.capacity.ui.rest;

import org.glassfish.jersey.server.ResourceConfig;
import org.secnod.shiro.jersey.AuthInjectionBinder;
import org.secnod.shiro.jersey.AuthorizationFilterFeature;
import org.secnod.shiro.jersey.SubjectFactory;

import com.fasterxml.jackson.datatype.joda.JodaMapper;
import com.fasterxml.jackson.jaxrs.json.JacksonJaxbJsonProvider;

public class JerseyConfig extends ResourceConfig {

	public JerseyConfig() {
		JacksonJaxbJsonProvider provider = new JacksonJaxbJsonProvider();
		JodaMapper jodaMapper = new JodaMapper();
		jodaMapper.setWriteDatesAsTimestamps(false);
		provider.setMapper(jodaMapper);
		register(provider);

		register(new AuthorizationFilterFeature());
		register(new SubjectFactory());
		register(new AuthInjectionBinder());
	}

}