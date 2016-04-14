package de.egore911.capacity.persistence.dao;

import java.util.Set;

import javax.annotation.Nonnull;

import de.egore911.capacity.persistence.model.AbsenceEntity;
import de.egore911.capacity.persistence.model.IcalImportEntity;
import de.egore911.capacity.persistence.selector.AbsenceSelector;
import de.egore911.persistence.dao.AbstractDao;
import de.egore911.persistence.util.EntityManagerUtil;

public class AbsenceDao extends AbstractDao<AbsenceEntity> {

	@Override
	protected Class<AbsenceEntity> getEntityClass() {
		return AbsenceEntity.class;
	}

	@Override
	protected AbsenceSelector createSelector() {
		return new AbsenceSelector();
	}

	public int deleteFromIcalImportExcept(@Nonnull IcalImportEntity icalImport,
			@Nonnull Set<Integer> absenceIds) {
		return EntityManagerUtil.getEntityManager()
			.createQuery("delete from Absence where icalImport = :icalImport and not id in(:absenceIds)")
			.setParameter("icalImport", icalImport)
			.setParameter("absenceIds", absenceIds)
			.executeUpdate();

	}

}
