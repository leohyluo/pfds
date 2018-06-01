package com.iebm.pfds.commons.log.type;

/**
 * 日志级别敏感等级
 * @author bliao
 *
 */
public enum CallLogLv {
	/**
	 * 普通
	 */
	NORMAL(1),
	/**
	 * 敏感
	 */
	SENSITIVITY(2),
	/**
	 * 审计
	 */
	AUDITS(3);
	
	private int lv;
	public int getLv() {
		return lv;
	}
	
	private CallLogLv(int lv) {
		this.lv = lv;
	}
}
