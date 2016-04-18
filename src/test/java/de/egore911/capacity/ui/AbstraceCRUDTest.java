package de.egore911.capacity.ui;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.hamcrest.core.IsNull.nullValue;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;

import org.junit.Test;

import de.egore911.capacity.ui.dto.AbstractDto;

public abstract class AbstraceCRUDTest<T extends AbstractDto> extends AbstractUiTest {

	protected abstract T createFixture();

	protected abstract String getPath();

	protected abstract Class<T> getFixtureClass();

	protected abstract void compareDtos(T fixture, T created);

	protected abstract void modifyFixture(T fixture);

	@Test
	public void testCRUD() {
		String path = getPath();

		// Create an fixture fixture
		T fixture = createFixture();

		// when: we create an fixture
		Entity<T> entity = Entity.entity(fixture, MediaType.APPLICATION_JSON);
		T created = target(path).request().post(entity, getFixtureClass());

		// then: we should get the created object
		Integer id = created.getId();
		assertThat(created.getId(), notNullValue());
		compareDtos(fixture, created);

		// when: we load the object by id
		T loaded = target(path + "/" + id).request().get(getFixtureClass());

		// then: we get back the identical object that was just created
		compareDtos(fixture, loaded);

		// Modify our fixture
		fixture.setId(id);
		modifyFixture(fixture);

		// when: we update the fixture
		entity = Entity.entity(fixture, MediaType.APPLICATION_JSON);
		target(path + "/" + id).request().put(entity, Integer.class);

		// then: we should have an updated fixture
		T updated = target(path + "/" + id).request().get(getFixtureClass());
		compareDtos(fixture, updated);

		// when: we delete the fixture
		target(path + "/" + id).request().delete();

		// then: we can no longer load the fixture
		T deleted = target(path + "/" + id).request().get(getFixtureClass());
		assertThat(deleted, nullValue());
	}

}
