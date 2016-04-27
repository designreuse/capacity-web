package de.egore911.capacity.ui;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNull.nullValue;

import java.util.List;
import java.util.UUID;

import javax.ws.rs.core.GenericType;

import de.egore911.capacity.ui.dto.User;

public class UserCRUDTest extends AbstractCRUDTest<User> {

	@Override
	protected User createFixture() {
		User fixture = new User();
		fixture.setName(UUID.randomUUID().toString());
		fixture.setLogin(UUID.randomUUID().toString());
		fixture.setEmail(UUID.randomUUID().toString());
		fixture.setPassword("0000000000111111111122222222223333333333");
		return fixture;
	}

	@Override
	protected String getPath() {
		return "user";
	}

	@Override
	protected Class<User> getFixtureClass() {
		return User.class;
	}

	@Override
	protected void modifyFixture(User fixture) {
		fixture.setName(UUID.randomUUID().toString());
	}

	@Override
	protected void compareDtos(User fixture, User created) {
		assertThat(created.getName(), equalTo(fixture.getName()));
		assertThat(created.getLogin(), equalTo(fixture.getLogin()));
		assertThat(created.getEmail(), equalTo(fixture.getEmail()));
		assertThat(created.getPassword(), nullValue());
		assertThat(fixture.getPassword(), equalTo("0000000000111111111122222222223333333333"));
	}

	@Override
	protected GenericType<List<User>> getGenericType() {
		return new GenericType<List<User>>() {
		};
	}

}
