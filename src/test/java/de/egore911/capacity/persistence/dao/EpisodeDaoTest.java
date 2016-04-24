package de.egore911.capacity.persistence.dao;

import java.time.LocalDate;
import java.util.UUID;

import de.egore911.capacity.persistence.model.EpisodeEntity;

public class EpisodeDaoTest extends AbstractDaoCRUDTest<EpisodeEntity> {

	@Override
	protected EpisodeEntity createFixture() {
		EpisodeEntity episode = new EpisodeEntity();
		episode.setStart(LocalDate.now());
		episode.setEnd(LocalDate.now());
		episode.setName(UUID.randomUUID().toString());
		return episode;
	}

	@Override
	protected void modifyFixture(EpisodeEntity episode) {
		episode.setName(UUID.randomUUID().toString());
	}

	@Override
	protected EpisodeDao getDao() {
		return new EpisodeDao();
	}

}
