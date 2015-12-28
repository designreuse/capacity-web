package de.egore911.capacity.persistence.selector;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.collections4.CollectionUtils;
import org.joda.time.LocalDate;

import de.egore911.capacity.persistence.model.ContractEntity_;
import de.egore911.capacity.persistence.model.EmployeeEntity;
import de.egore911.capacity.persistence.model.EmployeeEntity_;
import de.egore911.capacity.persistence.model.IntegerDbObject_;
import de.egore911.persistence.selector.AbstractSelector;

public class EmployeeSelector extends AbstractSelector<EmployeeEntity> {

	private static final long serialVersionUID = -4834238226605804927L;

	private Collection<Integer> ids;
	private LocalDate contractRangeStartDate;
	private LocalDate contractRangeEndDate;

	@Override
	protected Class<EmployeeEntity> getEntityClass() {
		return EmployeeEntity.class;
	}

	@Override
	protected List<Predicate> generatePredicateList(CriteriaBuilder builder, Root<EmployeeEntity> from, CriteriaQuery<?> query) {
		List<Predicate> predicates = new ArrayList<>();

		if (CollectionUtils.isNotEmpty(ids)) {
			predicates.add(from.get(IntegerDbObject_.id).in(ids));
		}

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
					)
				)
			);
		}

		return predicates;
	}

	public EmployeeSelector withId(Integer id) {
		this.ids = Collections.singleton(id);
		return this;
	}

	public EmployeeSelector withActiveContract(LocalDate contractRangeStartDate, LocalDate contractRangeEndDate) {
		this.contractRangeStartDate = contractRangeStartDate;
		this.contractRangeEndDate = contractRangeEndDate;
		return this;
	}

	public EmployeeSelector withIds(Collection<Integer> ids) {
		this.ids = ids;
		return this;
	}

}
