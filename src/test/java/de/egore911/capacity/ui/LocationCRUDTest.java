package de.egore911.capacity.ui;

import de.egore911.capacity.ui.dto.Location;

import javax.ws.rs.core.GenericType;
import java.util.List;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

public class LocationCRUDTest extends AbstractCRUDTest<Location> {

	@Override
	protected Location createFixture() {
		Location fixture = new Location();
		fixture.setName(UUID.randomUUID().toString());
		return fixture;
	}

	@Override
	protected String getPath() {
		return "location";
	}

	@Override
	protected Class<Location> getFixtureClass() {
		return Location.class;
	}

	@Override
	protected void modifyFixture(Location fixture) {
		fixture.setName(UUID.randomUUID().toString());
	}

	@Override
	protected void compareDtos(Location fixture, Location created) {
		assertThat(created.getName(), equalTo(fixture.getName()));
	}

	@Override
	protected GenericType<List<Location>> getGenericType() {
		return new GenericType<List<Location>>() {
		};
	}

}
