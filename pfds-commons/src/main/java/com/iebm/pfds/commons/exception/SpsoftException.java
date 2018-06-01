package com.iebm.pfds.commons.exception;

import org.slf4j.MDC;

import java.util.Date;

/**
 * 错误消息封装
 */
public class SpsoftException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6450680099756290007L;
	/**
	 * 错误码
	 */
	private final String errorCode;
	
	/**
	 * 错误级别
	 */
	private final int errorLV;
	
	/**
	 * 错误事件编码
	 */
	private final String errorId=MDC.get("X-B3-SpanId");
	
	/**
	 * 错误发生时间
	 */
	private final Date errorTime=new Date();
	

	public SpsoftException(String errorCode,String message,int errorLV) {
		super(message);
		this.errorCode = errorCode;
		this.errorLV=errorLV;
	}

   public SpsoftException(String errorCode,String message,int errorLV,Throwable t) {
		super(message,t);
		this.errorCode = errorCode;
		this.errorLV=errorLV;
	}
    
	public SpsoftException(String errorCode,String message,SpsoftExceptionLevel errorLV) {
		super(message);
		this.errorCode = errorCode;
		this.errorLV=errorLV.getValue();
	}
	
	public SpsoftException(SpsoftExceptionMsg spsoftExceptionMsg) {
		super(spsoftExceptionMsg.getMessage());
		this.errorCode = spsoftExceptionMsg.getErrorCode();
		this.errorLV=spsoftExceptionMsg.getErrorLV().getValue();
	}
	
	public SpsoftException(SpsoftExceptionMsg spsoftExceptionMsg,Throwable t) {
		super(spsoftExceptionMsg.getMessage(),t);
		this.errorCode = spsoftExceptionMsg.getErrorCode();
		this.errorLV=spsoftExceptionMsg.getErrorLV().getValue();
	}
	
	public String getErrorCode() {
		return errorCode;
	}

	public int getErrorLV() {
		return errorLV;
	}

	public String getErrorId() {
		return errorId;
	}

	public Date getErrorTime() {
		return errorTime;
	}




}
