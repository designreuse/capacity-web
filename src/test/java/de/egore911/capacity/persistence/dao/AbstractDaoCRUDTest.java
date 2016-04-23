package de.egore911.capacity.persistence.dao;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import de.egore911.capacity.AbstractDatabaseTest;
import de.egore911.capacity.persistence.model.IntegerDbObject;
import de.egore911.persistence.dao.AbstractDao;

public abstract class AbstractDaoCRUDTest<T extends IntegerDbObject> extends AbstractDatabaseTest {

	protected abstract T createFixture();

	protected abstract void modifyFixture(T fixture);

	protected abstract AbstractDao<T> getDao();

	@Test
	public void testCRUD() {
		T absence = createFixture();

		T created = getDao().save(absence);

		assertThat(created.getId(), notNullValue());

		modifyFixture(created);

		T updated = getDao().save(absence);

		assertThat(updated.getId(), notNullValue());
		assertThat(updated.getId(), equalTo(created.getId()));
	}

}
