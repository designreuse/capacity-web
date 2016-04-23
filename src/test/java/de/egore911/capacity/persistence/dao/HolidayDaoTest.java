package de.egore911.capacity.persistence.dao;

import java.util.UUID;

import org.joda.time.LocalDate;

import de.egore911.capacity.persistence.model.HolidayEntity;

public class HolidayDaoTest extends AbstractDaoCRUDTest<HolidayEntity> {

	@Override
	protected HolidayEntity createFixture() {
		HolidayEntity holiday = new HolidayEntity();
		holiday.setDate(LocalDate.now());
		modifyFixture(holiday);
		return holiday;
	}

	@Override
	protected void modifyFixture(HolidayEntity created) {
		created.setName(UUID.randomUUID().toString());
	}

	@Override
	protected HolidayDao getDao() {
		return new HolidayDao();
	}

}
