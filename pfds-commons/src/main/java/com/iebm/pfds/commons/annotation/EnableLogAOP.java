package com.iebm.pfds.commons.annotation;

import com.iebm.pfds.commons.log.LogAOP;
import org.springframework.context.annotation.Import;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 启用请求日志注解
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Import({LogAOP.class})
public @interface EnableLogAOP {
	
}
