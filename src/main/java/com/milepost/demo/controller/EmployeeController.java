package com.milepost.demo.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.milepost.system.util.HttpClientUtil;
import com.milepost.system.util.JsonUtil;


@RequestMapping("/employee")
@Controller
public class EmployeeController {

	private static Logger logger = LoggerFactory.getLogger(EmployeeController.class);
	
	@ResponseBody
	@RequestMapping("aaa") 
	public Map<String, Object> testMuiAjax(@RequestParam Map<String, String> paramsMap){
		//测试接受参数
		System.out.println(paramsMap);
		
		//返回结果
		Map<String, Object> resultMap = new HashMap<String,Object>();
		resultMap.put("code", "9");
		resultMap.put("msg", "操作成功！");
		Map<String, Object> bodyMap = new HashMap<String,Object>();
		resultMap.put("body", bodyMap);
		bodyMap.put("pageSize", "12");
		bodyMap.put("currentPage", "1");
		bodyMap.put("totalPages", "43");
		List<Map<String, String>> bodyList = new ArrayList<Map<String,String>>();
		bodyMap.put("bodyList", bodyList);
		Map<String, String> map1 = new HashMap<String,String>();
		map1.put("id", "1");
		map1.put("name", "张三1");
		map1.put("age", "12");
		map1.put("email", "zhangsan@wisdom.com");
		map1.put("remark", "张三备注信息");
		bodyList.add(map1);
		Map<String, String> map2 = new HashMap<String,String>();
		map2.put("id", "2");
		map2.put("name", "李四");
		map2.put("age", "33");
		map2.put("email", "lisi@wisdom.com");
		map2.put("remark", "李四备注信息");
		bodyList.add(map2);
		Map<String, String> map3 = new HashMap<String,String>();
		map3.put("id", "3");
		map3.put("name", "王五");
		map3.put("age", "54");
		map3.put("email", "wangwu@wisdom.com");
		map3.put("remark", "王五备注信息");
		bodyList.add(map3);
		return resultMap;
	}
	
	/*@RequestMapping("/testJdbcTemplate")
	public void testSelectAll(){
		String sql = "SELECT * FROM EMPLOYEE";
		List<Map<String, Object>> employeeList = jdbcTemplate.queryForList(sql);
		System.out.println(employeeList);
	}*/
	
	@RequestMapping("/testLogger")
	public void testLogger(){
		logger.debug("Controller输出的日志");
		
		
	}
	
	
	@RequestMapping("/testPage")
	public void testPage(@RequestParam Map<String,Object> paramsMap, int pageNo, @RequestParam int pageSize){
		try {
			System.out.println(paramsMap);
			System.out.println(pageSize);
			System.out.println(pageNo);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@ResponseBody
	@RequestMapping("/testLogin")
	public Map<String, String> testLogin(@RequestParam Map<String,Object> paramsMap, HttpServletRequest request){
		Map<String, String> sessionMap = new HashMap<String, String>();
		try {
			HttpSession session = request.getSession();
			//获取登录的用户信息
			String username = (String) paramsMap.get("username");
			String password = (String) paramsMap.get("password");
			System.out.println("testLogin[username:"+username+"password;"+password+"]");
			//放入session中
			session.setAttribute("username", username);
			session.setAttribute("password", password);
			//返回JSESSIONID
			//sessionMap.put("JSESSIONID", session.getId());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sessionMap;
	}
	
	@ResponseBody
	@RequestMapping("/testLoginAfter1")
	public Map<String, String> testLoginAfter1(@RequestParam Map<String,Object> paramsMap, HttpServletRequest request){
		Map<String, String> sessionMap = new HashMap<String, String>();
		try {
			HttpSession session = request.getSession();
			String username = (String) session.getAttribute("username");
			String password = (String) session.getAttribute("password");
			System.out.println("testLoginAfter1[username:"+username+"password;"+password+"]");
			if(username==null || password==null){
				//用户信息为null，表示当前用户没有登录或者登录已经失效了，重新返回一个新的JSESIONID
				//sessionMap.put("JSESSIONID", session.getId());
				sessionMap.put("username", null);
				sessionMap.put("password", null);
			}else{
				//否则返回AlreadyLogin
				sessionMap.put("JSESSIONID", "AlreadyLogin");
				sessionMap.put("username", username);
				sessionMap.put("password", password);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sessionMap;
	}
	
	/**
	 * 
	 * @param paramMap
	 */
	@ResponseBody
	@RequestMapping("/testHttpClient")
	public Map<String,Object> testHttpClient(@RequestParam Map<String, String> paramMap){
		//从前端接收到的参数，不要使用命名参数，直接使用Map接收参数，这样即使前端没传过来参数也不会报错
		System.out.println("从前端接受到的参数：" + paramMap);
		paramMap.put("username", "张三");
		//请求后台的地址
		String url = "http://192.168.124.21:8080/api/cy/choice.htm";
		//将参数直接传到后台
		String response = HttpClientUtil.doPost(url, paramMap);
		System.out.println(response);
		if(response!=null) {
			response = response.trim();
			response = Pattern.compile("^\"").matcher(response).replaceAll("");
			response = Pattern.compile("\"$").matcher(response).replaceAll("");
		}	
		System.out.println(response);
		//第一层是json数组
		//List<Object> list = JsonUtil.jsonArray2List(response);

		//第一层是json对象
		Map<String,Object> map = JsonUtil.jsonObject2Map(response);
		
		for(Map.Entry<String, Object> entry : map.entrySet()){
			String key = entry.getKey();
			Object value = entry.getValue();
			System.out.println("key:" + key);
			System.out.println("value:" + value);
		}
		List<Object> dlxList = (List<Object>) map.get("dlx");
		for(Object object : dlxList){
			Map<String, Object> dlxMap = (Map<String, Object>) object;
			for(Map.Entry<String, Object> entry : dlxMap.entrySet()){
				System.out.println(entry.getKey());
				System.out.println(entry.getValue());
			}
		}
		List<Object> xlxList = (List<Object>) map.get("xlx");
		for(Object object : xlxList){
			Map<String, Object> xlxMap = (Map<String, Object>) object;
			for(Map.Entry<String, Object> entry : xlxMap.entrySet()){
				System.out.println(entry.getKey());
				System.out.println(entry.getValue());
			}
		}
		return map;
	}
	
	//http://192.168.171.44:8080/firstpro/employee/testCallback
	@ResponseBody
	@RequestMapping("/testCallback")
	public Map<String,Object> testCallback(@RequestParam Map<String,Object> paramsMap){
		try {
			System.out.println(paramsMap);
			long sleep = Long.parseLong(paramsMap.get("sleep").toString()); 
			Thread.sleep(1000*sleep);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return paramsMap;
	}
	
}
