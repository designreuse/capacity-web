package de.egore911.capacity.ui;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

import java.util.List;
import java.util.UUID;

import javax.ws.rs.core.GenericType;

import de.egore911.capacity.ui.dto.Role;

public class RoleCRUDTest extends AbstractCRUDTest<Role> {

	@Override
	protected Role createFixture() {
		Role fixture = new Role();
		fixture.setName(UUID.randomUUID().toString());
		return fixture;
	}

	@Override
	protected String getPath() {
		return "role";
	}

	@Override
	protected Class<Role> getFixtureClass() {
		return Role.class;
	}

	@Override
	protected void modifyFixture(Role fixture) {
		fixture.setName(UUID.randomUUID().toString());
	}

	@Override
	protected void compareDtos(Role fixture, Role created) {
		assertThat(created.getName(), equalTo(fixture.getName()));
	}

	@Override
	protected GenericType<List<Role>> getGenericType() {
		return new GenericType<List<Role>>() {
		};
	}

}
