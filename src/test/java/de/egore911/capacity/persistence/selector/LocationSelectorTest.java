package de.egore911.capacity.persistence.selector;

import de.egore911.capacity.persistence.model.LocationEntity;
import de.egore911.persistence.selector.AbstractSelector;

public class LocationSelectorTest extends AbstractSelectorTest<LocationEntity> {

	@Override
	protected AbstractSelector<LocationEntity> getSelector() {
		return new LocationSelector();
	}

}
