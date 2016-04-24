package de.egore911.capacity.persistence.dao;

import java.util.UUID;

import de.egore911.capacity.persistence.model.LocationEntity;

public class LocationDaoTest extends AbstractDaoCRUDTest<LocationEntity> {

	@Override
	protected LocationEntity createFixture() {
		LocationEntity location = new LocationEntity();
		location.setName(UUID.randomUUID().toString());
		return location;
	}

	@Override
	protected void modifyFixture(LocationEntity location) {
		location.setName(UUID.randomUUID().toString());
	}

	@Override
	protected LocationDao getDao() {
		return new LocationDao();
	}

}
