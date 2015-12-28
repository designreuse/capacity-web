package de.egore911.capacity.persistence.selector;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.collections4.CollectionUtils;

import de.egore911.capacity.persistence.model.EmployeeEntity;
import de.egore911.capacity.persistence.model.EmployeeEntity_;
import de.egore911.capacity.persistence.model.IntegerDbObject_;
import de.egore911.persistence.selector.AbstractSelector;

public class EmployeeSelector extends AbstractSelector<EmployeeEntity> {

	private static final long serialVersionUID = -4834238226605804927L;

	private Collection<Integer> ids;

	@Override
	protected Class<EmployeeEntity> getEntityClass() {
		return EmployeeEntity.class;
	}

	@Override
	protected List<Predicate> generatePredicateList(CriteriaBuilder builder, Root<EmployeeEntity> from) {
		List<Predicate> predicates = new ArrayList<>();

		if (CollectionUtils.isNotEmpty(ids)) {
			predicates.add(from.get(IntegerDbObject_.id).in(ids));
		}
		
		return predicates;
	}

	public EmployeeSelector withId(Integer id) {
		this.ids = Collections.singleton(id);
		return this;
	}

	public EmployeeSelector withIds(Collection<Integer> ids) {
		this.ids = ids;
		return this;
	}

}
