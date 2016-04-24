package de.egore911.capacity.persistence.selector;

import javax.annotation.Nonnull;

import de.egore911.capacity.persistence.model.IcalImportEntity;

public class IcalImportSelector extends AbstractResourceSelector<IcalImportEntity> {

	private static final long serialVersionUID = 204373498420323012L;

	@Override
	@Nonnull
	protected Class<IcalImportEntity> getEntityClass() {
		return IcalImportEntity.class;
	}

}
