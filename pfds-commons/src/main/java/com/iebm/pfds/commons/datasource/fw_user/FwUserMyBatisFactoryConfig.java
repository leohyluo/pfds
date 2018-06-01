package com.iebm.pfds.commons.datasource.fw_user;

import com.github.pagehelper.PageHelper;
import com.iebm.pfds.commons.datasource.MyBatisFactoryConfig;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.aop.framework.autoproxy.BeanNameAutoProxyCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.interceptor.TransactionInterceptor;

import javax.sql.DataSource;

public class FwUserMyBatisFactoryConfig extends MyBatisFactoryConfig {

	@Autowired
	private @Qualifier("fwUserDataSource") DataSource fwUserDataSource;

	@Value("${mybatis.fw_user.mapperLocations}")
	private String mapperLocations;

	// 提供SqlSession
	@Bean(name = "fwUserSqlSessionFactory")
	public SqlSessionFactory sqlSessionFactoryBean() {
		return super.sqlSessionFactoryBean(fwUserDataSource, mapperLocations);
	}

	@Bean("fwUserSessionConfig")
	protected org.apache.ibatis.session.Configuration configuration() {

		return super.configuration();
	}

	@Bean("fwUserPageHelper")
	public PageHelper pageHelper() {
		return super.pageHelper();
	}

	@Bean("fwUsertransactionInterceptor")
	public TransactionInterceptor transactionInterceptor(
			@Qualifier("fwUserDSTransactionManager") DataSourceTransactionManager transactionManager) {
		return super.transactionInterceptor(transactionManager);
	}

	@Bean("fwUserDSTransactionManager")
	public DataSourceTransactionManager annotationDrivenTransactionManager() {
		return new DataSourceTransactionManager(fwUserDataSource);
	}

	@Bean("fwUserAutoProxyCreator")
	public BeanNameAutoProxyCreator transactionAutoProxy() {
		return super.transactionAutoProxy(new String[] { "fwUsertransactionInterceptor" });
	}

}
