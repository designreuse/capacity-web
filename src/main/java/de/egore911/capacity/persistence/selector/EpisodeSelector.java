package de.egore911.capacity.persistence.selector;

import javax.annotation.Nonnull;

import de.egore911.capacity.persistence.model.EpisodeEntity;

public class EpisodeSelector extends AbstractResourceSelector<EpisodeEntity> {

	private static final long serialVersionUID = -583846840402271708L;

	@Override
	@Nonnull
	protected Class<EpisodeEntity> getEntityClass() {
		return EpisodeEntity.class;
	}

}
