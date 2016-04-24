package de.egore911.capacity.persistence.dao;

import de.egore911.capacity.persistence.model.LocationEntity;
import de.egore911.capacity.persistence.selector.LocationSelector;
import de.egore911.persistence.dao.AbstractDao;

public class LocationDao extends AbstractDao<LocationEntity> {

	@Override
	protected Class<LocationEntity> getEntityClass() {
		return LocationEntity.class;
	}

	@Override
	protected LocationSelector createSelector() {
		return new LocationSelector();
	}

}
