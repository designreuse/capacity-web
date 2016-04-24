package de.egore911.capacity.persistence.model;

import java.time.LocalDate;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(HolidayEntity.class)
public abstract class HolidayEntity_ extends de.egore911.capacity.persistence.model.IntegerDbObject_ {

	public static volatile SingularAttribute<HolidayEntity, LocalDate> date;
	public static volatile SingularAttribute<HolidayEntity, Integer> hoursReduction;
	public static volatile SingularAttribute<HolidayEntity, String> name;
	public static volatile ListAttribute<HolidayEntity, LocationEntity> locations;

}

