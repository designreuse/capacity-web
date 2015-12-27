package de.egore911.capacity.ui.rest;

import de.egore911.capacity.util.listener.StartupListener;
import ma.glasnost.orika.MapperFacade;

abstract class AbstractService {

	protected final MapperFacade getMapper() {
		return StartupListener.MAPPER_FACTORY.getMapperFacade();
	}

}
