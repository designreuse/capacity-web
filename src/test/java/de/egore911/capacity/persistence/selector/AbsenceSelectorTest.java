package de.egore911.capacity.persistence.selector;

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
	public void smoketest() {
		LocalDate startDate = LocalDate.parse("2015-10-01");
		LocalDate endDate = LocalDate.parse("2015-10-31");

		new AbsenceSelector()
			.withEmployeeId(1)
			.withStartInclusive(startDate)
			.withEndInclusive(endDate)
			.findAll();
	}

}
