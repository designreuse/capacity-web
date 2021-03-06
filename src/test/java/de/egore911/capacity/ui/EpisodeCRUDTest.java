package de.egore911.capacity.ui;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import javax.ws.rs.core.GenericType;

import de.egore911.capacity.ui.dto.Episode;

public class EpisodeCRUDTest extends AbstractCRUDTest<Episode> {

	@Override
	protected Episode createFixture() {
		Episode fixture = new Episode();
		fixture.setStart(LocalDate.now());
		fixture.setEnd(LocalDate.now());
		fixture.setName(UUID.randomUUID().toString());
		fixture.setEmployeeEpisodes(Collections.emptyList());
		return fixture;
	}

	@Override
	protected String getPath() {
		return "episode";
	}

	@Override
	protected Class<Episode> getFixtureClass() {
		return Episode.class;
	}

	@Override
	protected void modifyFixture(Episode fixture) {
		fixture.setName(UUID.randomUUID().toString());
	}

	@Override
	protected void compareDtos(Episode fixture, Episode created) {
		assertThat(created.getStart(), equalTo(fixture.getStart()));
		assertThat(created.getEnd(), equalTo(fixture.getEnd()));
		assertThat(created.getName(), equalTo(fixture.getName()));
	}

	@Override
	protected GenericType<List<Episode>> getGenericType() {
		return new GenericType<List<Episode>>() {
		};
	}

}
