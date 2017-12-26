package com.iebm.pfds.commons.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DatabaseDriver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.alibaba.druid.pool.DruidDataSource;

@Configuration
public class DruidConfiguration {
	
	private static final Logger logger = LoggerFactory.getLogger(DruidConfiguration.class);
	
	@Value("${db.host}")
	private String ip;
	
	@Value("${db.port}")
	private String port;
	
	@Value("${db.name}")
	private String dbName;


	@ConditionalOnClass(DruidDataSource.class)
	@ConditionalOnProperty(name="spring.datasource.type", havingValue="com.alibaba.druid.pool.DruidDataSource", matchIfMissing=true)
	class Druid extends DruidConfiguration {
		
		@Bean
		@ConfigurationProperties("spring.datasource.druid")
		public DruidDataSource dataSource(DataSourceProperties properties) {
			DruidDataSource druidDataSource = (DruidDataSource) properties.initializeDataSourceBuilder().type(DruidDataSource.class).build();
			DatabaseDriver databaseDriver = DatabaseDriver.fromJdbcUrl(properties.determineUrl());
			String validationQuery = databaseDriver.getValidationQuery();
			if(validationQuery != null) {
				druidDataSource.setValidationQuery(validationQuery);
			}
			logger.info("DruidConfiguration init completed................");
			return druidDataSource;
		}
	}
}
