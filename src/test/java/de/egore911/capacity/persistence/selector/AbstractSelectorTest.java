package de.egore911.capacity.persistence.selector;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.number.OrderingComparison.greaterThanOrEqualTo;

import java.util.List;

import org.junit.Assume;
import org.junit.Test;

import de.egore911.capacity.AbstractDatabaseTest;
import de.egore911.persistence.selector.AbstractSelector;

public abstract class AbstractSelectorTest<T> extends AbstractDatabaseTest {

	protected abstract AbstractSelector<T> getSelector();

	@Test
	public void testFindAll() {
		AbstractSelector<T> selector = getSelector();
		List<T> entities = selector.findAll();

		assertThat(entities, hasSize((int) selector.count()));
	}

	@Test
	public void testOffsetAndLimit() {
		int max = (int) getSelector().count();

		Assume.assumeThat(max, greaterThanOrEqualTo(3));

		assertThat(getSelector().findAll(), hasSize(max));
		assertThat(getSelector().withOffset(1).findAll(), hasSize(max - 1));
		assertThat(getSelector().withOffset(max).findAll(), hasSize(0));

		assertThat(getSelector().withLimit(2).findAll(), hasSize(2));
		assertThat(getSelector().withOffset(max - 2).findAll(), hasSize(2));
		assertThat(getSelector().withOffset(max - 1).findAll(), hasSize(1));
		assertThat(getSelector().withOffset(max).findAll(), hasSize(0));
	}
}
