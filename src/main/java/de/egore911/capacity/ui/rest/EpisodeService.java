package de.egore911.capacity.ui.rest;

import javax.ws.rs.Path;

import de.egore911.capacity.persistence.dao.EpisodeDao;
import de.egore911.capacity.persistence.model.EpisodeEntity;
import de.egore911.capacity.persistence.selector.EpisodeSelector;
import de.egore911.capacity.ui.dto.Episode;

@Path("episode")
public class EpisodeService extends AbstractResourceService<Episode, EpisodeEntity> {

	@Override
	protected Class<Episode> getDtoClass() {
		return Episode.class;
	}

	@Override
	protected Class<EpisodeEntity> getEntityClass() {
		return EpisodeEntity.class;
	}

	@Override
	protected EpisodeSelector getSelector() {
		return new EpisodeSelector();
	}

	@Override
	protected EpisodeDao getDao() {
		return new EpisodeDao();
	}

}
