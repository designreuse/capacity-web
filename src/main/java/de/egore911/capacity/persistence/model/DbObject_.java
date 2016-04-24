package de.egore911.capacity.persistence.model;

import java.time.LocalDateTime;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(DbObject.class)
public abstract class DbObject_ {

	public static volatile SingularAttribute<DbObject, UserEntity> createdBy;
	public static volatile SingularAttribute<DbObject, LocalDateTime> created;
	public static volatile SingularAttribute<DbObject, LocalDateTime> modified;
	public static volatile SingularAttribute<DbObject, UserEntity> modifiedBy;

}

