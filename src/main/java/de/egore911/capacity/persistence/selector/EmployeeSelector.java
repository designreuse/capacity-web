package de.egore911.capacity.persistence.selector;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import de.egore911.capacity.persistence.model.EmployeeEntity;
import de.egore911.capacity.persistence.model.EmployeeEntity_;
import de.egore911.persistence.selector.AbstractSelector;

public class EmployeeSelector extends AbstractSelector<EmployeeEntity> {

	private static final long serialVersionUID = -4834238226605804927L;
	
	private Integer id;
	
	@Override
	protected Class<EmployeeEntity> getEntityClass() {
		return EmployeeEntity.class;
	}

	@Override
	protected List<Predicate> generatePredicateList(CriteriaBuilder builder, Root<EmployeeEntity> from) {
		List<Predicate> predicates = new ArrayList<>();
		
		if (id != null) {
			predicates.add(builder.equal(from.get(EmployeeEntity_.id), id));
		}
		
		return predicates;
	}
	
	public EmployeeSelector withId(Integer id) {
		this.id = id;
		return this;
	}

}
