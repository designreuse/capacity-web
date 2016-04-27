package de.egore911.capacity.persistence.model;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(RoleEntity.class)
public abstract class RoleEntity_ extends de.egore911.capacity.persistence.model.IntegerDbObject_ {

	public static volatile ListAttribute<RoleEntity, Permission> permissions;
	public static volatile SingularAttribute<RoleEntity, String> name;
	public static volatile ListAttribute<RoleEntity, UserEntity> users;

}

