package de.egore911.capacity.ui.rest;

import javax.ws.rs.Path;

import org.apache.shiro.subject.Subject;

import de.egore911.capacity.persistence.dao.RoleDao;
import de.egore911.capacity.persistence.model.RoleEntity;
import de.egore911.capacity.persistence.selector.RoleSelector;
import de.egore911.capacity.ui.dto.Role;

@Path("role")
public class RoleService extends AbstractResourceService<Role, RoleEntity> {

	@Override
	protected Class<Role> getDtoClass() {
		return Role.class;
	}

	@Override
	protected Class<RoleEntity> getEntityClass() {
		return RoleEntity.class;
	}

	@Override
	protected RoleSelector getSelector(Subject subject) {
		return new RoleSelector();
	}

	@Override
	protected RoleDao getDao() {
		return new RoleDao();
	}

}
