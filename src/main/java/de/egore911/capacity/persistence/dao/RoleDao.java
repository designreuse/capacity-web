package de.egore911.capacity.persistence.dao;

import de.egore911.capacity.persistence.model.RoleEntity;
import de.egore911.capacity.persistence.selector.RoleSelector;
import de.egore911.persistence.dao.AbstractDao;

public class RoleDao extends AbstractDao<RoleEntity> {

	@Override
	protected Class<RoleEntity> getEntityClass() {
		return RoleEntity.class;
	}

	@Override
	protected RoleSelector createSelector() {
		return new RoleSelector();
	}

}
