package de.egore911.capacity.persistence.model;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import org.joda.time.LocalDate;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(AbsenceEntity.class)
public abstract class AbsenceEntity_ extends de.egore911.capacity.persistence.model.IntegerDbObject_ {

	public static volatile SingularAttribute<AbsenceEntity, String> reason;
	public static volatile SingularAttribute<AbsenceEntity, LocalDate> start;
	public static volatile SingularAttribute<AbsenceEntity, EmployeeEntity> employee;
	public static volatile SingularAttribute<AbsenceEntity, LocalDate> end;

}

