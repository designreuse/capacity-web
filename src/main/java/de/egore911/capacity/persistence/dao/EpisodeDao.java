package de.egore911.capacity.persistence.dao;

import de.egore911.capacity.persistence.model.EpisodeEntity;
import de.egore911.capacity.persistence.selector.EpisodeSelector;
import de.egore911.persistence.dao.AbstractDao;

public class EpisodeDao extends AbstractDao<EpisodeEntity>{

	@Override
	protected Class<EpisodeEntity> getEntityClass() {
		return EpisodeEntity.class;
	}

	@Override
	protected EpisodeSelector createSelector() {
		return new EpisodeSelector();
	}

}
