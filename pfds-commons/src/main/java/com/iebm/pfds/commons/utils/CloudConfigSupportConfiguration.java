package com.iebm.pfds.commons.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.bootstrap.config.PropertySourceBootstrapConfiguration;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.env.*;

import java.util.HashMap;
import java.util.Map;

/**
 * 配置中心读取数据的工具类
 *
 */
@Configuration
public class CloudConfigSupportConfiguration implements ApplicationContextInitializer<ConfigurableApplicationContext>, Ordered {
     private Logger logger = LoggerFactory.getLogger(CloudConfigSupportConfiguration.class);
     
     /**
      * 配置中心属性集合,key=属性名,value=属性值
      */
     public  static  Map<String, Object> backupPropertyMap = new HashMap<String, Object>();

     @Override
     public void initialize(ConfigurableApplicationContext applicationContext) {
	     logger.info("检查Config Service配置资源");
	     ConfigurableEnvironment environment = applicationContext.getEnvironment();
	     MutablePropertySources propertySources = environment.getPropertySources();
	     logger.info("加载PropertySources源：" + propertySources.size() + "个");

	     PropertySource cloudConfigSource = getLoadedCloudPropertySource(propertySources);
         logger.info("成功获取ConfigService配置资源");
         //备份
         backupPropertyMap = makeBackupPropertyMap(cloudConfigSource);
    }

     /**
      * 获取加载的Cloud Config 配置项
      *
      * @param propertySources
      * @return
      */
     private PropertySource getLoadedCloudPropertySource(MutablePropertySources propertySources) {
         if (!propertySources.contains(PropertySourceBootstrapConfiguration.BOOTSTRAP_PROPERTY_SOURCE_NAME)) {
             return null;
         }
         
         PropertySource propertySource = propertySources.get(PropertySourceBootstrapConfiguration.BOOTSTRAP_PROPERTY_SOURCE_NAME);
         
         if (propertySource instanceof CompositePropertySource) {
             for (PropertySource<?> source : ((CompositePropertySource) propertySource).getPropertySources()) {
                 if ("configService".equals(source.getName())) {
                     return source;
                 }
             }
         }
         return null;
     }
     
     /**
      * 生成备份的配置数据
      *
      * @param propertySource
      * @return
      */
     private Map<String, Object> makeBackupPropertyMap(PropertySource propertySource) {
         Map<String, Object> backupSourceMap = new HashMap<>();
        if (propertySource instanceof CompositePropertySource) {
             CompositePropertySource composite = (CompositePropertySource) propertySource;
             for (PropertySource<?> source : composite.getPropertySources()) {
                 if (source instanceof MapPropertySource) {
                     MapPropertySource mapSource = (MapPropertySource) source;
                     for (String propertyName : mapSource.getPropertyNames()) {
                         // 前面的配置覆盖后面的配置
                         if (!backupSourceMap.containsKey(propertyName)) {
                             backupSourceMap.put(propertyName, mapSource.getProperty(propertyName));
                         }
                     }
                 }
             }
         }
        return backupSourceMap;
     }

     
     
  @Override
  public int getOrder() {
    return 0;
  }
      
}