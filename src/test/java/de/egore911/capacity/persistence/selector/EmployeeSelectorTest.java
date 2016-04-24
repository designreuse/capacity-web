package de.egore911.capacity.persistence.selector;

import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.beans.HasPropertyWithValue.hasProperty;
import static org.hamcrest.number.OrderingComparison.greaterThanOrEqualTo;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.hamcrest.Matchers;
import org.joda.time.LocalDate;
import org.junit.Test;

import de.egore911.capacity.persistence.model.EmployeeEntity;
import de.egore911.persistence.selector.AbstractSelector;

@SuppressWarnings("unchecked")
public class EmployeeSelectorTest extends AbstractSelectorTest<EmployeeEntity> {

	@Override
	protected AbstractSelector<EmployeeEntity> getSelector() {
		return new EmployeeSelector();
	}

	@Test
	public void testPartialContractRanges() {
		LocalDate contractRangeStartDate = LocalDate.parse("2015-02-10");
		LocalDate contractRangeEndDate = LocalDate.parse("2015-02-10");

		int all = (int) new EmployeeSelector().count();

		assertThat(new EmployeeSelector().withActiveContract(null, contractRangeEndDate).findAll(), hasSize(all));

		assertThat(new EmployeeSelector().withActiveContract(contractRangeStartDate, null).findAll(), hasSize(all));

		assertThat(new EmployeeSelector().withActiveContract(null, null).findAll(), hasSize(all));
	}

	@Test
	public void testContractRange_workingOnTenthOfFebruary() {
		LocalDate contractRangeStartDate = LocalDate.parse("2015-02-10");
		LocalDate contractRangeEndDate = LocalDate.parse("2015-02-10");
		List<EmployeeEntity> employees = new EmployeeSelector()
				.withActiveContract(contractRangeStartDate, contractRangeEndDate)
				.findAll();

		assertThat(employees, hasSize(greaterThanOrEqualTo(8)));

		assertThat(employees, hasItems(Matchers.hasProperty("id", equalTo(1))));
		assertThat(employees, hasItems(Matchers.hasProperty("id", equalTo(2))));
		assertThat(employees, hasItems(Matchers.hasProperty("id", equalTo(3))));
		assertThat(employees, not(hasItems(Matchers.hasProperty("id", equalTo(4)))));
		assertThat(employees, hasItems(Matchers.hasProperty("id", equalTo(5))));
		assertThat(employees, not(hasItems(Matchers.hasProperty("id", equalTo(6)))));
		assertThat(employees, hasItems(Matchers.hasProperty("id", equalTo(7))));
		assertThat(employees, not(hasItems(Matchers.hasProperty("id", equalTo(8)))));
		assertThat(employees, not(hasItems(Matchers.hasProperty("id", equalTo(9)))));
		assertThat(employees, not(hasItems(Matchers.hasProperty("id", equalTo(10)))));
		assertThat(employees, hasItems(Matchers.hasProperty("id", equalTo(13))));
		assertThat(employees, not(hasItems(Matchers.hasProperty("id", equalTo(14)))));
		assertThat(employees, hasItems(Matchers.hasProperty("id", equalTo(15))));
		assertThat(employees, not(hasItems(Matchers.hasProperty("id", equalTo(16)))));
		assertThat(employees, hasItems(Matchers.hasProperty("id", equalTo(17))));
		assertThat(employees, not(hasItems(Matchers.hasProperty("id", equalTo(18)))));
	}

	@Test
	public void testContractRange_workingInOctober() {
		LocalDate contractRangeStartDate = LocalDate.parse("2015-10-01");
		LocalDate contractRangeEndDate = LocalDate.parse("2015-10-31");
		List<EmployeeEntity> employees = new EmployeeSelector()
				.withActiveContract(contractRangeStartDate, contractRangeEndDate)
				.findAll();

		assertThat(employees, hasSize(greaterThanOrEqualTo(12)));

		assertThat(employees, hasItems(Matchers.hasProperty("id", equalTo(1))));
		assertThat(employees, hasItems(Matchers.hasProperty("id", equalTo(2))));
		assertThat(employees, not(hasItems(Matchers.hasProperty("id", equalTo(3)))));
		assertThat(employees, hasItems(Matchers.hasProperty("id", equalTo(4))));
		assertThat(employees, hasItems(Matchers.hasProperty("id", equalTo(5))));
		assertThat(employees, not(hasItems(Matchers.hasProperty("id", equalTo(6)))));
		assertThat(employees, hasItems(Matchers.hasProperty("id", equalTo(7))));
		assertThat(employees, hasItems(Matchers.hasProperty("id", equalTo(8))));
		assertThat(employees, hasItems(Matchers.hasProperty("id", equalTo(9))));
		assertThat(employees, hasItems(Matchers.hasProperty("id", equalTo(10))));
		assertThat(employees, not(hasItems(Matchers.hasProperty("id", equalTo(13)))));
		assertThat(employees, hasItems(Matchers.hasProperty("id", equalTo(14))));
		assertThat(employees, hasItems(Matchers.hasProperty("id", equalTo(15))));
		assertThat(employees, not(hasItems(Matchers.hasProperty("id", equalTo(16)))));
		assertThat(employees, hasItems(Matchers.hasProperty("id", equalTo(17))));
		assertThat(employees, hasItems(Matchers.hasProperty("id", equalTo(18))));

	}

