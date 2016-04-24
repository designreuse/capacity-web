package de.egore911.capacity.persistence.model;

import java.time.LocalDate;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(EpisodeEntity.class)
public abstract class EpisodeEntity_ extends de.egore911.capacity.persistence.model.IntegerDbObject_ {

	public static volatile ListAttribute<EpisodeEntity, EmployeeEpisodeEntity> employeeEpisodes;
	public static volatile SingularAttribute<EpisodeEntity, LocalDate> start;
	public static volatile SingularAttribute<EpisodeEntity, String> name;
	public static volatile SingularAttribute<EpisodeEntity, LocalDate> end;

}

