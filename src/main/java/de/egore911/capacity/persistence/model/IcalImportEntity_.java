package de.egore911.capacity.persistence.model;

import de.egore911.capacity.persistence.model.IcalImportEntity.Auth;
import java.time.LocalDateTime;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(IcalImportEntity.class)
public abstract class IcalImportEntity_ extends de.egore911.capacity.persistence.model.IntegerDbObject_ {

	public static volatile SingularAttribute<IcalImportEntity, String> password;
	public static volatile SingularAttribute<IcalImportEntity, Auth> auth;
	public static volatile SingularAttribute<IcalImportEntity, String> name;
	public static volatile SingularAttribute<IcalImportEntity, LocalDateTime> lastImported;
	public static volatile SingularAttribute<IcalImportEntity, String> url;
	public static volatile SingularAttribute<IcalImportEntity, String> username;

}

