package de.egore911.capacity.persistence.selector;

import java.time.LocalDate;
import java.util.List;

import javax.annotation.Nonnull;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import de.egore911.capacity.persistence.model.AbsenceEntity;
import de.egore911.capacity.persistence.model.AbsenceEntity_;
import de.egore911.capacity.persistence.model.IntegerDbObject_;

public class AbsenceSelector extends AbstractResourceSelector<AbsenceEntity> {

	private static final long serialVersionUID = 2834185298759056762L;

	private Integer employeeId;
	private LocalDate startInclusive;
	private LocalDate endInclusive;

	@Nonnull
	@Override
	protected Class<AbsenceEntity> getEntityClass() {
		return AbsenceEntity.class;
	}

	@Nonnull
	@Override
	protected List<Predicate> generatePredicateList(@Nonnull CriteriaBuilder builder, @Nonnull Root<AbsenceEntity> from, @Nonnull CriteriaQuery<?> query) {
		List<Predicate> predicates = super.generatePredicateList(builder, from, query);

		if (employeeId != null) {
			predicates.add(builder.equal(from.get(AbsenceEntity_.employee).get(IntegerDbObject_.id), employeeId));
		}

		if (startInclusive != null && endInclusive != null) {
			predicates.add(
				builder.or(
					// Absence starts before query range begin and ends after query range begin
					builder.and(
						builder.lessThanOrEqualTo(from.get(AbsenceEntity_.start), startInclusive),
						builder.greaterThanOrEqualTo(from.get(AbsenceEntity_.end), startInclusive)
					),
					// Absence starts before query range end and ends after query range end
					builder.and(
						builder.lessThanOrEqualTo(from.get(AbsenceEntity_.start), endInclusive),
						builder.greaterThanOrEqualTo(from.get(AbsenceEntity_.end), endInclusive)
					),
					// Absence starts in query range
					builder.and(
						builder.greaterThanOrEqualTo(from.get(AbsenceEntity_.start), startInclusive),
						builder.lessThanOrEqualTo(from.get(AbsenceEntity_.start), endInclusive)
					),
					// Absence ends in query range
					builder.and(
						builder.greaterThanOrEqualTo(from.get(AbsenceEntity_.end), startInclusive),
						builder.lessThanOrEqualTo(from.get(AbsenceEntity_.end), endInclusive)
					)
				)
			);
		}

		return predicates;
	}

	public AbsenceSelector withEmployeeId(Integer employeeId) {
		this.employeeId = employeeId;
		return this;
	}

	public AbsenceSelector withStartInclusive(LocalDate startInclusive) {
		this.startInclusive = startInclusive;
		return this;
	}

	public AbsenceSelector withEndInclusive(LocalDate endInclusive) {
		this.endInclusive = endInclusive;
		return this;
	}

}
