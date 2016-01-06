package de.egore911.capacity.persistence.model;

import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(LocationEntity.class)
public abstract class LocationEntity_ extends de.egore911.capacity.persistence.model.IntegerDbObject_ {

	public static volatile SetAttribute<LocationEntity, HolidayEntity> holidays;
	public static volatile SingularAttribute<LocationEntity, String> name;
	public static volatile SetAttribute<LocationEntity, EmployeeEntity> employees;

}

