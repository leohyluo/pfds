package com.iebm.pfds.commons.datasource.qf_frwdb;

import com.iebm.pfds.commons.datasource.DataSourceFactoryConfig;
import org.springframework.context.annotation.Import;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 框架库 数据源注解
 * 
 * @author Li Ning
 *
 */
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Import(DataSourceFactoryConfig.FrwdbDataSource.class)
public @interface FrwdbDataSource {
}