package com.iebm.pfds.commons.exception;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.iebm.pfds.commons.utils.DateUtils;
import com.iebm.pfds.commons.utils.ExceptionUtil;
import org.apache.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * 公共异常处理类
 */
@ControllerAdvice
public class GlobalExceptionHandler {
	private Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

	//是否开启异常推栈内容的输出
	@Value("${fw.reutrnExceptionStackTraceElement:false}")
	private boolean reutrnExceptionStackTraceElement;

	/**
	 * 异常捕获和处理
	 * @param request 请求对象
	 * @param ex 需要捕获的异常类型
	 * @param response 响应对象
	 * @return 自定义的结果
	 */
	@ExceptionHandler
	@ResponseBody
	public Object exceptionHandler(HttpServletRequest request,Exception ex,HttpServletResponse response) {
		// 错误信息入库
		FwExceptionLog exceptionLog =  new FwExceptionLog();
		exceptionLog.setErrorId(MDC.get("X-B3-SpanId"));
		StringBuilder sb = new StringBuilder();
		StackTraceElement[] stackTrace = ex.getStackTrace();
		sb.append(ex.getMessage()).append("\r\n");
		for (StackTraceElement stackTraceElement : stackTrace) {
			sb.append(stackTraceElement).append("\r\n");
		}

		exceptionLog.setErrorTime(new Date());
	
		
		exceptionLog.setSpsoftException(ExceptionUtil.packSpsoftException(ex));

		if (sb.length()>2000){
			exceptionLog.setEx(sb.toString().substring(0,1999));
		}else{
			exceptionLog.setEx(sb.toString());
		}
		exceptionLog.setRequestUrl(request.getRequestURL().toString());
		exceptionLog.setRequestMethod(request.getMethod());
		exceptionLog.setRequestParam(getRequestParam(request)); // 为se请求参数属性设值

		// 返回信息
		Map<String, Object> returnMap = new HashMap<>();
		returnMap.put("errorCode", exceptionLog.getErrorCode());
		returnMap.put("errorMessage", exceptionLog.getErrorMessage().replaceAll("\\<", "&lt;").replaceAll("\\>", "&gt;") );
		returnMap.put("errorLV", exceptionLog.getErrorLV());
		returnMap.put("errorId", exceptionLog.getErrorId());
		returnMap.put("returnErrorTime", DateUtils.format(exceptionLog.getErrorTime(), "yyyy-MM-dd HH:mm:ss"));
		if (reutrnExceptionStackTraceElement) {
			returnMap.put("Throwable", "\r以下信息必须在生产环境去除\r" + sb.toString());
		}
		log.error(JSON.toJSONString(exceptionLog));
		log.error(sb.toString());
		ModelAndView mav = new ModelAndView();

		//参考Spring Boot默认的错误处理返回
		
		MappingJackson2JsonView view = new MappingJackson2JsonView();
		view.setAttributesMap(returnMap);
		mav.setView(view);
		response.setStatus(HttpStatus.SC_BAD_REQUEST);
		return mav;
	}

	private String getRequestParam(HttpServletRequest request) {

		JSONObject param = new JSONObject();
		Enumeration<String> e = request.getParameterNames();
		while (e.hasMoreElements()) {
			String pName = e.nextElement();
			param.put(pName, request.getParameterValues(pName));
		}
		return JSON.toJSONString(param, SerializerFeature.WriteMapNullValue);
	}
}
