package de.egore911.capacity.persistence.selector;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.ListJoin;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.LocalDate;

import de.egore911.capacity.persistence.model.AbsenceEntity;
import de.egore911.capacity.persistence.model.AbsenceEntity_;
import de.egore911.capacity.persistence.model.ContractEntity_;
import de.egore911.capacity.persistence.model.EmployeeEntity;
import de.egore911.capacity.persistence.model.EmployeeEntity_;
import de.egore911.capacity.persistence.model.IntegerDbObject_;
import de.egore911.capacity.persistence.model.WorkingHoursEntity_;

public class EmployeeSelector extends AbstractResourceSelector<EmployeeEntity> {

	private static final long serialVersionUID = -4834238226605804927L;

	private LocalDate contractRangeStartDate;
	private LocalDate contractRangeEndDate;
	private String email;
	private LocalDate availableAt;
	private LocalDate absentAt;

	@Override
	protected Class<EmployeeEntity> getEntityClass() {
		return EmployeeEntity.class;
	}

	@Override
	protected List<Predicate> generatePredicateList(CriteriaBuilder builder, Root<EmployeeEntity> from, CriteriaQuery<?> query) {
		List<Predicate> predicates = super.generatePredicateList(builder, from, query);

		if (contractRangeStartDate != null && contractRangeEndDate != null) {
			predicates.add(
				builder.or(
					// Case 1: The query range starts before the contract starts and ends after the contract is started
					builder.and(
						builder.lessThanOrEqualTo(from.get(EmployeeEntity_.contract).get(ContractEntity_.start), contractRangeStartDate),
						builder.greaterThanOrEqualTo(from.get(EmployeeEntity_.contract).get(ContractEntity_.start), contractRangeEndDate)
					),
					// Case 2: The query range starts before the contract ends and ends after the contract is ended
					builder.and(
						builder.lessThanOrEqualTo(from.get(EmployeeEntity_.contract).get(ContractEntity_.end), contractRangeStartDate),
						builder.greaterThanOrEqualTo(from.get(EmployeeEntity_.contract).get(ContractEntity_.end), contractRangeEndDate)
					),
					// Case 3: The query range starts after the contract starts and ends before the contract is ended
					builder.and(
						builder.lessThanOrEqualTo(from.get(EmployeeEntity_.contract).get(ContractEntity_.start), contractRangeStartDate),
						builder.greaterThanOrEqualTo(from.get(EmployeeEntity_.contract).get(ContractEntity_.end), contractRangeEndDate)
					),
					// Case 4: The contract has neither a start nor an end date
					builder.and(
						from.get(EmployeeEntity_.contract).get(ContractEntity_.start).isNull(),
						from.get(EmployeeEntity_.contract).get(ContractEntity_.end).isNull()
					),
					// Case 5: The contract starts before our query range and the contract does not have an end date
					builder.and(
						builder.lessThanOrEqualTo(from.get(EmployeeEntity_.contract).get(ContractEntity_.start), contractRangeStartDate),
						from.get(EmployeeEntity_.contract).get(ContractEntity_.end).isNull()
					),
					// Case 6: The contract ends after our query range and the contract does not have a start date
					builder.and(
						builder.greaterThanOrEqualTo(from.get(EmployeeEntity_.contract).get(ContractEntity_.end), contractRangeEndDate),
						from.get(EmployeeEntity_.contract).get(ContractEntity_.start).isNull()
					),
					// Case 7: The contract starts in our query range and the contract does not have an end date
					builder.and(
						builder.greaterThanOrEqualTo(from.get(EmployeeEntity_.contract).get(ContractEntity_.start), contractRangeStartDate),
						builder.lessThanOrEqualTo(from.get(EmployeeEntity_.contract).get(ContractEntity_.start), contractRangeEndDate),
						from.get(EmployeeEntity_.contract).get(ContractEntity_.end).isNull()
					),
					// Case 8: The contract ends in our query range and the contract does not have a start date
					builder.and(
						builder.greaterThanOrEqualTo(from.get(EmployeeEntity_.contract).get(ContractEntity_.end), contractRangeStartDate),
						builder.lessThanOrEqualTo(from.get(EmployeeEntity_.contract).get(ContractEntity_.end), contractRangeEndDate),
						from.get(EmployeeEntity_.contract).get(ContractEntity_.start).isNull()
					),
					// Case 9: The contract starts in our query range and the contract ends after our query range
					builder.and(
						builder.greaterThanOrEqualTo(from.get(EmployeeEntity_.contract).get(ContractEntity_.start), contractRangeStartDate),
						builder.lessThanOrEqualTo(from.get(EmployeeEntity_.contract).get(ContractEntity_.start), contractRangeEndDate),
						builder.greaterThanOrEqualTo(from.get(EmployeeEntity_.contract).get(ContractEntity_.end), contractRangeEndDate)
					),
					// Case 10: The contract ends in our query range and the contract starts before our query range
					builder.and(
						builder.greaterThanOrEqualTo(from.get(EmployeeEntity_.contract).get(ContractEntity_.end), contractRangeStartDate),
						builder.lessThanOrEqualTo(from.get(EmployeeEntity_.contract).get(ContractEntity_.end), contractRangeEndDate),
						builder.lessThanOrEqualTo(from.get(EmployeeEntity_.contract).get(ContractEntity_.start), contractRangeStartDate)
					),
					// Case 10: The contract starts and ands in our query range
					builder.and(
						builder.greaterThanOrEqualTo(from.get(EmployeeEntity_.contract).get(ContractEntity_.start), contractRangeStartDate),
						builder.lessThanOrEqualTo(from.get(EmployeeEntity_.contract).get(ContractEntity_.end), contractRangeEndDate)
					)
				)
			);
		}

		if (StringUtils.isNotEmpty(email)) {
			predicates.add(builder.equal(from.get(EmployeeEntity_.email), email));
		}

		if (availableAt != null) {
			/*Subquery<Integer> subquery = query.subquery(Integer.class);
			Root<EmployeeEntity> subfrom = subquery.from(EmployeeEntity.class);
			ListJoin<EmployeeEntity, AbsenceEntity> subfromAbsences = subfrom.join(EmployeeEntity_.absences);
			subquery.select(subfrom.get(IntegerDbObject_.id));
			subquery.where(builder.and(
					builder.lessThanOrEqualTo(subfromAbsences.get(AbsenceEntity_.start), availableAt),
					builder.greaterThanOrEqualTo(subfromAbsences.get(AbsenceEntity_.end), availableAt),
					builder.equal(subfrom.join(EmployeeEntity_.contract).join(ContractEntity_.workingHours).get(WorkingHoursEntity_.dayOfWeek), availableAt.getDayOfWeek())
				));
			predicates.add(builder.not(from.get(IntegerDbObject_.id).in(subquery)));*/

			// Find all employees that have a working day at the requested date
			Subquery<Integer> subqueryWorkingDay = subqueryWorkingDay(builder, query, availableAt);

			// Find all employees that have an absence at the requested date
			Subquery<Integer> subqueryAbsence = subqueryAbsence(builder, query, availableAt);
			
			predicates.add(builder.and(
					from.get(IntegerDbObject_.id).in(subqueryWorkingDay),
					builder.not(from.get(IntegerDbObject_.id).in(subqueryAbsence))
				));
		}

		if (absentAt != null) {

			// Find all employees that have a working day at the requested date
			Subquery<Integer> subqueryWorkingDay = subqueryWorkingDay(builder, query, absentAt);

			// Find all employees that have an absence at the requested date
			Subquery<Integer> subqueryAbsence = subqueryAbsence(builder, query, absentAt);

			predicates.add(builder.or(
				builder.not(from.get(IntegerDbObject_.id).in(subqueryWorkingDay)),
				from.get(IntegerDbObject_.id).in(subqueryAbsence)
			));
		}

		return predicates;
	}

