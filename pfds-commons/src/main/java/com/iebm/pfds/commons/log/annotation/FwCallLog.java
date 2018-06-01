package com.iebm.pfds.commons.log.annotation;


import com.iebm.pfds.commons.log.type.CallLogLv;
import com.iebm.pfds.commons.log.type.CallLogType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * rest接口描述类，供日志记录使用
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)  
public @interface FwCallLog {
	
	/**
	 * 描述
	 * @return 描述
	 */
	String description() default "";
	
	/**
	 * 日志级别
	 * @return 日志级别
	 */
	CallLogLv logLv() default CallLogLv.NORMAL;

	/**
	 * 用户操作日志（用于用户操作日志时，进行调用日志过滤的控制，原则上仅业务组装层该属性值为True）
	 * @return
     */
	boolean userOpt() default false;

	/**
	 * 功能类型
	 * @return 功能类型
	 */
	CallLogType restType();
}
