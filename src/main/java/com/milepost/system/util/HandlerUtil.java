package com.milepost.system.util;

/**
 * SpringMVC 的handler所用的工具类
 * @author HRF
 */
public class HandlerUtil {

	public static final String SUCCESS_MESSAGE = "操作成功！";
	public static final String FAILURE_MESSAGE = "操作失败！";
	public static final String SERVER_VALIDATE_FAILURE_MESSAGE = "服务端校验失败，怀疑您使用了非正常手段提交了非法数据，请正确操作！";
	public static final String LOG_FORMAT = "Parameters={}";
	/**
	 * 执行add和update的handler method的返回值字符串
	 * @param isSuccess 1：成功；0：失败
	 * @param message 提示信息
	 * @return
	 */
	public static String returnMessage (int isSuccess, String message){
		return "{\"isSuccess\" : \""+ isSuccess +"\", \"message\" : \""+ message +"\"}";
	}
}
