package com.iebm.pfds.commons.log.domain;


import com.iebm.pfds.commons.log.type.CallLogResult;

/**
 * 请求日志--异常日志
 * @author bliao
 *
 */
public class CallLogException extends CallLogBase{

	/**
	 * 错误码
	 */
	private String errorCode="";
	/**
	 * 错误描述
	 */
	private String errorMessage="";
	/**
	 * 错误堆
	 */
	private String errorStackTrace="";
	
	/**
	 * 用户id
	 */
	private String userId;


	
	/**
	 * 错误Id
	 */
	private String errorId;
	
	public CallLogException() {
		super();
	}
	

	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	


	
	@Override
	public CallLogResult getLogType() {
		return CallLogResult.SPSOFT_EXCEPTION_LOG;
	}


	public String getErrorCode() {
		return errorCode;
	}


	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}


	public String getErrorMessage() {
		return errorMessage;
	}


	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	
	public String getErrorId() {
		return errorId;
	}
	public void setErrorId(String errorId) {
		this.errorId = errorId;
	}

	public String getErrorStackTrace() {
		return errorStackTrace;
	}

	public void setErrorStackTrace(String errorStackTrace) {
		this.errorStackTrace = errorStackTrace;
	}
}
