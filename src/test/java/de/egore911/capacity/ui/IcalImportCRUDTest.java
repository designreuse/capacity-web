package de.egore911.capacity.ui;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

import java.util.List;
import java.util.UUID;

import javax.ws.rs.core.GenericType;

import de.egore911.capacity.ui.dto.IcalImport;
import de.egore911.capacity.ui.dto.IcalImport.Auth;

public class IcalImportCRUDTest extends AbstractCRUDTest<IcalImport> {

	@Override
	protected IcalImport createFixture() {
		IcalImport fixture = new IcalImport();
		fixture.setName(UUID.randomUUID().toString());
		fixture.setUrl(UUID.randomUUID().toString());
		fixture.setAuth(Auth.NONE);
		return fixture;
	}

	@Override
	protected String getPath() {
		return "ical_import";
	}

	@Override
	protected Class<IcalImport> getFixtureClass() {
		return IcalImport.class;
	}

	@Override
	protected void modifyFixture(IcalImport fixture) {
		fixture.setUrl(UUID.randomUUID().toString());
	}

	@Override
	protected void compareDtos(IcalImport fixture, IcalImport created) {
		assertThat(created.getName(), equalTo(fixture.getName()));
		assertThat(created.getUrl(), equalTo(fixture.getUrl()));
	}

	@Override
	protected GenericType<List<IcalImport>> getGenericType() {
		return new GenericType<List<IcalImport>>() {
		};
	}

}
