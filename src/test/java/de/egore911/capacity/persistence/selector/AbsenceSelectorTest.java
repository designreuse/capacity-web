package de.egore911.capacity.persistence.selector;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsEmptyCollection.empty;
import static org.hamcrest.Matchers.hasSize;

import org.joda.time.LocalDate;
import org.junit.Test;

import de.egore911.capacity.persistence.model.AbsenceEntity;
import de.egore911.persistence.selector.AbstractSelector;

public class AbsenceSelectorTest extends AbstractSelectorTest<AbsenceEntity> {

	@Override
	protected AbstractSelector<AbsenceEntity> getSelector() {
		return new AbsenceSelector();
	}

	@Test
	public void testPartialContractRanges() {
		LocalDate beforeRange = LocalDate.parse("2015-01-01");
		LocalDate afterRange = LocalDate.parse("2015-10-31");

		int all = (int) new AbsenceSelector().count();

		assertThat(new AbsenceSelector().withStartInclusive(null).withEndInclusive(afterRange).findAll(), hasSize(all));

		assertThat(new AbsenceSelector().withStartInclusive(beforeRange).withEndInclusive(null).findAll(), hasSize(all));

		assertThat(new AbsenceSelector().withStartInclusive(null).withEndInclusive(null).findAll(), hasSize(all));
	}

	@Test
	public void smoketest() {
		LocalDate wayBeforeRange = LocalDate.parse("2014-01-01");
		LocalDate beforeRange = LocalDate.parse("2015-01-01");
		LocalDate inRange = LocalDate.parse("2015-02-14");
		LocalDate afterRange = LocalDate.parse("2015-10-31");
		LocalDate wayAfterRange = LocalDate.parse("2016-10-31");

		assertThat(new AbsenceSelector().withEmployeeId(1).withStartInclusive(wayBeforeRange)
				.withEndInclusive(beforeRange).findAll(), empty());

		assertThat(new AbsenceSelector().withEmployeeId(1).withStartInclusive(beforeRange).withEndInclusive(inRange)
				.findAll(), hasSize(1));

		assertThat(new AbsenceSelector().withEmployeeId(1).withStartInclusive(inRange).withEndInclusive(afterRange)
				.findAll(), hasSize(1));

		assertThat(new AbsenceSelector().withEmployeeId(1).withStartInclusive(afterRange)
				.withEndInclusive(wayAfterRange).findAll(), empty());
	}

}
