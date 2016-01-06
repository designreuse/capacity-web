package de.egore911.capacity.persistence.dao;

import de.egore911.capacity.persistence.model.IcalImportEntity;
import de.egore911.capacity.persistence.selector.IcalImportSelector;
import de.egore911.persistence.dao.AbstractDao;

public class IcalImportDao extends AbstractDao<IcalImportEntity> {

	@Override
	protected Class<IcalImportEntity> getEntityClass() {
		return IcalImportEntity.class;
	}

	@Override
	protected IcalImportSelector createSelector() {
		return new IcalImportSelector();
	}

}
