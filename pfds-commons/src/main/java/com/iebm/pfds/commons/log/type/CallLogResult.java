package com.iebm.pfds.commons.log.type;

/**
 * 调用结果
 * @author bliao
 *
 */
public enum CallLogResult {

	/**
	 * 调用
	 */
	CALL_LOG,
	
	/**
	 * 调用正常返回
	 */
	CALL_RETURN_LOG,
	

	/**
	 * 系统异常返回
	 */
	SYSTEM_EXCEPTION_LOG,
	

	/**
	 * 业务异常返回
	 */
	SPSOFT_EXCEPTION_LOG
}
