package com.iebm.pfds.commons.datasource;

import com.iebm.pfds.commons.datasource.fw_user.FwUserMyBatisFactoryConfig;
import com.iebm.pfds.commons.datasource.qf_bizdb.BizdbMybatisConfig;
import com.iebm.pfds.commons.datasource.qf_frwdb.FrwdbMybatisConfig;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import javax.sql.DataSource;

public class DataSourceFactoryConfig {

	

	/**
	 * 业务库 数据源
	 * @author lhy
	 *
	 */
	@Configuration
	@Import(BizdbMybatisConfig.class)
	public static class BizdbDataSource {

		@Bean(name = "qfBizdbDataSource")
		@ConfigurationProperties(prefix = "spring.datasource.qf_bizdb")
		public DataSource qfBizdbDataSource() {
			return DataSourceBuilder.create().build();
		}

	}

	/**
	 * 框架库 数据源
	 * @author lhy
	 *
	 */
	@Configuration
	@Import(FrwdbMybatisConfig.class)
	public static class FrwdbDataSource {
		
		@Bean(name = "qfFrwdbDataSource")
		@ConfigurationProperties(prefix = "spring.datasource.qf_frwdb")
		public DataSource qfFrwdbDataSource() {
			return DataSourceBuilder.create().build();
		}
		
	}

	@Configuration
	@Import(FwUserMyBatisFactoryConfig.class)
	public static class FwUserDataSource {
		
		@Bean(name = "fwUserDataSource")
		@ConfigurationProperties(prefix = "spring.datasource.fw_user")
		public DataSource fwUserDataSource() {
			return DataSourceBuilder.create().build();
		}
	}

}