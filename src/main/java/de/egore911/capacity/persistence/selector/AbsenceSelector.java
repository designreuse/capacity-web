package de.egore911.capacity.persistence.selector;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import de.egore911.capacity.persistence.model.AbsenceEntity;
import de.egore911.capacity.persistence.model.AbsenceEntity_;
import de.egore911.capacity.persistence.model.EmployeeEntity_;
import de.egore911.persistence.selector.AbstractSelector;

public class AbsenceSelector extends AbstractSelector<AbsenceEntity> {

	private static final long serialVersionUID = 2834185298759056762L;
	private Integer employeeId;

	@Override
	protected Class<AbsenceEntity> getEntityClass() {
		return AbsenceEntity.class;
	}

	@Override
	protected List<Predicate> generatePredicateList(CriteriaBuilder builder, Root<AbsenceEntity> from, CriteriaQuery<?> query) {
		List<Predicate> predicates = new ArrayList<>();

		if (employeeId != null) {
			predicates.add(builder.equal(from.get(AbsenceEntity_.employee).get(EmployeeEntity_.id), employeeId));
		}

		return predicates;
	}

	public AbsenceSelector withEmployeeId(Integer employeeId) {
		this.employeeId = employeeId;
		return this;
	}

}
