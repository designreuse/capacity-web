package de.egore911.capacity.persistence.model;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(EmployeeEntity.class)
public abstract class EmployeeEntity_ extends de.egore911.capacity.persistence.model.IntegerDbObject_ {

	public static volatile SetAttribute<EmployeeEntity, AbilityEntity> abilities;
	public static volatile SingularAttribute<EmployeeEntity, LocationEntity> location;
	public static volatile SingularAttribute<EmployeeEntity, String> color;
	public static volatile SingularAttribute<EmployeeEntity, ContractEntity> contract;
	public static volatile SingularAttribute<EmployeeEntity, String> email;
	public static volatile SingularAttribute<EmployeeEntity, String> name;
	public static volatile ListAttribute<EmployeeEntity, AbsenceEntity> absences;

}

