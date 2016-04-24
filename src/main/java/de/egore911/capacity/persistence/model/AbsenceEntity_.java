package de.egore911.capacity.persistence.model;

import java.time.LocalDate;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(AbsenceEntity.class)
public abstract class AbsenceEntity_ extends de.egore911.capacity.persistence.model.IntegerDbObject_ {

	public static volatile SingularAttribute<AbsenceEntity, String> reason;
	public static volatile SingularAttribute<AbsenceEntity, IcalImportEntity> icalImport;
	public static volatile SingularAttribute<AbsenceEntity, LocalDate> start;
	public static volatile SingularAttribute<AbsenceEntity, String> externalId;
	public static volatile SingularAttribute<AbsenceEntity, LocalDate> end;
	public static volatile SingularAttribute<AbsenceEntity, EmployeeEntity> employee;

}