	private static Subquery<Integer> subqueryWorkingDay(CriteriaBuilder builder, CriteriaQuery<?> query, LocalDate at) {
		Subquery<Integer> subqueryWorkingDay = query.subquery(Integer.class);
		{
			Root<EmployeeEntity> subfrom = subqueryWorkingDay.from(EmployeeEntity.class);
			subqueryWorkingDay.select(subfrom.get(IntegerDbObject_.id));
			subqueryWorkingDay.where(
				builder.equal(subfrom.join(EmployeeEntity_.contract).join(ContractEntity_.workingHours).get(WorkingHoursEntity_.dayOfWeek), at.getDayOfWeek())
			);
		}
		return subqueryWorkingDay;
	}

	private static Subquery<Integer> subqueryAbsence(CriteriaBuilder builder, CriteriaQuery<?> query, LocalDate at) {
		Subquery<Integer> subqueryAbsence = query.subquery(Integer.class);
		{
			Root<EmployeeEntity> subfrom = subqueryAbsence.from(EmployeeEntity.class);
			ListJoin<EmployeeEntity,AbsenceEntity> fromAbsences = subfrom.join(EmployeeEntity_.absences, JoinType.LEFT);
			subqueryAbsence.select(subfrom.get(IntegerDbObject_.id));
			subqueryAbsence.where(
					builder.and(
							builder.lessThanOrEqualTo(fromAbsences.get(AbsenceEntity_.start), at),
							builder.greaterThanOrEqualTo(fromAbsences.get(AbsenceEntity_.end), at)
						)
			);
		}
		return subqueryAbsence;
	}

	public EmployeeSelector withActiveContract(LocalDate contractRangeStartDate, LocalDate contractRangeEndDate) {
		this.contractRangeStartDate = contractRangeStartDate;
		this.contractRangeEndDate = contractRangeEndDate;
		return this;
	}

	public EmployeeSelector withEmail(String email) {
		this.email = email;
		return this;
	}

	public EmployeeSelector withAvailability(LocalDate availableAt) {
		this.availableAt = availableAt;
		return this;
	}

	public EmployeeSelector withAbsence(LocalDate absentAt) {
		this.absentAt = absentAt;
		return this;
	}

}
