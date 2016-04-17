package de.egore911.capacity.persistence.selector;

import org.joda.time.LocalDate;
import org.junit.Test;

import de.egore911.capacity.AbstractDatabaseTest;

public class AbsenceSelectorTest extends AbstractDatabaseTest {

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
