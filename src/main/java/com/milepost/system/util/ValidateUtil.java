package com.milepost.system.util;

import java.util.Collection;

/**
 * 校验工具类
 * @author HRF
 */
public class ValidateUtil {

	
	/**
	 * 判断字符串是否为null、""，不为空的时候，返回true，否则返回false
	 * @param src
	 * @return
	 */
	public static boolean isValid(String src){
		return !(src == null || "".equals(src.trim()));
	}
	
	/**
	 * 判断集合的有效性，有效返回true，否则返回false，这里的形参Collection不要指定泛型，否则调用此方法的时候也要传入带泛型的参数
	 * @param collection
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static boolean isValid(Collection collection){
		if(collection==null || collection.isEmpty()){
			return false;
		}
		return true;
	}
	
	/**
	 * 判断是数组是否有效
	 * @param arr
	 * @return
	 */
	public static boolean isValid(Object[] arr){
		if(arr==null || arr.length==0){
			return false;
		}
		return true;
	}
	
}
