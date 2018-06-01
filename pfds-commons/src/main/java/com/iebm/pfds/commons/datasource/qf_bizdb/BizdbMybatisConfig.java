package com.iebm.pfds.commons.datasource.qf_bizdb;

import com.github.pagehelper.PageHelper;
import com.iebm.pfds.commons.datasource.MyBatisFactoryConfig;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.aop.framework.autoproxy.BeanNameAutoProxyCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.interceptor.TransactionInterceptor;

import javax.sql.DataSource;

@Configuration
public class BizdbMybatisConfig extends MyBatisFactoryConfig {

	@Autowired
	private @Qualifier("qfBizdbDataSource") DataSource qfBizdbDataSource;

	@Value("${mybatis.qf_bizdb.mapperLocations}")
	private String mapperLocations;

	// 提供SqlSession
	@Bean(name = "qfBizdbSqlSessionFactory")
	public SqlSessionFactory sqlSessionFactoryBean() {
		return super.sqlSessionFactoryBean(qfBizdbDataSource, mapperLocations);
	}

	@Bean("qfBizdbSessionConfig")
	protected org.apache.ibatis.session.Configuration configuration() {

		return super.configuration();
	}
	@Bean("qfBizdbPageHelper")
	public PageHelper pageHelper() {
		return super.pageHelper();
	}

	@Bean("qfBizdbtransactionInterceptor")
	public TransactionInterceptor transactionInterceptor(
			@Qualifier("qfBizdbDSTransactionManager") DataSourceTransactionManager transactionManager) {
		return super.transactionInterceptor(transactionManager);
	}

	@Bean("qfBizdbDSTransactionManager")
	public DataSourceTransactionManager annotationDrivenTransactionManager() {
		return new DataSourceTransactionManager(qfBizdbDataSource);
	}

	@Bean("qfBizdbAutoProxyCreator")
	public BeanNameAutoProxyCreator transactionAutoProxy() {
		return super.transactionAutoProxy(new String[] { "qfBizdbtransactionInterceptor" });
	}

}
