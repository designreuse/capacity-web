package de.egore911.capacity.ui.rest;

import de.egore911.capacity.persistence.dao.LocationDao;
import de.egore911.capacity.persistence.model.LocationEntity;
import de.egore911.capacity.persistence.selector.LocationSelector;
import de.egore911.capacity.ui.dto.Location;

import javax.ws.rs.Path;

@Path("location")
public class LocationService extends AbstractResourceService<Location, LocationEntity> {

	@Override
	protected Class<Location> getDtoClass() {
		return Location.class;
	}

	@Override
	protected Class<LocationEntity> getEntityClass() {
		return LocationEntity.class;
	}

	@Override
	protected LocationSelector getSelector() {
		return new LocationSelector();
	}

	@Override
	protected LocationDao getDao() {
		return new LocationDao();
	}

}
