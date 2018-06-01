package com.iebm.pfds.commons.exception;

import java.util.Date;

/**
 * 自定义业务异常日志
 *
 */
public class FwExceptionLog {

	
	/**
	 * 错误码
	 */
	private String errorCode;
	/**
	 * 错误描述
	 */
	private String errorMessage;
	/**
	 * 错误级别
	 */
	private Integer errorLV;
	/**
	 * 原始错误对象
	 */
	private String ex ;
	
	/**
	 * 错误事件编码
	 */
	private String errorId;
	/**
	 * 错误发生时间
	 */
	private Date errorTime;
	
	/**
	 * 请求url
	 */
	private String requestUrl;
	
	/**
	 * 请求参数
	 */
	private String requestParam;
	
	/**
	 * 请求方式
	 */
	private String requestMethod;
	
	/**
	 * 用户id
	 */
	private String userId;

	/**
	 * 组织id
	 */
	private String orgId;


	
	public void setSpsoftException(SpsoftException ex){
		this.errorCode=ex.getErrorCode();
		this.errorLV=ex.getErrorLV();
		this.errorMessage=ex.getMessage();
		this.errorId=ex.getErrorId();
		this.errorTime=ex.getErrorTime();
		
		
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



	public Integer getErrorLV() {
		return errorLV;
	}



	public void setErrorLV(Integer errorLV) {
		this.errorLV = errorLV;
	}



	public String getEx() {
		return ex;
	}



	public void setEx(String ex) {
		this.ex = ex;
	}



	public String getErrorId() {
		return errorId;
	}



	public void setErrorId(String errorId) {
		this.errorId = errorId;
	}



	public Date getErrorTime() {
		return errorTime;
	}



	public void setErrorTime(Date errorTime) {
		this.errorTime = errorTime;
	}



	public String getRequestUrl() {
		return requestUrl;
	}



	public void setRequestUrl(String requestUrl) {
		this.requestUrl = requestUrl;
	}



	public String getRequestParam() {
		return requestParam;
	}



	public void setRequestParam(String requestParam) {
		this.requestParam = requestParam;
	}



	public String getRequestMethod() {
		return requestMethod;
	}



	public void setRequestMethod(String requestMethod) {
		this.requestMethod = requestMethod;
	}



	public String getUserId() {
		return userId;
	}



	public void setUserId(String userId) {
		this.userId = userId;
	}



	public String getOrgId() {
		return orgId;
	}



	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}
	
}
