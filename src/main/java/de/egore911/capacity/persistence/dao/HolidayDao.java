package de.egore911.capacity.persistence.dao;

import de.egore911.capacity.persistence.model.HolidayEntity;
import de.egore911.capacity.persistence.selector.HolidaySelector;
import de.egore911.persistence.dao.AbstractDao;

public class HolidayDao extends AbstractDao<HolidayEntity> {

	@Override
	protected Class<HolidayEntity> getEntityClass() {
		return HolidayEntity.class;
	}

	@Override
	protected HolidaySelector createSelector() {
		return new HolidaySelector();
	}

}
