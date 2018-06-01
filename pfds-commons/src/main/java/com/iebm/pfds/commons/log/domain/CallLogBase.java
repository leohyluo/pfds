package com.iebm.pfds.commons.log.domain;

import com.alibaba.fastjson.annotation.JSONField;
import com.iebm.pfds.commons.log.type.CallLogLv;
import com.iebm.pfds.commons.log.type.CallLogResult;

import java.util.Date;

/**
 * 请求日志--基类
 */
public abstract class CallLogBase {

	/**
	 * 日志级别
	 */
	private CallLogLv logLv;
	
	/**
	 * 日志内容
	 */
	private String description;
	
	/**
	 * 记录时间
	 */
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	private Date logTime;

	/**
	 * 用户信息 ID
	 */
	private String userAboutInfo;

	/**
	 * 组织ID
	 */
	private String orgId;

	/**
	 * 用户/调用端 IP
	 */
	private String clientIp;

	/**
	 * 服务器IP
	 */
	private String serverIp;

	/**
	 * 服务器端口
	 */
	private String servicePort;

	/**
	 * 服务名称
	 */
	private String serviceName;


	/**
	 * 公司ID
	 */
	private Long companyId;

	/**
	 * 代理编号
	 */
	private String bkCompanyCode;


	/**
	 *接口url
	 */
	private String url;
	/**
	 * 接口请求参数
	 */
	private String params;
	/**
	 *接口所在类
	 */
	private String className;
	/**
	 *请求方法名
	 */
	private String methodName;
	/**
	 *接口请求方式
	 */
	private String methodType;
	/**
	 * 接口的功能类型
	 */
	private String restType;

	/**
	 * 用户操作日志（用于用户操作日志时，进行调用日志过滤的控制，原则上仅业务组装层该属性值为True）
	 */
	private boolean userOpt;

	/**
	 * 请求结果类型
	 * @return
	 */
	public abstract CallLogResult getLogType();

	public String getClientIp() {
		return clientIp;
	}

	public void setClientIp(String clientIp) {
		this.clientIp = clientIp;
	}

	public String getOrgId() {
		return orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

	public Long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}

	public String getBkCompanyCode() {
		return bkCompanyCode;
	}

	public void setBkCompanyCode(String bkCompanyCode) {
		this.bkCompanyCode = bkCompanyCode;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getParams() {
		return params;
	}

	public void setParams(String params) {
		this.params = params;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getMethodName() {
		return methodName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	public String getMethodType() {
		return methodType;
	}

	public void setMethodType(String methodType) {
		this.methodType = methodType;
	}

	public String getRestType() {
		return restType;
	}

	public void setRestType(String restType) {
		this.restType = restType;
	}

	public CallLogLv getLogLv() {
		return logLv;
	}

	public void setLogLv(CallLogLv logLv) {
		this.logLv = logLv;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getLogTime() {
		return logTime;
	}

	public void setLogTime(Date logTime) {
		this.logTime = logTime;
	}

	public String getUserAboutInfo() {
		return userAboutInfo;
	}

	public void setUserAboutInfo(String userAboutInfo) {
		this.userAboutInfo = userAboutInfo;
	}

	public boolean isUserOpt() {
		return userOpt;
	}

	public void setUserOpt(boolean userOpt) {
		this.userOpt = userOpt;
	}

	public String getServerIp() {
		return serverIp;
	}

	public void setServerIp(String serverIp) {
		this.serverIp = serverIp;
	}

	public String getServicePort() {
		return servicePort;
	}

	public void setServicePort(String servicePort) {
		this.servicePort = servicePort;
	}

	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}
}
