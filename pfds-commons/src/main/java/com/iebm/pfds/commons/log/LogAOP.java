package com.iebm.pfds.commons.log;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.PropertyPreFilter;
import com.alibaba.fastjson.serializer.SerializerFeature;
/*import com.spsoft.common.exception.SpsoftException;
import com.spsoft.common.log.annotation.FwCallLog;
import com.spsoft.common.log.domain.CallLogBase;
import com.spsoft.common.log.domain.CallLogException;
import com.spsoft.common.log.domain.CallLogReturn;
import com.spsoft.common.log.type.CallLogLv;
import com.spsoft.common.security.vo.FSUsers;
import com.spsoft.common.tmsc.vo.OauthUser;
import com.spsoft.common.utils.CloudConfigSupportConfiguration;
import com.spsoft.common.utils.CommonUtils;
import com.spsoft.common.utils.ExceptionUtil;*/
import com.iebm.pfds.commons.exception.SpsoftException;
import com.iebm.pfds.commons.log.annotation.FwCallLog;
import com.iebm.pfds.commons.log.domain.CallLogBase;
import com.iebm.pfds.commons.log.domain.CallLogException;
import com.iebm.pfds.commons.log.domain.CallLogReturn;
import com.iebm.pfds.commons.log.type.CallLogLv;
import com.iebm.pfds.commons.security.vo.FSUsers;
import com.iebm.pfds.commons.utils.CloudConfigSupportConfiguration;
import com.iebm.pfds.commons.utils.CommonUtils;
import com.iebm.pfds.commons.utils.ExceptionUtil;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.Environment;
/*import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;*/
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Date;

/**
 * 调用日志记录切面
 * @author lhy
 *
 */
@Component
@Aspect
@Order(1)
public class LogAOP {

	private Logger logger = LoggerFactory.getLogger(LogAOP.class);
	
	private CallLogLv defaultLogLv = CallLogLv.NORMAL;

	@Autowired
	private Environment env;

	private String serverIp="unkonw";
	private String serviceName="unkonw";
	private String servicePort="unkonw";

	/**
	 * 初始化配置.
	 */
	@PostConstruct
	public void init(){
		String lv = (String)CloudConfigSupportConfiguration.backupPropertyMap.get("fw.logaop.loglv");//记录日志的敏感级别
//		String lv = "";//记录日志的敏感级别

		try {
			serverIp = InetAddress.getLocalHost().getHostAddress();
		} catch (UnknownHostException e) {
			serverIp =  "unknown";
		}

		servicePort = env.getProperty("server.port" );
		serviceName = env.getProperty("spring.application.name" );

		try {
			if(lv != null)
				defaultLogLv = CallLogLv.valueOf(lv.trim().toUpperCase());
		} catch (Exception e) {
			defaultLogLv = CallLogLv.NORMAL;
			logger.error("初始化失败！",e);
		}
	}
		

	/**
	 * 异常日志记录
	 * @param jp 切点
	 * @param logAn 请求日志注解
	 * @param rm RequestMapping注解
	 * @param tr 异常对象
	 */
	@AfterThrowing(pointcut="@annotation(logAn) && @annotation(rm)",throwing="tr")
    public void exceptionInvoke(JoinPoint jp, FwCallLog logAn, RequestMapping rm, Throwable tr){
    	if (defaultLogLv.getLv()<=logAn.logLv().getLv() && tr instanceof Exception){
    		
    		CallLogException cl = new CallLogException();

			StringBuilder sb = new StringBuilder();
			StackTraceElement[] stackTrace = tr.getStackTrace();
			sb.append(tr.getMessage()).append("\r\n");
			for (StackTraceElement stackTraceElement : stackTrace) {
				sb.append(stackTraceElement).append("\r\n");
			}
			cl.setErrorStackTrace(sb.toString());

    		Exception ex = (Exception)tr;
        	SpsoftException spex = ExceptionUtil.packSpsoftException(ex);
        	cl.setErrorCode(spex.getErrorCode());
        	cl.setErrorMessage(spex.getMessage());
        	cl.setErrorId(MDC.get("X-B3-SpanId"));
        	fillCallLog(jp, logAn, rm, cl);
        	try{
				logger.error("调用日志--异常",cl);
        	}catch(Exception e){
        		logger.error("调用日志保存失败！",e);
        	}
    	}    	
    }
	
	
	/**
	 * 正常调用后处理
	 * @param jp	切点
	 * @param logAn	日志注解
	 * @param rm	RequestMapping注解
	 * @throws Exception
	 */
	@AfterReturning("@annotation(logAn) && @annotation(rm)")
	public void afterInvoke(JoinPoint jp,FwCallLog logAn,RequestMapping rm) {
    	if (defaultLogLv.getLv()<=logAn.logLv().getLv()){
    		CallLogReturn cl = new CallLogReturn();
    		fillCallLog(jp, logAn, rm, cl);
    		try{
				logger.info("调用日志",cl);
        	}catch(Exception ex){
        		logger.error("调用日志保存失败！",ex);
        	}
    	}    	
    }


