package de.egore911.capacity.persistence.model;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import org.joda.time.LocalDate;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(EpisodeEntity.class)
public abstract class EpisodeEntity_ extends de.egore911.capacity.persistence.model.IntegerDbObject_ {

	public static volatile SingularAttribute<EpisodeEntity, LocalDate> start;
	public static volatile SingularAttribute<EpisodeEntity, String> name;
	public static volatile SingularAttribute<EpisodeEntity, LocalDate> end;

}

