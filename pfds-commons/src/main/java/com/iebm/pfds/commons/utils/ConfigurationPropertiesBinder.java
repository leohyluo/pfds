package com.iebm.pfds.commons.utils;

import org.springframework.beans.factory.BeanCreationException;
import org.springframework.boot.bind.PropertiesConfigurationFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.support.DefaultConversionService;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

/**
 * 手动将配置属性绑定到bean构造器
 * Created by lhkong on 2017/8/16.
 */
public class ConfigurationPropertiesBinder {

    private ApplicationContext applicationContext;
    //属性转换服务
    private ConversionService conversionService;
    //配置信息
    private MutablePropertySources propertySources;
    //校验器
    private Validator validator;

    public ConfigurationPropertiesBinder(ApplicationContext applicationContext){
        this.applicationContext = applicationContext;
        this.propertySources = ((ConfigurableEnvironment)applicationContext.getEnvironment()).getPropertySources();
    }


    public ConfigurationPropertiesBinder setConversionService(ConversionService conversionService){
        this.conversionService = conversionService;
        return this;
    }

    public ConfigurationPropertiesBinder setValidator(Validator validator){
        this.validator = validator;
        return this;
    }

    private Validator getValidator(){
        if (this.validator != null) {
            return this.validator;
        }
        LocalValidatorFactoryBean validator = new LocalValidatorFactoryBean();
        validator.setApplicationContext(applicationContext);
        validator.afterPropertiesSet();
        return validator;
    }

    private Validator determineValidator(Object bean) {
        Validator validator = getValidator();
        boolean supportsBean = (validator != null && validator.supports(bean.getClass()));
        if (ClassUtils.isAssignable(Validator.class, bean.getClass())) {
            if (supportsBean) {
                return new ChainingValidator(validator, (Validator) bean);
            }
            return (Validator) bean;
        }
        return (supportsBean ? validator : null);
    }

    private static class ChainingValidator implements Validator {

        private Validator[] validators;

        ChainingValidator(Validator... validators) {
            Assert.notNull(validators, "Validators must not be null");
            this.validators = validators;
        }

        @Override
        public boolean supports(Class<?> clazz) {
            for (Validator validator : this.validators) {
                if (validator.supports(clazz)) {
                    return true;
                }
            }
            return false;
        }

        @Override
        public void validate(Object target, Errors errors) {
            for (Validator validator : this.validators) {
                if (validator.supports(target.getClass())) {
                    validator.validate(target, errors);
                }
            }
        }

    }

    private String getAnnotationDetails(ConfigurationProperties annotation) {
        if (annotation == null) {
            return "";
        }
        StringBuilder details = new StringBuilder();
        details.append("prefix=").append(annotation.prefix());
        details.append(", ignoreInvalidFields=").append(annotation.ignoreInvalidFields());
        details.append(", ignoreUnknownFields=").append(annotation.ignoreUnknownFields());
        details.append(", ignoreNestedProperties=")
                .append(annotation.ignoreNestedProperties());
        return details.toString();
    }

    private void postProcessInitialization(Object bean, ConfigurationProperties annotation){
        Object target = bean;
        PropertiesConfigurationFactory<Object> factory = new PropertiesConfigurationFactory<Object>(target);
        factory.setPropertySources(propertySources);
        factory.setValidator(determineValidator(target));
        factory.setConversionService(this.conversionService == null? new DefaultConversionService(): this.conversionService);
        if (annotation != null) {
            factory.setIgnoreInvalidFields(annotation.ignoreInvalidFields());
            factory.setIgnoreUnknownFields(annotation.ignoreUnknownFields());
            factory.setExceptionIfInvalid(annotation.exceptionIfInvalid());
            factory.setIgnoreNestedProperties(annotation.ignoreNestedProperties());
            if (org.springframework.util.StringUtils.hasLength(annotation.prefix())) {
                factory.setTargetName(annotation.prefix());
            }
        }
        try {
            factory.bindPropertiesToTarget();
        }
        catch (Exception ex) {
            String targetClass = ClassUtils.getShortName(target.getClass());
            throw new BeanCreationException(bean.getClass().getName(), "Could not bind properties to "
                    + targetClass + " (" + getAnnotationDetails(annotation) + ")", ex);
        }
    }

    /**
     * 绑定属性到目标服务
     * @param bean
     */
    public void bindPropertiesToTarget(Object bean){
        if(bean == null)return;
        ConfigurationProperties annotation = AnnotationUtils.findAnnotation(bean.getClass(), ConfigurationProperties.class);
        postProcessInitialization(bean, annotation);
    }
}
