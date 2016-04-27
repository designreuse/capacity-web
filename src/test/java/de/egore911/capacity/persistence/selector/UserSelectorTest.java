package de.egore911.capacity.persistence.selector;

import de.egore911.capacity.persistence.model.UserEntity;
import de.egore911.persistence.selector.AbstractSelector;

public class UserSelectorTest extends AbstractSelectorTest<UserEntity> {

	@Override
	protected AbstractSelector<UserEntity> getSelector() {
		return new UserSelector();
	}

}
