package com.iebm.pfds.commons.datasource.qf_frwdb;

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

public class FrwdbMybatisConfig extends MyBatisFactoryConfig {

	@Autowired
	private @Qualifier("qfFrwdbDataSource") DataSource qfFrwdbDataSource;

	@Value("${mybatis.qf_frwdb.mapperLocations}")
	private String mapperLocations;

	// 提供SqlSession
	@Bean(name = "qfFrwdbSqlSessionFactory")
	public SqlSessionFactory sqlSessionFactoryBean() {
		return super.sqlSessionFactoryBean(qfFrwdbDataSource, mapperLocations);
	}

	@Bean("qfFrwdbSessionConfig")
	protected org.apache.ibatis.session.Configuration configuration() {

		return super.configuration();
	}

	@Bean("qfFrwdbPageHelper")
	public PageHelper pageHelper() {
		return super.pageHelper();
	}

	@Bean("qfFrwdbtransactionInterceptor")
	public TransactionInterceptor transactionInterceptor(
			@Qualifier("qfFrwdbDSTransactionManager") DataSourceTransactionManager transactionManager) {
		return super.transactionInterceptor(transactionManager);
	}

	@Bean("qfFrwdbDSTransactionManager")
	public DataSourceTransactionManager annotationDrivenTransactionManager() {
		return new DataSourceTransactionManager(qfFrwdbDataSource);
	}

	@Bean("qfFrwdbAutoProxyCreator")
	public BeanNameAutoProxyCreator transactionAutoProxy() {
		return super.transactionAutoProxy(new String[] { "qfFrwdbtransactionInterceptor" });
	}

}
