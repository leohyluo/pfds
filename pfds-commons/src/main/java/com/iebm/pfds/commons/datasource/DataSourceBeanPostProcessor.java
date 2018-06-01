package com.iebm.pfds.commons.datasource;

import com.alibaba.druid.pool.DruidDataSource;
import com.iebm.pfds.commons.utils.ConfigurationPropertiesBinder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.cloud.bootstrap.config.PropertySourceBootstrapConfiguration;
import org.springframework.cloud.bootstrap.encrypt.KeyProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.env.*;
import org.springframework.security.crypto.encrypt.TextEncryptor;
import org.springframework.security.rsa.crypto.KeyStoreKeyFactory;
import org.springframework.security.rsa.crypto.RsaSecretEncryptor;
import org.springframework.util.StringUtils;

import java.sql.SQLException;
import java.util.*;


public class DataSourceBeanPostProcessor implements BeanFactoryPostProcessor, ApplicationContextAware {

    private final static Logger logger = LoggerFactory.getLogger(DataSourceBeanPostProcessor.class);

    private ApplicationContext applicationContext;

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        DefaultListableBeanFactory factory = (DefaultListableBeanFactory)beanFactory;
        PropertySource propertySource = this.resolvePropertySource();
        List<DataSourceProperties> dataSourceProperties = collectDataSourceProperties(propertySource);
        registryDatabase(dataSourceProperties, factory);
    }


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }


    private void registryDatabase(Collection<DataSourceProperties> collections, ConfigurableListableBeanFactory beanFactory){
        DefaultListableBeanFactory factory = (DefaultListableBeanFactory)beanFactory;
        Map<Object,Object> notifyMap = new TreeMap<>();
        if(!collections.isEmpty()){
            collections.forEach(p->{
                BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder.genericBeanDefinition(DruidDataSource.class);
                factory.registerBeanDefinition(p.getName(), beanDefinitionBuilder.getBeanDefinition());
                DruidDataSource dataSource = (DruidDataSource)factory.getBean(p.getName());
                try {
                    com.alibaba.druid.pool.DruidDataSourceFactory.config(dataSource, p.getDecryptProperties());
                } catch (SQLException e) {
                    logger.error("Cannot config properties to datasource "+p.getName() , e);
                }
                notifyMap.put(p.getName(), dataSource);
            });
        }
        addDataSourceToRouter(notifyMap);
    }

    private void addDataSourceToRouter(Map<Object,Object> datasources ){
        MultipleDataSourceRouter.getInstance().setTargetDataSources(datasources);
        //MultipleDataSourceRouter.getInstance().setDefaultTargetDataSource(datasources.entrySet().iterator().next().getValue());
    }



    protected static class DataSourceProperties{
        private String name;

        private Map<String,Object> properties;

        private Properties decryptProperties;

        private static final String NAME_PRIFIX = "spring.datasource.";

        private static final String ALIAS  = "alias";

        public DataSourceProperties( Map<String, Object> properties, KeyProperties keyProperties) {
            this.properties = properties;
            resolveName();
            decrypt(keyProperties);
        }

        public static boolean isDataSourceProperties( Map<String, Object> properties){
            return properties.containsKey(NAME_PRIFIX.concat(ALIAS)) || properties.containsKey(NAME_PRIFIX.concat("driver-class-name"));
        }


        private void  decrypt(KeyProperties keyProperties){
            if(properties == null)return;
            TextEncryptor encryptor = new RsaSecretEncryptor(
                    new KeyStoreKeyFactory(keyProperties.getKeyStore().getLocation(),
                            keyProperties.getKeyStore().getPassword().toCharArray()).getKeyPair(
                            keyProperties.getKeyStore().getAlias(),
                            keyProperties.getKeyStore().getSecret().toCharArray()),
                    keyProperties.getRsa().getAlgorithm(),keyProperties.getRsa().getSalt(),
                    keyProperties.getRsa().isStrong());
            Properties deProperties = new Properties();
            properties.forEach((k,v) ->{
                if(k.startsWith(NAME_PRIFIX)){
                    String key = k.substring(NAME_PRIFIX.length());
                    String value = String.valueOf(v);
                    if (StringUtils.hasText(value) && value.startsWith("{cipher}")) {
                        value = value.substring("{cipher}".length());
                        try {
                            value = encryptor.decrypt(value);
                        }catch (Exception e){
                            String message = "Cannot decrypt: key=" + key;
                            logger.warn(message);
                        }
                    }
                    if(StringUtils.hasText(value))deProperties.setProperty(key, value);
                }
            });
            this.decryptProperties = deProperties;
        }

        private void resolveName(){
            if(properties.containsKey(NAME_PRIFIX.concat(ALIAS))){
                this.name = String.valueOf(properties.get(NAME_PRIFIX.concat(ALIAS)));
            }else{
                String str = String.valueOf(properties.get(NAME_PRIFIX.concat("url")));
                if(StringUtils.hasText(str)){
                    String[] strs = str.split("/");
                    String databaseNameStr = strs[strs.length-1];
                    String [] pNames = databaseNameStr.split("\\?");
                    this.name = pNames[0];
                }
            }
        }

        public String getName() {
            return name;
        }

        public Map<String, Object> getProperties() {
            return properties;
        }
        public Properties getDecryptProperties(){
            return decryptProperties;
        }
    }

    private List<DataSourceProperties> collectDataSourceProperties(PropertySource propertySource){
        if (propertySource instanceof CompositePropertySource) {
            CompositePropertySource composite = (CompositePropertySource) propertySource;
            List<DataSourceProperties> result = new ArrayList<>();

            KeyProperties key = new KeyProperties();
            new ConfigurationPropertiesBinder(applicationContext).bindPropertiesToTarget(key);

            composite.getPropertySources().forEach(source->{
                if(source instanceof MapPropertySource){
                    Map<String, Object> properties = ((MapPropertySource) source).getSource();
                    boolean isDataSourceProperties = DataSourceProperties.isDataSourceProperties(properties);
                    if(isDataSourceProperties){
                        result.add(new DataSourceProperties(properties, key));
                    }
                }
            });
            return result;
        }
        return Collections.emptyList();
    }


    private PropertySource resolvePropertySource(){
        ConfigurableEnvironment environment = (ConfigurableEnvironment)applicationContext.getEnvironment();
        MutablePropertySources propertySources = environment.getPropertySources();
        PropertySource cloudConfigSource = getLoadedCloudPropertySource(propertySources);
        return cloudConfigSource;
    }

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
}
