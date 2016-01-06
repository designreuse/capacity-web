package de.egore911.capacity.persistence.model;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import org.joda.time.LocalTime;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(WorkingHoursEntity.class)
public abstract class WorkingHoursEntity_ {

	public static volatile SingularAttribute<WorkingHoursEntity, Integer> dayOfWeek;
	public static volatile SingularAttribute<WorkingHoursEntity, LocalTime> start;
	public static volatile SingularAttribute<WorkingHoursEntity, LocalTime> end;

}

