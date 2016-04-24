package de.egore911.capacity.persistence.selector;

import java.util.List;

import javax.annotation.Nonnull;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.ListJoin;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.joda.time.LocalDate;

import de.egore911.capacity.persistence.model.HolidayEntity;
import de.egore911.capacity.persistence.model.HolidayEntity_;
import de.egore911.capacity.persistence.model.LocationEntity;

public class HolidaySelector extends AbstractResourceSelector<HolidayEntity> {

	private static final long serialVersionUID = 5033423386936177219L;

	private LocalDate startInclusive;
	private LocalDate endInclusive;
	private LocationEntity onlyLocation;
	private LocationEntity includingLocation;

	@Nonnull
	@Override
	protected Class<HolidayEntity> getEntityClass() {
		return HolidayEntity.class;
	}

	@Nonnull
	@Override
	protected List<Predicate> generatePredicateList(CriteriaBuilder builder, Root<HolidayEntity> from, CriteriaQuery<?> query) {
		List<Predicate> predicates = super.generatePredicateList(builder, from, query);
		if (startInclusive != null) {
			predicates.add(builder.greaterThanOrEqualTo(from.get(HolidayEntity_.date), startInclusive));
		}
		if (endInclusive != null) {
			predicates.add(builder.lessThanOrEqualTo(from.get(HolidayEntity_.date), endInclusive));
		}
		if (onlyLocation != null) {
			ListJoin<HolidayEntity, LocationEntity> fromHoliday = from.join(HolidayEntity_.locations);
			predicates.add(builder.equal(fromHoliday, onlyLocation));
		}
		if (includingLocation != null) {
			ListJoin<HolidayEntity, LocationEntity> fromHoliday = from.join(HolidayEntity_.locations, JoinType.LEFT);
			predicates.add(
					builder.or(
							builder.equal(fromHoliday, includingLocation),
							builder.isEmpty(from.get(HolidayEntity_.locations))
					)
			);
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

	public HolidaySelector withOnlyLocation(LocationEntity onlyLocation) {
		this.onlyLocation = onlyLocation;
		return this;
	}

	public HolidaySelector withIncludingLocation(LocationEntity includingLocation) {
		this.includingLocation = includingLocation;
		return this;
	}

}
