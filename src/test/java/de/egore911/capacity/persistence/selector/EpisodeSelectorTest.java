package de.egore911.capacity.persistence.selector;

import de.egore911.capacity.persistence.model.EpisodeEntity;
import de.egore911.persistence.selector.AbstractSelector;

public class EpisodeSelectorTest extends AbstractSelectorTest<EpisodeEntity> {

	@Override
	protected AbstractSelector<EpisodeEntity> getSelector() {
		return new EpisodeSelector();
	}

}
