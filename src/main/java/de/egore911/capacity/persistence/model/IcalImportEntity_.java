package de.egore911.capacity.persistence.model;

import java.time.LocalDateTime;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(IcalImportEntity.class)
public abstract class IcalImportEntity_ extends de.egore911.capacity.persistence.model.IntegerDbObject_ {

	public static volatile SingularAttribute<IcalImportEntity, String> name;
	public static volatile SingularAttribute<IcalImportEntity, LocalDateTime> lastImported;
	public static volatile SingularAttribute<IcalImportEntity, String> url;

}

