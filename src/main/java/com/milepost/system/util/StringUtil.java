package com.milepost.system.util;

/**
 * 字符串工具类
 * @author HRF
 */
public class StringUtil {
	
	/**
	 * 为like关键字解析一个String，
	 * @return
	 */
	public static String parseStrForLike(String value) {
		if(value == null){
			return "%%";
		}else{
			return "%"+ value +"%";
		}
	}
	
	/**
	 * 按照指定的字符拆分一个字符串，返回字符串数组
	 * 
	 * @param str
	 * @param tag
	 * @return
	 */
	public static String[] str2Arr(String str, String tag) {
		if (ValidateUtil.isValid(str)) {
			return str.split(tag);
		}
		return null;
	}

	/**
	 * 将一个数组转换成一个字符串，以指定的字符分割
	 * 
	 * @param value
	 * @param tag
	 * @return
	 */
	public static String arr2Str(Object[] value, String tag) {
		String temp = "";
		if (ValidateUtil.isValid(value)) {
			for (Object object : value) {
				temp = temp + object + tag;
			}
			return temp.substring(0, temp.length() - 1);
		}
		return temp;
	}
	

	/**
	 * 判断在 values 数组中是否含有指定的 value 字符串
	 * 
	 * @param values
	 * @param value
	 * @return
	 */
	public static boolean contains(String[] values, String value) {
		if (ValidateUtil.isValid(values)) {
			for (String str : values) {
				if (str.equals(value)) {
					return true;
				}
			}
		}
		return false;
	}
	
	/**
	 * 返回一个字符的剪短描述，长度为 length
	 * @param str
	 * @param length
	 * @return
	 */
	public static String getDescString (String str,int length){
		if(ValidateUtil.isValid(str) && str.length()>length){
			return str.substring(0, length);
		}
		return str;
	}
	

	/**
	 * 将一个字符串转化成一个int，如果转化失败或转化后的结果<1,则返回defaultValue
	 * 主要用于处理从前台接受过来的pageNo和pageSize
	 * @param src
	 * @return
	 */
	public static int str2Int(String src,int defaultValue){
		int srcInt = defaultValue;
		try {
			srcInt = Integer.parseInt(src);
			if(srcInt < 1){
				srcInt = defaultValue;
			}
		} catch (Exception e) {}
		return srcInt;
	}

}
