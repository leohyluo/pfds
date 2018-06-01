package com.iebm.pfds.commons.datasource;

import com.github.pagehelper.PageHelper;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.type.JdbcType;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.aop.framework.autoproxy.BeanNameAutoProxyCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.bind.RelaxedPropertyResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.TransactionManagementConfigurer;
import org.springframework.transaction.interceptor.TransactionInterceptor;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;


@Configuration
@EnableTransactionManagement
public class MajorMyBatisConfig implements TransactionManagementConfigurer {


	private static Log logger = LogFactory.getLog(MajorMyBatisConfig.class);
	
	@Autowired
    private Environment env;
	
	@Value("${mybatis.default.mapperLocations}")
	private String mapperLocation;
	
	@Autowired
	private DataSource datasource;
	
    @Bean
    public SqlSessionFactory sqlSessionFactory() throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(datasource);
        bean.setTypeAliasesPackage(StringUtils.EMPTY);
        //添加插件
        bean.setPlugins(new Interceptor[]{getPageHelper()});

        //添加XML目录
        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        try {
        	bean.setMapperLocations(resolver.getResources(mapperLocation));
        	// 修复使用oracle数据库时, 字段为null无法获取对应jdbcType类型的问题[Tan Ling 2017-07-08 17:13]
            SqlSessionFactory sqlSessionFactory = bean.getObject();
            sqlSessionFactory.getConfiguration().setJdbcTypeForNull(JdbcType.NULL);

            return sqlSessionFactory;
        } catch (Exception e) {
            logger.error("could not resolver mybatis resource from mapperLocation", e);
            throw new RuntimeException(e);
        }
    }

    @Bean
    public DataSource datasource() throws Exception{
    	RelaxedPropertyResolver propertyResolver = new RelaxedPropertyResolver(env, "spring.");
    	Map<String, Object> propertiesMap= propertyResolver.getSubProperties("datasource.");
    	Map<String, Object> pureMap = new HashMap<>();
    	  for (Map.Entry<String,Object> entry : propertiesMap.entrySet()) {
    		    String key = entry.getKey();
    		    if(StringUtils.isNotBlank(key)){
        			pureMap.put(key, String.valueOf(propertiesMap.get(key)));
        		}
    		  }
    /*	for (String key: propertiesMap.keySet()) {
    		if(StringUtils.isNotBlank(key)){
    			pureMap.put(key, String.valueOf(propertiesMap.get(key)));
    		}
		}*/
    	return com.alibaba.druid.pool.DruidDataSourceFactory.createDataSource(pureMap);
    }
    
    @Bean
    public SqlSessionTemplate sqlSessionTemplate(SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

    @Bean
    @Override
    public PlatformTransactionManager annotationDrivenTransactionManager() {
        return new DataSourceTransactionManager(datasource);
    }
    
    @Bean
    protected TransactionInterceptor transactionInterceptor(PlatformTransactionManager transactionManager) {
		TransactionInterceptor ti = new TransactionInterceptor();
		ti.setTransactionManager(transactionManager);
		Properties properties = new Properties();
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
    
    @Bean
    protected BeanNameAutoProxyCreator transactionAutoProxy() {
		BeanNameAutoProxyCreator transactionAutoProxy = new BeanNameAutoProxyCreator();
		transactionAutoProxy.setProxyTargetClass(false);
		transactionAutoProxy.setBeanNames(new String[] { "*ServiceImpl" });
		transactionAutoProxy.setInterceptorNames(new String[]{"transactionInterceptor"});
		return transactionAutoProxy;
	}
    
    // 分页插件
    private PageHelper getPageHelper(){
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
}
