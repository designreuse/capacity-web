package de.egore911.capacity.persistence.selector;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.ListJoin;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.joda.time.LocalDate;

import de.egore911.capacity.persistence.model.HolidayEntity;
import de.egore911.capacity.persistence.model.HolidayEntity_;
import de.egore911.capacity.persistence.model.LocationEntity;
import de.egore911.persistence.selector.AbstractSelector;

public class HolidaySelector extends AbstractSelector<HolidayEntity> {

	private static final long serialVersionUID = 5033423386936177219L;

	private LocalDate startInclusive;
	private LocalDate endInclusive;
	private LocationEntity location;

	@Override
	protected Class<HolidayEntity> getEntityClass() {
		return HolidayEntity.class;
	}

	@Override
	protected List<Predicate> generatePredicateList(CriteriaBuilder builder, Root<HolidayEntity> from) {
		List<Predicate> predicates = new ArrayList<>();
		if (startInclusive != null) {
			predicates.add(builder.greaterThanOrEqualTo(from.get(HolidayEntity_.date), startInclusive));
		}
		if (endInclusive != null) {
			predicates.add(builder.lessThanOrEqualTo(from.get(HolidayEntity_.date), endInclusive));
		}
		if (location != null) {
			ListJoin<HolidayEntity, LocationEntity> fromHoliday = from.join(HolidayEntity_.locations);
			predicates.add(builder.equal(fromHoliday, location));
		}
		return predicates;
	}

	public HolidaySelector withStartInclusive(LocalDate startInclusive) {
		this.startInclusive = startInclusive;
		return this;
	}

	public HolidaySelector withEndInclusive(LocalDate endInclusive) {
		this.endInclusive = endInclusive;
		return this;
	}

	public HolidaySelector withLocation(LocationEntity location) {
		this.location = location;
		return this;
	}

}
