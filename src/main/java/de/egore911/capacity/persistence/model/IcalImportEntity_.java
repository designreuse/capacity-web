package de.egore911.capacity.persistence.model;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import org.joda.time.LocalDateTime;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(IcalImportEntity.class)
public abstract class IcalImportEntity_ extends de.egore911.capacity.persistence.model.IntegerDbObject_ {

	public static volatile SingularAttribute<IcalImportEntity, String> name;
	public static volatile SingularAttribute<IcalImportEntity, LocalDateTime> lastImported;
	public static volatile SingularAttribute<IcalImportEntity, String> url;

}

