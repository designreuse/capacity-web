package de.egore911.capacity.ui.rest;

import javax.ws.rs.Path;

import de.egore911.capacity.persistence.dao.AbsenceDao;
import de.egore911.capacity.persistence.model.AbsenceEntity;
import de.egore911.capacity.persistence.selector.AbsenceSelector;
import de.egore911.capacity.ui.dto.Absence;

@Path("absence")
public class AbsenceService extends AbstractResourceService<Absence, AbsenceEntity> {

	@Override
	protected Class<Absence> getDtoClass() {
		return Absence.class;
	}

	@Override
	protected Class<AbsenceEntity> getEntityClass() {
		return AbsenceEntity.class;
	}

	@Override
	protected AbsenceSelector getSelector() {
		return new AbsenceSelector();
	}

	@Override
	protected AbsenceDao getDao() {
		return new AbsenceDao();
	}

}
