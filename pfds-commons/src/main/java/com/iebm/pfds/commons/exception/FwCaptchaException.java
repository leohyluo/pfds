package com.iebm.pfds.commons.exception;


import org.springframework.security.core.AuthenticationException;

/**
 * 验证码 错误异常
 */
public class FwCaptchaException extends AuthenticationException {
	private static final long serialVersionUID = 1L;

	public FwCaptchaException(String msg) {
		super(msg);
	}

	public FwCaptchaException(String msg, Throwable t) {
		super(msg, t);
	}

}
