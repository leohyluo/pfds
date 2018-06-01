package com.iebm.pfds.commons.exception;

/**
 * 异常消息
 */
public class SpsoftExceptionMsg {

	/**
	 * 异常编码（服务、模块内自行定义的异常编码）
	 */
	private String errorCode;
	/**
	 * 异常描述消息
	 */
	private String message;
	/**
	 * 异常级别
	 */
	private SpsoftExceptionLevel errorLV;
	/**
	 * 服务编号（功能模块的编号）
	 */
	private String serverNo;
	/**
	 * 服务实例ID，可空
	 */
	private String serverEntityId;
	
	
	
	public SpsoftExceptionMsg(String errorCode, String message, SpsoftExceptionLevel errorLV) {
		super();
		this.errorCode = errorCode;
		this.message = message;
		this.errorLV = errorLV;
	}
	public SpsoftExceptionMsg(String errorCode, String message, SpsoftExceptionLevel errorLV,
			String serverNo, String serverEntityId) {
		super();
		this.errorCode = errorCode;
		this.message = message;
		this.errorLV = errorLV;
		this.serverNo = serverNo;
		this.serverEntityId = serverEntityId;
	}
	public String getErrorCode() {
		return errorCode;
	}
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public SpsoftExceptionLevel getErrorLV() {
		return errorLV;
	}
	public void setErrorLV(SpsoftExceptionLevel errorLV) {
		this.errorLV = errorLV;
	}
	public String getServerNo() {
		return serverNo;
	}
	public void setServerNo(String serverNo) {
		this.serverNo = serverNo;
	}
	public String getServerEntityId() {
		return serverEntityId;
	}
	public void setServerEntityId(String serverEntityId) {
		this.serverEntityId = serverEntityId;
	}
	
	
	
}
