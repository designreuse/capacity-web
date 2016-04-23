package de.egore911.capacity.ui;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.hamcrest.core.IsNull.nullValue;

import java.util.List;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.junit.Test;

import de.egore911.capacity.ui.dto.AbstractDto;

public abstract class AbstractCRUDTest<T extends AbstractDto> extends AbstractUiTest {

	protected abstract T createFixture();

	protected abstract String getPath();

	protected abstract Class<T> getFixtureClass();

	protected abstract void compareDtos(T fixture, T created);

	protected abstract void modifyFixture(T fixture);

	protected abstract GenericType<List<T>> getGenericType();

	@Test
	public void testCRUD() {
		String path = getPath();

		// Create an fixture fixture
		T fixture = createFixture();

		// Read the amount of existing stored elements
		List<T> list = target(path).request().get(getGenericType());
		final int preSize = list.size();

		// when: we create an fixture
		Entity<T> entity = Entity.entity(fixture, MediaType.APPLICATION_JSON);
		T created = target(path).request().post(entity, getFixtureClass());

		// then: we should get the created object
		Integer id = created.getId();
		assertThat(created.getId(), notNullValue());
		compareDtos(fixture, created);

		// and then: we should have one more stored element
		list = target(path).request().get(getGenericType());
		assertThat(list.size(), equalTo(preSize + 1));

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

		// and then: we should still have one more stored element
		list = target(path).request().get(getGenericType());
		assertThat(list.size(), equalTo(preSize + 1));

		// when: we delete the fixture
		target(path + "/" + id).request().delete();

		// then: we can no longer load the fixture
		T deleted = target(path + "/" + id).request().get(getFixtureClass());
		assertThat(deleted, nullValue());

		// and then: we should have the same amount of more stored elements as before the test
		list = target(path).request().get(getGenericType());
		assertThat(list.size(), equalTo(preSize));

		// when: we try do delete the removed entity again
		Response response = target(path + "/" + id).request().delete();

		// then: we get a HTTP 400
		assertThat(response.getStatus(), equalTo(Response.Status.BAD_REQUEST.getStatusCode()));
	}

}
