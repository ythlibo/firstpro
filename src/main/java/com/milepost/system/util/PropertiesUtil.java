package com.milepost.system.util;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PropertiesUtil {

	private static final Logger logger = LoggerFactory.getLogger(PropertiesUtil.class);
	
	//项目类路径，即   .....classes/
	static String classPath;
	static {   
		classPath = PropertiesUtil.class.getResource("/").getPath();
		if(classPath!=null && classPath.startsWith("/")){
			classPath = classPath.substring(1);
		}
		//System.out.println(classPath);
	}
	
	//属性文件的路径，相对与类路径，默认是类路径下的config.properties文件
	static String propertiesFilePath = classPath + "config.properties";
	
    /**  
    * 根据主键key读取主键的值value，没有则返回null  
    * @param filePath 属性文件的名称，默认是  “config.properties”，即类路径下的config.properties文件，
    * 也可以带有文件夹路径的，如“”
    * @param key 键名  
    */
	public static String getByKey(String key) {  
		return getByKey(propertiesFilePath, key);
	}
	public static String getByKey(String key, String defaultValue ) {
		String result = getByKey(propertiesFilePath, key); 
		return result==null?defaultValue:result;
	}
    public static String getByKey(String filePath, String defaultValue, String key) {   
        Properties props = new Properties(); 
        String value = null;
        InputStream inputStream = null;
        try {   
            //inputStream = PropertiesUtil.class.getClassLoader().getResourceAsStream(filePath);
        	inputStream = new FileInputStream(filePath);
            props.load(inputStream);   
            value = props.getProperty(key);   
        } catch (Exception e) {   
        	logger.error("PropertiesUtil#getByKey{}..." +  "filePath:" + filePath + ";key:" + key, e);
        }finally{
        	try {
				inputStream.close();
			} catch (Exception e) {
				logger.error("PropertiesUtil#getByKey-close{}..." +  "filePath:" + filePath + ";key:" + key, e);
			}
        }
        return value==null?defaultValue:value;   
    }   
      
    /**  
    * 插入一对properties信息(主键及其键值)  
    * 无论该主键已经存在，都将在属性文件的末尾插件这一对键值，请谨慎使用
    * @param filePath 属性文件的名称，默认是  “config.properties”，即类路径下的config.properties文件，
    * 也可以带有文件夹路径的，如“”
    * @param key 键名  
    * @param value 键值  
    */   
    public static boolean writeProperties(String key,String value) { 
    	return writeProperties(propertiesFilePath, key, value);
    }         
    
    public static boolean writeProperties(String filePath, String key,String value) {          
    	boolean result = false;
    	OutputStream outputStream = null;
    	Properties properties = null;
    	try {   
    		properties = new Properties();
            outputStream = new FileOutputStream(filePath,true);//true表示追加打开，将内容写入文件的结尾
            properties.setProperty(key, value);   
            properties.store(outputStream, null);   
            result = true;
    	} catch (Exception e) {   
        	logger.error("PropertiesUtil#writeProperties{}..." +  "filePath:" + filePath + ";key:" + key + ";value:" + value, e);
        	result = false;
        }finally {
			try {
				outputStream.close();
			} catch (IOException e) {
				logger.error("PropertiesUtil#writeProperties-close{}..." +  "filePath:" + filePath + ";key:" + key + ";value:" + value, e);
				result = false;
			}
		}
    	return result;
    }   
  
    /**  
    * 更新properties文件的键值对  
    * 如果该主键已经存在，更新该主键的值；  
    * 如果该主键不存在，则插件一对键值。
    * @param filePath 属性文件的名称，默认是  “config.properties”，即类路径下的config.properties文件，
    * 也可以带有文件夹路径的，如“”  
    * @param key 键名  
    * @param value 键值  
    */   
    public static boolean updateProperties(String key,String value) {
    	return updateProperties(propertiesFilePath, key, value);
    }
    
    public static boolean updateProperties(String filePath, String key,String value) { 
    	boolean result = false;
    	OutputStream outputStream = null;
    	Properties properties = null;
    	InputStream inputStream = null;
        try {   
        	properties = new Properties();
        	//inputStream = PropertiesUtil.class.getClassLoader().getResourceAsStream(filePath);
        	inputStream = new FileInputStream(filePath);
        	properties.load(inputStream);   
        	outputStream = new FileOutputStream(filePath);//完全覆盖掉文件中的所有内容，即更改文件              
        	properties.setProperty(key, value);   
        	properties.store(outputStream,null);  
        	result = true;
        } catch (Exception e) {   
        	logger.error("PropertiesUtil#updateProperties{}..." +  "filePath:" + filePath + ";key:" + key + ";value:" + value, e);
        	result = false;
        }finally{
        	try {
				inputStream.close();
			} catch (Exception e) {
				logger.error("PropertiesUtil#updateProperties{}-close..." +  "filePath:" + filePath + ";key:" + key + ";value:" + value, e);
				result = false;
			}
        	try {
				outputStream.close();
			} catch (Exception e) {
				logger.error("PropertiesUtil#updateProperties{}-close..." +  "filePath:" + filePath + ";key:" + key + ";value:" + value, e);
				result = false;
			}
        }  
        return result;
    }   
}


