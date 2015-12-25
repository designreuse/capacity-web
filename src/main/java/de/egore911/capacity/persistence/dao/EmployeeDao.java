package de.egore911.capacity.persistence.dao;

import de.egore911.capacity.persistence.model.EmployeeEntity;
import de.egore911.capacity.persistence.selector.EmployeeSelector;
import de.egore911.persistence.dao.AbstractDao;

public class EmployeeDao extends AbstractDao<EmployeeEntity> {

	@Override
	protected Class<EmployeeEntity> getEntityClass() {
		return EmployeeEntity.class;
	}

	@Override
	protected EmployeeSelector createSelector() {
		return new EmployeeSelector();
	}

}
