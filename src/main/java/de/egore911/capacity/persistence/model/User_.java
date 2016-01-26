package de.egore911.capacity.persistence.model;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(User.class)
public abstract class User_ extends de.egore911.capacity.persistence.model.IntegerDbObject_ {

	public static volatile SingularAttribute<User, String> password;
	public static volatile ListAttribute<User, Role> roles;
	public static volatile SingularAttribute<User, Boolean> hasPermission;
	public static volatile SingularAttribute<User, String> name;
	public static volatile SingularAttribute<User, Boolean> hasRole;
	public static volatile SingularAttribute<User, String> login;
	public static volatile SingularAttribute<User, String> email;

}

