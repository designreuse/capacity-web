package de.egore911.capacity.persistence.dao;

import de.egore911.capacity.persistence.model.UserEntity;
import de.egore911.capacity.persistence.selector.UserSelector;
import de.egore911.persistence.dao.AbstractDao;

public class UserDao extends AbstractDao<UserEntity> {

	@Override
	protected Class<UserEntity> getEntityClass() {
		return UserEntity.class;
	}

	@Override
	protected UserSelector createSelector() {
		return new UserSelector();
	}

}
