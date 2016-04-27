package de.egore911.capacity.persistence.dao;

import java.util.UUID;

import de.egore911.capacity.persistence.model.UserEntity;

public class UserDaoTest extends AbstractDaoCRUDTest<UserEntity> {

	@Override
	protected UserEntity createFixture() {
		UserEntity user = new UserEntity();
		user.setName(UUID.randomUUID().toString());
		user.setLogin(UUID.randomUUID().toString());
		user.setPassword("0000000000111111111122222222223333333333");
		user.setEmail(UUID.randomUUID().toString());
		return user;
	}

	@Override
	protected void modifyFixture(UserEntity user) {
		user.setName(UUID.randomUUID().toString());
	}

	@Override
	protected UserDao getDao() {
		return new UserDao();
	}

}
