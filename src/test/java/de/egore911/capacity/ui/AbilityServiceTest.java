package de.egore911.capacity.ui;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

import org.junit.Test;

public class AbilityServiceTest extends AbstractUiTest {

	@Test
	public void loadAbilities() {
		String abilities = target("abilities").request().get(String.class);

		assertThat(abilities, equalTo("[{\"name\":\"Relaxing\"},{\"name\":\"Working\"}]"));
	}
}
