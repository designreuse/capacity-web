package de.egore911.capacity.persistence.selector;

import de.egore911.capacity.persistence.model.IcalImportEntity;
import de.egore911.persistence.selector.AbstractSelector;

public class IcalImportSelectorTest extends AbstractSelectorTest<IcalImportEntity> {

	@Override
	protected AbstractSelector<IcalImportEntity> getSelector() {
		return new IcalImportSelector();
	}

}
