package de.egore911.capacity.ui;

import org.junit.Test;

import de.egore911.capacity.ui.rest.JerseyConfig;
import de.egore911.capacity.ui.rest.VersionService;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class VersionServiceTest extends AbstractUiTest {

	@Override
	protected JerseyConfig configure() {
		JerseyConfig application = super.configure();
		application.register(VersionService.class);
		return application;
	}

	@Test
	public void test() {
		String version = target("version").request().get(String.class);
		assertThat(version, equalTo("{\"maven\":\"?\",\"git\":\"\",\"buildTimestamp\":null}"));
	}

}
