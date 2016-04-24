package de.egore911.capacity.persistence.dao;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.hamcrest.core.IsNull.nullValue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import de.egore911.capacity.AbstractDatabaseTest;
import de.egore911.capacity.persistence.model.IntegerDbObject;
import de.egore911.persistence.dao.AbstractDao;
import de.egore911.persistence.util.EntityManagerUtil;

public abstract class AbstractDaoCRUDTest<T extends IntegerDbObject> extends AbstractDatabaseTest {

	private long count;

	protected abstract T createFixture();

	protected abstract void modifyFixture(T fixture);

	protected abstract AbstractDao<T> getDao();

	@Before
	public void before() {
		super.before();
		count = getDao().count();
	}

	@Test
	public void testCRUD() {

		// Create a fixture
		T absence = createFixture();

		// when: we create a fixture
		T created = getDao().save(absence);

		try {

			// then: we get back a fixture with an ID
			assertThat(created.getId(), notNullValue());

			// when: we modify a fixture
			modifyFixture(created);

			// then: we get an updated fixture that has the same ID
			T updated = getDao().save(absence);
			assertThat(updated.getId(), notNullValue());
			assertThat(updated.getId(), equalTo(created.getId()));

			// when: we load a fixture
			T loaded = getDao().findById(created.getId());

			// then: we get back the fixture matching the ID
			assertThat(loaded.getId(), equalTo(created.getId()));

			EntityManagerUtil.getEntityManager().clear();

			assertThat(EntityManagerUtil.getEntityManager().contains(loaded), is(false));

			loaded = getDao().reattach(loaded);

			assertThat(EntityManagerUtil.getEntityManager().contains(loaded), is(true));

		} finally {

			if (!EntityManagerUtil.getEntityManager().contains(created)) {
				created = getDao().reattach(created);
			}

			// when: we remove a fixture
			getDao().remove(created);

			// then: we can no longer find the fixture
			T loaded = getDao().findById(created.getId());
			assertThat(loaded, nullValue());
		}
	}

	@After
	public void after() {
		assertThat(count, equalTo(getDao().count()));
		super.after();
	}

}
