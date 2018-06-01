package com.iebm.pfds.commons.exception;

import com.alibaba.fastjson.JSONObject;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpStatusCodeException;


@Aspect
@Order(-1)
@Component
public class RPCCallExceptionAspect {

	
	 @Pointcut("@within(com.iebm.pfds.commons.exception.FwRPCCallExceptionHandler)")
	    private void serviceAspect() {
	  }
	
	@AfterThrowing(pointcut = "serviceAspect()",throwing = "e")
    public void changeDataSource(JoinPoint point, HttpStatusCodeException e) {
		
		String responseBodyAsString = e.getResponseBodyAsString();
		
		JSONObject parseObject = JSONObject.parseObject(responseBodyAsString);
		if(parseObject!=null){
			String errorMessage = parseObject.getString("errorMessage");
			
			Integer errorLV = parseObject.getInteger("errorLV");
			
			String errorCode = parseObject.getString("errorCode");
			
			throw new SpsoftException(errorCode,errorMessage,errorLV);
		}else{
			throw new SpsoftException("00009999",e.getMessage(),1);
		}
		
    }
}
