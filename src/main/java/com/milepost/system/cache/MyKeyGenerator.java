package com.milepost.system.cache;

import java.lang.reflect.Method;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.interceptor.KeyGenerator;

import com.milepost.system.util.StringUtil;

/**
 * 自定义缓存key生成器
 * @author HRF
 */ 
public class MyKeyGenerator implements KeyGenerator{

	private static final Logger log = LoggerFactory.getLogger(MyKeyGenerator.class);
	
	/**
	 * Object arg0：目标对象
	 * Method arg1：方法名
	 * Object... arg2：参数列表
	 */
	public Object generate(Object arg0, Method arg1, Object... arg2) {
		String className = arg0.getClass().getSimpleName();
		String mname = arg1.getName();
		String params = StringUtil.arr2Str(arg2,",");
		//因为 Service 由Spring的IOC容器管理，而默认情况下，IOC容器中所有的bean都是单例的，所以所有的 HashCode 是一样的，这里也可以不加 HashCode
		String key = className + "@" + arg0.hashCode() + "." + mname + "("+params+")" ;
		log.info("读取key为{}的缓存数据...",key);
		return key;
	}

}
