package com.iebm.pfds.commons.datasource;

import com.github.pagehelper.PageHelper;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.aop.framework.autoproxy.BeanNameAutoProxyCreator;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.interceptor.TransactionInterceptor;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.Properties;

public class MyBatisFactoryConfig {
	
	private static Log logger = LogFactory.getLog(MyBatisFactoryConfig.class);
	
	protected SqlSessionFactory sqlSessionFactoryBean(DataSource ds , String mapperLocations) {
		try {
			SqlSessionFactoryBean sessionFactoryBean = new SqlSessionFactoryBean();

			sessionFactoryBean.setDataSource(ds);
			// 配置类型别名
			sessionFactoryBean.setTypeAliasesPackage("");

			// 配置mapper的扫描， 找到所有的mapper.xml映射文件
			Resource[] resources = new PathMatchingResourcePatternResolver().getResources(mapperLocations);
			sessionFactoryBean.setMapperLocations(resources);

			// 加载全局配置
			sessionFactoryBean.setConfiguration(configuration());

			// 加载插件
			sessionFactoryBean.setPlugins(new Interceptor[] { pageHelper() });

			return sessionFactoryBean.getObject();
		} catch (IOException e) {
			logger.warn("mybatis resolver mapper*xml error , " + e.getMessage());
			 throw new RuntimeException(e);
			
		} catch (Exception e) {
			logger.warn("mybatis sqlSessionFacotryBean create error , " + e.getMessage());
			 throw new RuntimeException(e);
			
		}
	}

	protected MapperScannerConfigurer mapperScannerConfigurer(String beanName, String basePackage) {
		MapperScannerConfigurer mapperScannerConfigurer = new MapperScannerConfigurer();

		mapperScannerConfigurer.setSqlSessionFactoryBeanName(beanName);
		mapperScannerConfigurer.setBasePackage(basePackage);

		return mapperScannerConfigurer;
	}

	protected org.apache.ibatis.session.Configuration configuration() {
		// MyBatis 配置类
		org.apache.ibatis.session.Configuration mybatisConfig = new org.apache.ibatis.session.Configuration();
		// 开启驼峰匹配
		mybatisConfig.setMapUnderscoreToCamelCase(true);
		// 这个配置使全局的映射器启用或禁用缓存。系统默认值是true，设置只是为了展示出来
		mybatisConfig.setCacheEnabled(true);
		// 全局启用或禁用延迟加载。当禁用时，所有关联对象都会即时加载。 系统默认值是true，设置只是为了展示出来
		mybatisConfig.setLazyLoadingEnabled(true);
		// 允许或不允许多种结果集从一个单独的语句中返回（需要适合的驱动）。 系统默认值是true，设置只是为了展示出来
		mybatisConfig.setMultipleResultSetsEnabled(true);
		// 使用列标签代替列名。不同的驱动在这方便表现不同。参考驱动文档或充分测试两种方法来决定所使用的驱动。
		// 系统默认值是true，设置只是为了展示出来
		mybatisConfig.setUseColumnLabel(true);
		// 允许 JDBC 支持生成的键。需要适合的驱动。如果设置为 true
		// 则这个设置强制生成的键被使用，尽管一些驱动拒绝兼容但仍然有效（比如 Derby）。
		// 系统默认值是false，设置只是为了展示出来
		mybatisConfig.setUseGeneratedKeys(true);
		// 配置默认的执行器。SIMPLE 执行器没有什么特别之处。REUSE 执行器重用预处理语句。BATCH
		// 执行器重用语句和批量更新 系统默认值是SIMPLE，设置只是为了展示出来
		mybatisConfig.setDefaultExecutorType(ExecutorType.SIMPLE);
		// 设置超时时间，它决定驱动等待一个数据库响应的时间。 系统默认值是null，设置只是为了展示出来
		mybatisConfig.setDefaultStatementTimeout(25000);
		
		// TODO 测试使用后期删掉
		//mybatisConfig.addInterceptor(new SqlInterceptor());

		return mybatisConfig;
	}

	protected PageHelper pageHelper() {
		// 分页插件
		PageHelper pageHelper = new PageHelper();
		Properties properties = new Properties();
		properties.setProperty("offsetAsPageNum", "true");
		properties.setProperty("rowBoundsWithCount", "true");
		properties.setProperty("reasonable", "true");
		properties.setProperty("supportMethodsArguments", "true");
		properties.setProperty("returnPageInfo", "check");
		properties.setProperty("params", "count=countSql");
		pageHelper.setProperties(properties);
		return pageHelper;
	}

	protected TransactionInterceptor transactionInterceptor(DataSourceTransactionManager transactionManager) {
		TransactionInterceptor ti = new TransactionInterceptor();
		ti.setTransactionManager(transactionManager);
		Properties properties = new Properties();
		/*properties.setProperty("query*", "PROPAGATION_REQUIRED, readOnly");
		properties.setProperty("find*", "PROPAGATION_REQUIRED, readOnly");
		properties.setProperty("load*", "PROPAGATION_REQUIRED, readOnly");
		properties.setProperty("check*", "PROPAGATION_REQUIRED, readOnly");*/
		
		/********************* TODO Spring security oauth2 不知道那里调用了get?导致连接是只读，去掉 后期排错 ****************************/
		/*properties.setProperty("get*", "PROPAGATION_REQUIRED");*/
		properties.setProperty("add*", "PROPAGATION_REQUIRED");
		properties.setProperty("save*", "PROPAGATION_REQUIRED");
		properties.setProperty("clear*", "PROPAGATION_REQUIRED");
		properties.setProperty("insert*", "PROPAGATION_REQUIRED");
		properties.setProperty("delete*", "PROPAGATION_REQUIRED");
		properties.setProperty("update*", "PROPAGATION_REQUIRED");
		properties.setProperty("create*", "PROPAGATION_REQUIRED");
		
		ti.setTransactionAttributes(properties);

		return ti;
	}

	protected BeanNameAutoProxyCreator transactionAutoProxy(String... interceptorNames) {
		BeanNameAutoProxyCreator transactionAutoProxy = new BeanNameAutoProxyCreator();
		transactionAutoProxy.setProxyTargetClass(false);
		transactionAutoProxy.setBeanNames(new String[] { "*ServiceImpl" });
		transactionAutoProxy.setInterceptorNames(interceptorNames);

		return transactionAutoProxy;
	}

}
