package com.iebm.pfds.commons.exception;

/**
 * 商品服务业务异常定义
 */
public  enum FwSpsoftExceptionCode {
	
	/***数值转换错误*/
	NUMBER_FORMAT_EXCEPTION (new SpsoftExceptionMsg("0001", "数值转换错误",SpsoftExceptionLevel.SEVERITY,"0000", null)), 
	/***系统IO错误*/
	IO_EXCEPTION (new SpsoftExceptionMsg("0002", "系统IO错误",SpsoftExceptionLevel.EMERGENCY,"0000", null)),
	/***空指针错误*/
	NULL_POINTER_EXCEPTION (new SpsoftExceptionMsg("0003", "空指针错误",SpsoftExceptionLevel.EMERGENCY,"0000", null)),
	/***对象类型转换错误*/
	CLASS_CAST_EXCEPTION (new SpsoftExceptionMsg("0004", "对象类型转换错误",SpsoftExceptionLevel.EMERGENCY,"0000", null)),
	/***SQL执行异常*/
	SQL_EXCEPTION (new SpsoftExceptionMsg("0005", "SQL执行异常",SpsoftExceptionLevel.EMERGENCY,"0000", null)),
	/***服务连接异常*/
	CONNECT_EXCEPTION (new SpsoftExceptionMsg("0006", "服务连接异常",SpsoftExceptionLevel.EMERGENCY,"0000", null)),
	/***表单验证失败*/
	FORM_VALID_EXCEPTION (new SpsoftExceptionMsg("0007", "表单验证失败",SpsoftExceptionLevel.NORMAL,"0000", null)),
	/***不允许访问*/
	ACCESS_DENIED_EXCEPTION (new SpsoftExceptionMsg("0008", "不允许访问",SpsoftExceptionLevel.NORMAL,"0000", null)),
	/***不支持该请求方法*/
	METHOD_NOT_SUPPORT_EXCEPTION (new SpsoftExceptionMsg("0009", "不支持该请求方法",SpsoftExceptionLevel.NORMAL,"0000", null)),
	/***参数绑定错误*/
	SPRINGMVC_ARGS_BIND_EXCEPTION(new SpsoftExceptionMsg("0010", "参数绑定错误，请检查提交的参数数据类型",SpsoftExceptionLevel.NORMAL,"0000", null)),
	/***参数验证失败*/
	SPRINGMVC_ARGS_EXCEPTION(new SpsoftExceptionMsg("0011", "参数验证失败",SpsoftExceptionLevel.NORMAL,"0000", null)),
	/***其它异常*/
	UNKNOW_EXCEPTION (new SpsoftExceptionMsg("9999", "其它异常",SpsoftExceptionLevel.UNKNOW,"0000", null)),

	/***其它异常*/
	DB_BLOB_ENCODING_EXCEPTION (new SpsoftExceptionMsg("9501", "Blob类型编码转换错误",SpsoftExceptionLevel.NORMAL,"0000", null)),

	/***内部异常*/
	RPC_EXPDECODE_PARSE_EXCEPTION (new SpsoftExceptionMsg("9800", "内部异常，无法解析内部接口内容！",SpsoftExceptionLevel.SEVERITY,"0000", null)),
	/***其它任务执行失败*/
	TRANSACTION_OTHER_SPAN_FAIL_EXCEPTION(new SpsoftExceptionMsg("9700", "其它任务执行失败，本地调用事务回滚",SpsoftExceptionLevel.NORMAL,"0000", null)),
	/***链路ID与补偿来源会话ID为空*/
	TRANSACTION_ID_NULL_EXCEPTION (new SpsoftExceptionMsg("9701", "链路ID与补偿来源会话ID为空，无法处理事务补偿",SpsoftExceptionLevel.NORMAL,"0000", null)),


	ZIP_FILE_SOURCE_NOT_EXISTS (new SpsoftExceptionMsg("9601", "压缩源文件不存在（{0}）",SpsoftExceptionLevel.NORMAL,"0000", null)),
	ZIP_FILE_TARGET_EXISTS (new SpsoftExceptionMsg("9602", "压缩目标文件已存在（{0}）",SpsoftExceptionLevel.NORMAL,"0000", null)),
	ZIP_FILE_ZIP_ERROR(new SpsoftExceptionMsg("9603", "压缩失败（{0}）",SpsoftExceptionLevel.NORMAL,"0000", null)),
	ZIP_FILE_UNZIP_ERROR(new SpsoftExceptionMsg("9604", "解压缩失败（{0}）",SpsoftExceptionLevel.NORMAL,"0000", null)),
	ZIP_FILE_Source_NOT_DIRECTORY(new SpsoftExceptionMsg("9605", "压缩源文件不是文件夹（{0}）",SpsoftExceptionLevel.NORMAL,"0000", null));



	private FwSpsoftExceptionCode(SpsoftExceptionMsg msg){this.message=msg;}
	private SpsoftExceptionMsg message;
	public SpsoftExceptionMsg get(){return message;}
	
}
