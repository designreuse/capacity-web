package de.egore911.capacity.persistence.dao;

import java.util.UUID;

import de.egore911.capacity.persistence.model.RoleEntity;

public class RoleDaoTest extends AbstractDaoCRUDTest<RoleEntity> {

	@Override
	protected RoleEntity createFixture() {
		RoleEntity user = new RoleEntity();
		user.setName(UUID.randomUUID().toString());
		return user;
	}

	@Override
	protected void modifyFixture(RoleEntity user) {
		user.setName(UUID.randomUUID().toString());
	}

	@Override
	protected RoleDao getDao() {
		return new RoleDao();
	}

}
