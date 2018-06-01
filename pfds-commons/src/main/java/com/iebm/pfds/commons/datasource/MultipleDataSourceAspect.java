package com.iebm.pfds.commons.datasource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.core.annotation.Order;

import java.util.HashMap;
import java.util.Map;


/**
 * 多数据源切面，匹配com.spsoft下的dao 目录下的类
 */
@Aspect
@Order(-1)
public class MultipleDataSourceAspect {

	private static Log logger = LogFactory.getLog(MultipleDataSourceAspect.class);

	public static Map<String,String> DATASOURCE_MAPPING = new HashMap<>();

    @Before("execution(* com.spsoft..*.dao..*.*(..))")
    public void toggleDataSource(JoinPoint point){
        String targetClassName = point.getSignature().getDeclaringType().getName();
        for (String basepacket: DATASOURCE_MAPPING.keySet()){
            if(targetClassName.contains(basepacket)){
                MultipleDataSourceRouter.setDataSourceKey(DATASOURCE_MAPPING.get(basepacket));
                break;
            }
        }
    }

}

