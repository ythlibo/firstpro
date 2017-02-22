package com.milepost.system.util;

import org.junit.Test;

/**
 * 为了使单元测试类能获取的类路径能访问到配置文件，需要做如下：
 * 1.在/test_FTPClient/src/test下建立一个resource文件夹
 * 2.将src/main/resources下的文件全部放到新建的resource文件夹中
 * @author HRF
 *
 */
public class PropertiesUtilTest {

	
	/**
	 * 测试updateProperties
	 */
	@Test
	public void test3(){
		String filePath = "config.properties";//如果不传入，默认也是使用这个值
		String key = "pro1";
		String value = "aa_";
		System.out.println(PropertiesUtil.updateProperties(filePath, key, value) );
		System.out.println(PropertiesUtil.updateProperties(key, value));
		
		filePath = "config.properties";//如果不传入，默认也是使用这个值
		key = "pro2";
		value = "pro2_";
		System.out.println(PropertiesUtil.updateProperties(filePath, key, value));
		System.out.println(PropertiesUtil.updateProperties(key, value));
		
		filePath = "ftpConfig.properties";//如果不传入，默认也是使用这个值
		key = "ftp.password";
		value = "ftp.password_";
		System.out.println(PropertiesUtil.updateProperties(filePath, key, value));
		System.out.println(PropertiesUtil.updateProperties(key, value));//更新默认的配置文件，没有该属性则增加
	}
	
	/**
	 * 测试writeProperties
	 */
	@Test
	public void test2(){
		String filePath = "config.properties";//如果不传入，默认也是使用这个值
		String key = "pro1_key";
		String value = "pro1_val";
		System.out.println(PropertiesUtil.writeProperties(filePath, key, value) );
		System.out.println(PropertiesUtil.writeProperties(key, value));
		
		filePath = "config.properties";//如果不传入，默认也是使用这个值
		key = "pro2_key";
		value = "pro2_val";
		System.out.println(PropertiesUtil.writeProperties(filePath, key, value));
		System.out.println(PropertiesUtil.writeProperties(key, value));
		
		filePath = "ftpConfig.properties";//如果不传入，默认也是使用这个值
		key = "ftp.password_key";
		value = "ftp.password_val";
		System.out.println(PropertiesUtil.writeProperties(filePath, key, value));
		System.out.println(PropertiesUtil.writeProperties(key, value));//写入默认的配置文件
	}

	
	/**
	 * 测试getByKey
		#system's property
		SYS_COMPANY = Milepost
		SYS_NAME = Milepost\u534F\u540C\u529E\u516C\u7CFB\u7EDF
		SYS_VERSION_CODE = 1.0
		SYS_UPLOAD_PATH = G:/milepostUpload/
		SYS_TEMP_PATH = G:/milepostTemp/
		SYS_PAGE_SIZE = 10
	 * 
	 */
	@Test
	public void test1(){
		String key = "SYS_COMPANY";
		System.out.println(PropertiesUtil.getByKey(key));
		System.out.println(PropertiesUtil.getByKey(key, "sss"));
		System.out.println(PropertiesUtil.getByKey(key+"ss", "sss"));
		System.out.println(PropertiesUtil.getByKey(PropertiesUtil.classPath + "db.properties", "jdbc.user", "sssss"));
	}	
}
