package de.egore911.capacity.persistence.dao;

import java.util.UUID;

import org.joda.time.LocalDate;

import de.egore911.capacity.persistence.model.AbsenceEntity;

public class AbsenceDaoTest extends AbstractDaoCRUDTest<AbsenceEntity> {

	@Override
	protected AbsenceEntity createFixture() {
		AbsenceEntity absence = new AbsenceEntity();
		absence.setStart(LocalDate.now());
		absence.setEnd(LocalDate.now());
		absence.setReason(UUID.randomUUID().toString());
		absence.setEmployee(new EmployeeDao().findById(1));
		return absence;
	}

	@Override
	protected void modifyFixture(AbsenceEntity absence) {
		absence.setReason(UUID.randomUUID().toString());
	}

	@Override
	protected AbsenceDao getDao() {
		return new AbsenceDao();
	}

}
