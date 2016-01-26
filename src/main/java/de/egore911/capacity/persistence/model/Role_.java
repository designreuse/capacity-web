package de.egore911.capacity.persistence.model;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Role.class)
public abstract class Role_ extends de.egore911.capacity.persistence.model.IntegerDbObject_ {

	public static volatile ListAttribute<Role, Permission> permissions;
	public static volatile SingularAttribute<Role, Boolean> hasPermission;
	public static volatile SingularAttribute<Role, String> name;
	public static volatile ListAttribute<Role, User> users;

}

