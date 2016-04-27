package de.egore911.capacity.persistence.selector;

import javax.annotation.Nonnull;

import de.egore911.capacity.persistence.model.RoleEntity;

public class RoleSelector extends AbstractResourceSelector<RoleEntity> {

	private static final long serialVersionUID = -6277913360430683665L;

	@Nonnull
	@Override
	protected Class<RoleEntity> getEntityClass() {
		return RoleEntity.class;
	}

}
