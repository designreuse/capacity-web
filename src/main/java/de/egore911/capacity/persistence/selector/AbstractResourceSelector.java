package de.egore911.capacity.persistence.selector;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.annotation.Nonnull;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.collections4.CollectionUtils;

import de.egore911.capacity.persistence.model.IntegerDbObject;
import de.egore911.capacity.persistence.model.IntegerDbObject_;
import de.egore911.persistence.selector.AbstractSelector;

public abstract class AbstractResourceSelector<T extends IntegerDbObject> extends AbstractSelector<T> {

	private static final long serialVersionUID = -8692616820172675651L;

	private Collection<Integer> ids;

	@Override
	@Nonnull
	protected List<Predicate> generatePredicateList(@Nonnull CriteriaBuilder builder, @Nonnull Root<T> from,
			@Nonnull CriteriaQuery<?> criteriaQuery) {
		List<Predicate> predicates = new ArrayList<>();

		if (CollectionUtils.isNotEmpty(ids)) {
			predicates.add(from.get(IntegerDbObject_.id).in(ids));
		}

		return predicates;
	}

	public AbstractResourceSelector<T> withId(Integer id) {
		this.ids = Collections.singleton(id);
		return this;
	}

	public AbstractResourceSelector<T> withIds(Collection<Integer> ids) {
		this.ids = ids;
		return this;
	}

}
