package de.egore911.capacity.ui.rest;

import javax.annotation.Nonnull;
import javax.ws.rs.Path;

import de.egore911.capacity.persistence.dao.UserDao;
import de.egore911.capacity.persistence.model.UserEntity;
import de.egore911.capacity.persistence.selector.UserSelector;
import de.egore911.capacity.ui.dto.User;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.codec.Hex;
import org.apache.shiro.crypto.hash.Sha1Hash;
import org.apache.shiro.subject.Subject;
import org.secnod.shiro.jaxrs.Auth;

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
	protected UserSelector getSelector(Subject subject) {
		return new UserSelector();
	}

	@Override
	protected UserDao getDao() {
		return new UserDao();
	}

	@Override
	public User create(User user, @Auth Subject subject) {
		hashPassword(user);
		return super.create(user, subject);
	}

	@Override
	public void update(@Nonnull Integer id, User user, @Auth Subject subject) {
		hashPassword(user);
		super.update(id, user, subject);
	}

	private void hashPassword(User user) {
		if (StringUtils.isNotEmpty(user.getPassword())) {
			user.setPassword(new String(Hex.encode(new Sha1Hash(user.getPassword()).getBytes())));
		}
	}
}
