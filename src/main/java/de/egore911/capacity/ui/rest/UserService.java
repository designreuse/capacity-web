package de.egore911.capacity.ui.rest;

import javax.ws.rs.Path;

import de.egore911.capacity.persistence.dao.UserDao;
import de.egore911.capacity.persistence.model.UserEntity;
import de.egore911.capacity.persistence.selector.UserSelector;
import de.egore911.capacity.ui.dto.User;

@Path("user")
public class UserService extends AbstractResourceService<User, UserEntity> {

	@Override
	protected Class<User> getDtoClass() {
		return User.class;
	}

	@Override
	protected Class<UserEntity> getEntityClass() {
		return UserEntity.class;
	}

	@Override
	protected UserSelector getSelector() {
		return new UserSelector();
	}

	@Override
	protected UserDao getDao() {
		return new UserDao();
	}

}
