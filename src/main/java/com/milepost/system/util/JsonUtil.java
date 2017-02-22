package com.milepost.system.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class JsonUtil {
	
	/**
	 * 将json字符串转化成Map<String, Object>，要求第一层是json对象
	 * 其中，Object可以是Map<String, Object>、List<Object>、String,
	 * 第二层的Objec也可以是Map<String, Object>、List<Object>、String,
	 * 支持多层嵌套
	 * @param jsonObjectStr
	 * @return
	 */
	public static Map<String, Object> jsonObject2Map(String jsonObjectStr) {
		Map<String, Object> map = new HashMap<String, Object>();    
        JSONObject json = JSONObject.fromObject(jsonObjectStr); 
        for(Object key : json.keySet()){    
            Object value = json.get(key);
            if(value instanceof JSONArray){
            	JSONArray jsonArray = JSONArray.fromObject(value);
                map.put(key.toString(), jsonArray2List(jsonArray.toString()));    
            } else if(value instanceof JSONObject) {    
            	JSONObject jsonObject = JSONObject.fromObject(value);
            	map.put(key.toString(), jsonObject2Map(jsonObject.toString()));    
            } else{
            	map.put(key.toString(), value);    
            }   
        }    
        return map; 
	}
	
	/**
	 * 将json字符串转化成List<Object>，要求第一层是json数组
	 * 其中，Object可以是Map<String, Object>、List<Object>、String,
	 * 第二层的Objec也可以是Map<String, Object>、List<Object>、String,
	 * 支持多层嵌套
	 * @param jsonArrayStr
	 * @return
	 */
	public static List<Object> jsonArray2List(String jsonArrayStr) {
		List<Object> list = new ArrayList<Object>();    
		JSONArray json = JSONArray.fromObject(jsonArrayStr); 
		for(Object object : json){    
            if(object instanceof JSONArray){
            	JSONArray jsonArray = JSONArray.fromObject(object);
                list.add(jsonArray2List(jsonArray.toString()));    
            } else if(object instanceof JSONObject) {    
            	JSONObject jsonObject = JSONObject.fromObject(object);
            	list.add(jsonObject2Map(jsonObject.toString()));    
            } else{
            	list.add(object);
            }   
        }     
        return list; 
	}
}
