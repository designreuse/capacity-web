package de.egore911.capacity.persistence.dao;

import java.util.UUID;

import de.egore911.capacity.persistence.model.IcalImportEntity;

public class IcalImportDaoTest extends AbstractDaoCRUDTest<IcalImportEntity> {

	@Override
	protected IcalImportEntity createFixture() {
		IcalImportEntity icalImport = new IcalImportEntity();
		icalImport.setName(UUID.randomUUID().toString());
		icalImport.setUrl("file:///dev/null");
		return icalImport;
	}

	@Override
	protected void modifyFixture(IcalImportEntity icalImport) {
		icalImport.setName(UUID.randomUUID().toString());
	}

	@Override
	protected IcalImportDao getDao() {
		return new IcalImportDao();
	}

}
