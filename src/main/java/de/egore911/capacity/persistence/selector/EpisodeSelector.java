package de.egore911.capacity.persistence.selector;

import java.util.List;

import javax.annotation.Nonnull;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import de.egore911.capacity.persistence.model.EpisodeEntity;

public class EpisodeSelector extends AbstractResourceSelector<EpisodeEntity> {

	private static final long serialVersionUID = -583846840402271708L;

	@Override
	@Nonnull
	protected Class<EpisodeEntity> getEntityClass() {
		return EpisodeEntity.class;
	}

}
