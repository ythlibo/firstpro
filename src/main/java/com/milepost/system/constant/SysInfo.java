package com.milepost.system.constant;

import com.milepost.system.util.PropertiesUtil;

/**
 * 存放系统配置信息的类，所有的属性，只在系统启动时候从属性文件中读取，其余时候只从类中读取
 * @author HRF
 */
public class SysInfo {
	/**
	 * 技术支持
	 */
	public static String SYS_COMPANY = PropertiesUtil.getByKey("SYS_COMPANY", "Milepost");
	
	/**
	 * 系统名称
	 */
	public static String SYS_NAME = PropertiesUtil.getByKey("SYS_NAME", "Milepost协同办公系统");
	
	/**
	 * 系统版本号
	 */
	public static String SYS_VERSION_CODE = PropertiesUtil.getByKey("SYS_VERSION_CODE", "1.0");
	
	/**
	 * 上传文件的目录
	 */
	public static String SYS_UPLOAD_PATH = PropertiesUtil.getByKey("SYS_UPLOAD_PATH", null);
	
	/**
	 * 上传文件的临时目录
	 */
	public static String SYS_TEMP_PATH = PropertiesUtil.getByKey("SYS_TEMP_PATH", null);
	
	public static int SYS_PAGE_SIZE =  Integer.parseInt(PropertiesUtil.getByKey("SYS_PAGE_SIZE", "10"));
	

}
