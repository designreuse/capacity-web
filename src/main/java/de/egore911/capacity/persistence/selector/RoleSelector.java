package de.egore911.capacity.persistence.selector;

import java.util.List;

import javax.annotation.Nonnull;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.StringUtils;

import de.egore911.capacity.persistence.model.RoleEntity;
import de.egore911.capacity.persistence.model.RoleEntity_;
import de.egore911.capacity.persistence.model.UserEntity_;

public class RoleSelector extends AbstractResourceSelector<RoleEntity> {

	private static final long serialVersionUID = -6277913360430683665L;

	private String userLogin;

	@Nonnull
	@Override
	protected Class<RoleEntity> getEntityClass() {
		return RoleEntity.class;
	}

	public RoleSelector withUserLogin(String userLogin) {
		this.userLogin = userLogin;
		return this;
	}

	@Override
	protected List<Predicate> generatePredicateList(CriteriaBuilder builder, Root<RoleEntity> from,
			CriteriaQuery<?> criteriaQuery) {
		List<Predicate> predicates = super.generatePredicateList(builder, from, criteriaQuery);

		if (StringUtils.isNotEmpty(userLogin)) {
			predicates.add(builder.equal(from.join(RoleEntity_.users).get(UserEntity_.login), userLogin));
		}

		return predicates;
	}

}
