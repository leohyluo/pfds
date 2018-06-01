package com.iebm.pfds.commons.datasource.fw_user;

import com.iebm.pfds.commons.datasource.DataSourceFactoryConfig;
import org.springframework.context.annotation.Import;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Import(DataSourceFactoryConfig.FwUserDataSource.class)
public @interface FwUserDataSource {

}
