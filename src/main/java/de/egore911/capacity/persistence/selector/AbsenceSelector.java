package de.egore911.capacity.persistence.selector;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.joda.time.LocalDate;

import de.egore911.capacity.persistence.model.AbsenceEntity;
import de.egore911.capacity.persistence.model.AbsenceEntity_;
import de.egore911.capacity.persistence.model.IntegerDbObject_;
import de.egore911.persistence.selector.AbstractSelector;

public class AbsenceSelector extends AbstractSelector<AbsenceEntity> {

	private static final long serialVersionUID = 2834185298759056762L;

	private Integer employeeId;
	private LocalDate startInclusive;
	private LocalDate endInclusive;

	@Override
	protected Class<AbsenceEntity> getEntityClass() {
		return AbsenceEntity.class;
	}

	@Override
	protected List<Predicate> generatePredicateList(CriteriaBuilder builder, Root<AbsenceEntity> from, CriteriaQuery<?> query) {
		List<Predicate> predicates = new ArrayList<>();

		if (employeeId != null) {
			predicates.add(builder.equal(from.get(AbsenceEntity_.employee).get(IntegerDbObject_.id), employeeId));
		}
		
		if (startInclusive != null && endInclusive != null) {
			predicates.add(
				builder.or(
					// Case 1: The query range starts before the absence starts and ends after the absence is started
					builder.and(
						builder.lessThanOrEqualTo(from.get(AbsenceEntity_.start), startInclusive),
						builder.greaterThanOrEqualTo(from.get(AbsenceEntity_.start), endInclusive)
					),
					// Case 2: The query range starts before the absence ends and ends after the absence is ended
					builder.and(
						builder.lessThanOrEqualTo(from.get(AbsenceEntity_.end), startInclusive),
						builder.greaterThanOrEqualTo(from.get(AbsenceEntity_.end), endInclusive)
					),
					// Case 3: The query range starts after the absence starts and ends before the absence is ended
					builder.and(
						builder.lessThanOrEqualTo(from.get(AbsenceEntity_.start), startInclusive),
						builder.greaterThanOrEqualTo(from.get(AbsenceEntity_.end), endInclusive)
					),
					// Case 4: The query range starts after the absence starts and ends before the absence is ended
					builder.and(
						builder.greaterThanOrEqualTo(from.get(AbsenceEntity_.start), startInclusive),
						builder.lessThanOrEqualTo(from.get(AbsenceEntity_.end), endInclusive)
					),
					// Case 5: The absence has neither a start nor an end date
					builder.and(
						from.get(AbsenceEntity_.start).isNull(),
						from.get(AbsenceEntity_.end).isNull()
					),
					// Case 6: The absence starts before our query range and the absence does not have an end date
					builder.and(
						builder.lessThanOrEqualTo(from.get(AbsenceEntity_.start), startInclusive),
						from.get(AbsenceEntity_.end).isNull()
					),
					// Case 7: The absence ends after our query range and the absence does not have a start date
					builder.and(
						builder.greaterThanOrEqualTo(from.get(AbsenceEntity_.end), endInclusive),
						from.get(AbsenceEntity_.start).isNull()
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
