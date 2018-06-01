package com.iebm.pfds.commons.exception;

import java.text.MessageFormat;

/**
 * 服务业务异常对象
 */
public class FwSpsoftException extends SpsoftException {

	private static final long serialVersionUID = 1L;

	public FwSpsoftException(FwSpsoftExceptionCode code1) {
		super(
				code1.get().getServerNo()+code1.get().getErrorCode(),//完整错误码=服务编号+错误码
				code1.get().getMessage(),//错误描述
				code1.get().getErrorLV()); //错误级别
	}

	public FwSpsoftException(FwSpsoftExceptionCode code1,Object... o) {
		super(
				code1.get().getServerNo()+code1.get().getErrorCode(),//完整错误码=服务编号+错误码
				MessageFormat.format(code1.get().getMessage(),o),//错误描述
				code1.get().getErrorLV()); //错误级别
	}
}
