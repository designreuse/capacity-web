package de.egore911.capacity.persistence.selector;

import de.egore911.capacity.persistence.model.RoleEntity;
import de.egore911.persistence.selector.AbstractSelector;

public class RoleSelectorTest extends AbstractSelectorTest<RoleEntity> {

	@Override
	protected AbstractSelector<RoleEntity> getSelector() {
		return new RoleSelector();
	}

}
