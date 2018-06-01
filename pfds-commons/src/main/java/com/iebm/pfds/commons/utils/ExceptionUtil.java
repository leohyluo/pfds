package com.iebm.pfds.commons.utils;

import com.iebm.pfds.commons.exception.FwSpsoftException;
import com.iebm.pfds.commons.exception.FwSpsoftExceptionCode;
import com.iebm.pfds.commons.exception.SpsoftException;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.io.IOException;
import java.net.ConnectException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 异常处理工具
 *
 */
public class ExceptionUtil {
	private static Map<FwSpsoftExceptionCode,FwSpsoftException> defSpsoftException;
	static{
		defSpsoftException= new HashMap<FwSpsoftExceptionCode, FwSpsoftException>();
		defSpsoftException.put(FwSpsoftExceptionCode.NUMBER_FORMAT_EXCEPTION, new FwSpsoftException(FwSpsoftExceptionCode.NUMBER_FORMAT_EXCEPTION));
		defSpsoftException.put(FwSpsoftExceptionCode.IO_EXCEPTION, new FwSpsoftException(FwSpsoftExceptionCode.IO_EXCEPTION));
		defSpsoftException.put(FwSpsoftExceptionCode.NULL_POINTER_EXCEPTION, new FwSpsoftException(FwSpsoftExceptionCode.NULL_POINTER_EXCEPTION));
		defSpsoftException.put(FwSpsoftExceptionCode.CLASS_CAST_EXCEPTION, new FwSpsoftException(FwSpsoftExceptionCode.CLASS_CAST_EXCEPTION));
		defSpsoftException.put(FwSpsoftExceptionCode.SQL_EXCEPTION, new FwSpsoftException(FwSpsoftExceptionCode.SQL_EXCEPTION));
		defSpsoftException.put(FwSpsoftExceptionCode.CONNECT_EXCEPTION, new FwSpsoftException(FwSpsoftExceptionCode.CONNECT_EXCEPTION));
		defSpsoftException.put(FwSpsoftExceptionCode.UNKNOW_EXCEPTION, new FwSpsoftException(FwSpsoftExceptionCode.UNKNOW_EXCEPTION));
		defSpsoftException.put(FwSpsoftExceptionCode.FORM_VALID_EXCEPTION, new FwSpsoftException(FwSpsoftExceptionCode.FORM_VALID_EXCEPTION));
		defSpsoftException.put(FwSpsoftExceptionCode.ACCESS_DENIED_EXCEPTION, new FwSpsoftException(FwSpsoftExceptionCode.ACCESS_DENIED_EXCEPTION));
		defSpsoftException.put(FwSpsoftExceptionCode.METHOD_NOT_SUPPORT_EXCEPTION, new FwSpsoftException(FwSpsoftExceptionCode.METHOD_NOT_SUPPORT_EXCEPTION));
		defSpsoftException.put(FwSpsoftExceptionCode.SPRINGMVC_ARGS_BIND_EXCEPTION, new FwSpsoftException(FwSpsoftExceptionCode.SPRINGMVC_ARGS_BIND_EXCEPTION));
		defSpsoftException.put(FwSpsoftExceptionCode.SPRINGMVC_ARGS_EXCEPTION, new FwSpsoftException(FwSpsoftExceptionCode.SPRINGMVC_ARGS_EXCEPTION));
	}
	
	private ExceptionUtil(){
		
	}
	/**
	 * 异常包装
	 * <br>将不同的异常包装成系统定义的业务异常类
	 * @param ex 来源异常
	 * @return 自定义业务异常
	 */
	public static SpsoftException packSpsoftException(Exception ex){
		SpsoftException spex = null;
		if(ex != null){
			if (ex instanceof SpsoftException) {
				spex = (SpsoftException)ex;
			} else {
				if (ex instanceof NumberFormatException) {
					spex = defSpsoftException.get(FwSpsoftExceptionCode.NUMBER_FORMAT_EXCEPTION);
				} else if (ex instanceof IOException) {
					spex = defSpsoftException.get(FwSpsoftExceptionCode.IO_EXCEPTION);
				} else if (ex instanceof NullPointerException) {
					spex = defSpsoftException.get(FwSpsoftExceptionCode.NULL_POINTER_EXCEPTION);
				} else if (ex instanceof ClassCastException) {
					spex = defSpsoftException.get(FwSpsoftExceptionCode.CLASS_CAST_EXCEPTION);
				} else if (ex instanceof SQLException || ex instanceof BadSqlGrammarException) {
					spex = defSpsoftException.get(FwSpsoftExceptionCode.SQL_EXCEPTION);
				} else if (ex instanceof ConnectException) {
					spex = defSpsoftException.get(FwSpsoftExceptionCode.CONNECT_EXCEPTION);
				} else if (ex instanceof MethodArgumentNotValidException) {
					StringBuilder sbff = new StringBuilder();
					FwSpsoftException fse = defSpsoftException.get(FwSpsoftExceptionCode.FORM_VALID_EXCEPTION);
					List<ObjectError> allErrors = ((MethodArgumentNotValidException) ex).getBindingResult().getAllErrors();
					if(allErrors != null && !allErrors.isEmpty()){
						for(int i=0;i<allErrors.size();i++){
							sbff.append(allErrors.get(i).getDefaultMessage() + "\r\n");
						}
					}
					spex = new SpsoftException(fse.getErrorCode(), fse.getMessage() + ":" + sbff, fse.getErrorLV());
				} else if (ex instanceof AccessDeniedException) {
					spex = defSpsoftException.get(FwSpsoftExceptionCode.ACCESS_DENIED_EXCEPTION);
				} else if (ex instanceof AuthenticationException) {
					spex = new SpsoftException("400", ex.getMessage(), 3);
				} else if (ex instanceof HttpRequestMethodNotSupportedException) {
					spex = defSpsoftException.get(FwSpsoftExceptionCode.METHOD_NOT_SUPPORT_EXCEPTION);
				} else if ( ex instanceof BindException){
					StringBuilder sb = new StringBuilder();
					FwSpsoftException fse = defSpsoftException.get(FwSpsoftExceptionCode.SPRINGMVC_ARGS_EXCEPTION);
					List<ObjectError> allErrors = ((BindException) ex).getAllErrors();
					if(allErrors != null && !allErrors.isEmpty()){
						for (int i=0;i<allErrors.size();i++) {
							if ("typeMismatch".equals(allErrors.get(i).getCode())) {
								sb.append("参数绑定错误，请检查提交的参数数据类型" + "\r\n");
							} else {
								sb.append(allErrors.get(i).getDefaultMessage() + "\r\n");
							}
						}
					}
					spex = new SpsoftException(fse.getErrorCode(), fse.getMessage() + ":" + sb, fse.getErrorLV());
				}	else {
					spex = defSpsoftException.get(FwSpsoftExceptionCode.UNKNOW_EXCEPTION);
				}
			}
		}
		return spex;
	}
}
