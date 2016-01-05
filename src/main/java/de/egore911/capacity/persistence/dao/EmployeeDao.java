package de.egore911.capacity.persistence.dao;

import java.util.List;

import javax.persistence.EntityManager;

import de.egore911.capacity.persistence.model.EmployeeEntity;
import de.egore911.capacity.persistence.selector.EmployeeSelector;
import de.egore911.persistence.dao.AbstractDao;
import de.egore911.persistence.util.EntityManagerUtil;

public class EmployeeDao extends AbstractDao<EmployeeEntity> {

	@Override
	protected Class<EmployeeEntity> getEntityClass() {
		return EmployeeEntity.class;
	}

	@Override
	protected EmployeeSelector createSelector() {
		return new EmployeeSelector();
	}

	public List<String> findAbilities() {
		EntityManager em = EntityManagerUtil.getEntityManager();
		return em
				.createQuery("select distinct a.name from Employee e join e.abilities a order by a.name", String.class)
				.getResultList();
	}
}
