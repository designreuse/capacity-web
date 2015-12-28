package de.egore911.capacity.persistence.dao;

import de.egore911.capacity.persistence.model.AbsenceEntity;
import de.egore911.capacity.persistence.selector.AbsenceSelector;
import de.egore911.persistence.dao.AbstractDao;

public class AbsenceDao extends AbstractDao<AbsenceEntity> {

	@Override
	protected Class<AbsenceEntity> getEntityClass() {
		return AbsenceEntity.class;
	}

	@Override
	protected AbsenceSelector createSelector() {
		return new AbsenceSelector();
	}

}