	/**
	 * 为请求日志对象填充数据
	 * @param jp 切点
	 * @param logAn 日志注解
	 * @param rm RequestMapping注解
	 * @param callLog 请求日志对象
	 */
	private void fillCallLog(JoinPoint jp, FwCallLog logAn, RequestMapping rm, CallLogBase callLog){
		callLog.setMethodName(jp.getSignature().toString());
		callLog.setClassName(jp.getTarget().getClass().toString());
		callLog.setParams( JSON.toJSONString(jp.getArgs(),
			new PropertyPreFilter(){//Request、Response、IO流、BindingResult不序列化为JSON
    			@Override
				public boolean apply(JSONSerializer serializer, Object object, String name){
					return !(object instanceof ServletResponse 
								|| object instanceof ServletRequest
								|| object instanceof OutputStream 
								|| object instanceof InputStream
								|| object instanceof MultipartFile
								|| object instanceof BindingResult
								|| object instanceof UserDetails
					);
    			}
    		}, 
    		new SerializerFeature[0])
    	);//将方问参数转为JSON字符串
		StringBuilder urlValue = new StringBuilder();

    	for(String v:rm.value()){
    		//urlValue+=v+"\t";
    		urlValue.append(v+" ");
    	}

    	callLog.setUrl(urlValue.toString());
    	String urlMethod="";
    	for(RequestMethod r:rm.method()){
    		urlMethod=r.name();//urlMethod+=r.name()+"\t";   一个Controller可以配置多个Method
    	}
    	callLog.setMethodType(urlMethod);
    	callLog.setDescription(logAn.description());
    	callLog.setLogLv(logAn.logLv());
    	callLog.setLogTime(new Date());
		callLog.setUserOpt( logAn.userOpt() );

		callLog.setServerIp(serverIp);
		callLog.setServiceName(serviceName);
		callLog.setServicePort(servicePort);
		callLog.setClientIp(CommonUtils.getIPAddrByRequestContextHolder());

		try {
			Object ui = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			if ( ui instanceof FSUsers){
				FSUsers fsUsersParam = (FSUsers) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
				callLog.setUserAboutInfo( fsUsersParam==null ? "unkonw" : fsUsersParam.getUserId().toString() );
				callLog.setBkCompanyCode(fsUsersParam.getBkCompanyCode());
				callLog.setCompanyId(fsUsersParam.getCompanyId());
				callLog.setOrgId( fsUsersParam.getOrgId() );
			}
			/*else if ( ui instanceof OauthUser){
				OauthUser fsUsersParam = (OauthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
				callLog.setUserAboutInfo( fsUsersParam.getUserId() );
				callLog.setOrgId( fsUsersParam.getOrgId() );
			}*/
		}catch(Exception ex) {
			callLog.setUserAboutInfo( "fail"  );
		}

    	callLog.setRestType(logAn.restType().toString());
	}
    
}
 