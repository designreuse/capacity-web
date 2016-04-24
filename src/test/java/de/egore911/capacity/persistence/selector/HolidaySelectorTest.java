package de.egore911.capacity.persistence.selector;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.core.IsEqual.equalTo;

import java.util.List;

import org.hamcrest.Matchers;
import org.junit.Test;

import de.egore911.capacity.persistence.dao.LocationDao;
import de.egore911.capacity.persistence.model.HolidayEntity;
import de.egore911.capacity.persistence.model.LocationEntity;
import de.egore911.persistence.selector.AbstractSelector;

public class HolidaySelectorTest extends AbstractSelectorTest<HolidayEntity> {

	@Override
	protected AbstractSelector<HolidayEntity> getSelector() {
		return new HolidaySelector();
	}

	@Test
	public void testHolidaysAtLocation1() {
		LocationEntity location = new LocationDao().findById(1);
		List<HolidayEntity> holidays = new HolidaySelector().withOnlyLocation(location).findAll();
		assertThat(holidays, hasSize(2));

		assertThat(holidays, hasItem(Matchers.<HolidayEntity> hasProperty("name", equalTo("Some vacation 14"))));
		assertThat(holidays, hasItem(Matchers.<HolidayEntity> hasProperty("name", equalTo("Some vacation 16"))));
	}

	@Test
	public void testHolidaysAtLocation2() {
		LocationEntity location = new LocationDao().findById(2);
		List<HolidayEntity> holidays = new HolidaySelector().withOnlyLocation(location).findAll();

		assertThat(holidays, hasSize(2));

		assertThat(holidays, hasItem(Matchers.<HolidayEntity> hasProperty("name", equalTo("Some vacation 15"))));
		assertThat(holidays, hasItem(Matchers.<HolidayEntity> hasProperty("name", equalTo("Some vacation 16"))));
	}

	@Test
	public void testHolidaysAtLocation1Including() {
		LocationEntity location = new LocationDao().findById(1);
		List<HolidayEntity> holidays = new HolidaySelector().withIncludingLocation(location).findAll();

		assertThat(holidays, hasSize(3));

		assertThat(holidays, hasItem(Matchers.<HolidayEntity> hasProperty("name", equalTo("Some vacation 14"))));
		assertThat(holidays, hasItem(Matchers.<HolidayEntity> hasProperty("name", equalTo("Some vacation 16"))));
		assertThat(holidays, hasItem(Matchers.<HolidayEntity> hasProperty("name", equalTo("Some vacation 13"))));
	}

	@Test
	public void testHolidaysAtLocation2Including() {
		LocationEntity location = new LocationDao().findById(2);
		List<HolidayEntity> holidays = new HolidaySelector().withIncludingLocation(location).findAll();

		assertThat(holidays, hasSize(3));

		assertThat(holidays, hasItem(Matchers.<HolidayEntity> hasProperty("name", equalTo("Some vacation 15"))));
		assertThat(holidays, hasItem(Matchers.<HolidayEntity> hasProperty("name", equalTo("Some vacation 16"))));
		assertThat(holidays, hasItem(Matchers.<HolidayEntity> hasProperty("name", equalTo("Some vacation 13"))));
	}

}