	@Test
	public void testContractRange_workingInSeptember() {
		LocalDate contractRangeStartDate = LocalDate.parse("2015-09-01");
		LocalDate contractRangeEndDate = LocalDate.parse("2015-09-30");
		List<EmployeeEntity> employees = new EmployeeSelector()
				.withActiveContract(contractRangeStartDate, contractRangeEndDate)
				.findAll();

		assertThat(employees, hasSize(greaterThanOrEqualTo(8)));

		assertThat(employees, hasItems(Matchers.hasProperty("id", equalTo(1))));
		assertThat(employees, hasItems(Matchers.hasProperty("id", equalTo(2))));
		assertThat(employees, hasItems(Matchers.hasProperty("id", equalTo(3))));
		assertThat(employees, not(hasItems(Matchers.hasProperty("id", equalTo(4)))));
		assertThat(employees, hasItems(Matchers.hasProperty("id", equalTo(5))));
		assertThat(employees, not(hasItems(Matchers.hasProperty("id", equalTo(6)))));
		assertThat(employees, hasItems(Matchers.hasProperty("id", equalTo(7))));
		assertThat(employees, not(hasItems(Matchers.hasProperty("id", equalTo(8)))));
		assertThat(employees, not(hasItems(Matchers.hasProperty("id", equalTo(9)))));
		assertThat(employees, not(hasItems(Matchers.hasProperty("id", equalTo(10)))));
		assertThat(employees, hasItems(Matchers.hasProperty("id", equalTo(13))));
		assertThat(employees, not(hasItems(Matchers.hasProperty("id", equalTo(14)))));
		assertThat(employees, hasItems(Matchers.hasProperty("id", equalTo(15))));
		assertThat(employees, not(hasItems(Matchers.hasProperty("id", equalTo(16)))));
		assertThat(employees, hasItems(Matchers.hasProperty("id", equalTo(17))));
		assertThat(employees, not(hasItems(Matchers.hasProperty("id", equalTo(18)))));

	}

	@Test
	public void testContractRange_workingInNovember() {
		LocalDate contractRangeStartDate = LocalDate.parse("2015-11-01");
		LocalDate contractRangeEndDate = LocalDate.parse("2015-11-30");
		List<EmployeeEntity> employees = new EmployeeSelector()
				.withActiveContract(contractRangeStartDate, contractRangeEndDate)
				.findAll();

		assertThat(employees, hasSize(greaterThanOrEqualTo(8)));

		assertThat(employees, hasItems(Matchers.hasProperty("id", equalTo(1))));
		assertThat(employees, hasItems(Matchers.hasProperty("id", equalTo(2))));
		assertThat(employees, not(hasItems(Matchers.hasProperty("id", equalTo(3)))));
		assertThat(employees, hasItems(Matchers.hasProperty("id", equalTo(4))));
		assertThat(employees, not(hasItems(Matchers.hasProperty("id", equalTo(5)))));
		assertThat(employees, hasItems(Matchers.hasProperty("id", equalTo(6))));
		assertThat(employees, not(hasItems(Matchers.hasProperty("id", equalTo(7)))));
		assertThat(employees, hasItems(Matchers.hasProperty("id", equalTo(8))));
		assertThat(employees, not(hasItems(Matchers.hasProperty("id", equalTo(9)))));
		assertThat(employees, not(hasItems(Matchers.hasProperty("id", equalTo(10)))));
		assertThat(employees, not(hasItems(Matchers.hasProperty("id", equalTo(13)))));
		assertThat(employees, hasItems(Matchers.hasProperty("id", equalTo(14))));
		assertThat(employees, not(hasItems(Matchers.hasProperty("id", equalTo(15)))));
		assertThat(employees, hasItems(Matchers.hasProperty("id", equalTo(16))));
		assertThat(employees, not(hasItems(Matchers.hasProperty("id", equalTo(17)))));
		assertThat(employees, hasItems(Matchers.hasProperty("id", equalTo(18))));

	}

	@Test
	public void testSorting() {
		List<EmployeeEntity> employees = new EmployeeSelector()
				.withEmail("from@october")
				.withSortColumn("name")
				.withAscending(true)
				.findAll();

		assertThat(employees, contains(hasProperty("name", equalTo("User from start october")), hasProperty("name", equalTo("User from start october until dec"))));

		employees = new EmployeeSelector()
				.withEmail("from@october")
				.withSortColumn("name")
				.withAscending(false)
				.findAll();

		assertThat(employees, contains(hasProperty("name", equalTo("User from start october until dec")), hasProperty("name", equalTo("User from start october"))));
	}

}
