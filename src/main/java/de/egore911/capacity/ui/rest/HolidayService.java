package de.egore911.capacity.ui.rest;

import javax.ws.rs.Path;

import de.egore911.capacity.persistence.dao.HolidayDao;
import de.egore911.capacity.persistence.model.HolidayEntity;
import de.egore911.capacity.persistence.selector.HolidaySelector;
import de.egore911.capacity.ui.dto.Holiday;

@Path("holiday")
public class HolidayService extends AbstractResourceService<Holiday, HolidayEntity> {

	@Override
	protected Class<Holiday> getDtoClass() {
		return Holiday.class;
	}

	@Override
	protected Class<HolidayEntity> getEntityClass() {
		return HolidayEntity.class;
	}

	@Override
	protected HolidaySelector getSelector() {
		return new HolidaySelector();
	}

	@Override
	protected HolidayDao getDao() {
		return new HolidayDao();
	}

}
