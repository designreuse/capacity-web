package de.egore911.capacity.persistence.selector;

import javax.annotation.Nonnull;

import de.egore911.capacity.persistence.model.LocationEntity;

public class LocationSelector extends AbstractResourceSelector<LocationEntity> {

	private static final long serialVersionUID = -4291390496649347890L;

	@Nonnull
	@Override
	protected Class<LocationEntity> getEntityClass() {
		return LocationEntity.class;
	}

}
