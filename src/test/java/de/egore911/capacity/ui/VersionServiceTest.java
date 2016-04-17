package de.egore911.capacity.ui;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

import org.junit.Test;

public class VersionServiceTest extends AbstractUiTest {

	@Test
	public void test() {
		String version = target("version").request().get(String.class);
		assertThat(version, equalTo("{\"maven\":\"?\",\"git\":\"\",\"buildTimestamp\":null}"));
	}

}
