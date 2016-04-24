package de.egore911.capacity.persistence.model;

import java.time.LocalTime;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(WorkingHoursEntity.class)
public abstract class WorkingHoursEntity_ {

	public static volatile SingularAttribute<WorkingHoursEntity, Integer> dayOfWeek;
	public static volatile SingularAttribute<WorkingHoursEntity, LocalTime> start;
	public static volatile SingularAttribute<WorkingHoursEntity, LocalTime> end;

}

