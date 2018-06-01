package com.iebm.pfds.commons.log.domain;


import com.iebm.pfds.commons.log.type.CallLogResult;

/**
 * 请求日志--正常日志
 * @author bliao
 *
 */
public class CallLogReturn extends CallLogBase{

	@Override
	public CallLogResult getLogType() {
		return CallLogResult.CALL_RETURN_LOG;
	}

}
