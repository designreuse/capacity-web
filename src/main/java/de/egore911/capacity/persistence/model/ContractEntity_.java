package de.egore911.capacity.persistence.model;

import java.time.LocalDate;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(ContractEntity.class)
public abstract class ContractEntity_ {

	public static volatile SingularAttribute<ContractEntity, LocalDate> start;
	public static volatile SingularAttribute<ContractEntity, LocalDate> end;
	public static volatile SingularAttribute<ContractEntity, Integer> vacationDaysPerYear;
	public static volatile ListAttribute<ContractEntity, WorkingHoursEntity> workingHours;

}

