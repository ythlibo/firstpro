package com.milepost.system.util;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.client.CookieStore;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.junit.Test;

import net.sf.json.JSONObject;

/**
 * 测试HttpClientUtil
 */
public class TestHttpClientUtil {
	
	
	
	/**
	 * 测试Cookie
	 * HttpClient4.x可以自带维持会话功能，只要使用同一个HttpClient且未关闭连接，则可以保持同一个会话，
	 * 如果想在多个HttpClient连接之间保持相同的会话，就需要自己保存会话信息，因为客户端的会话信息是保存在cookie中的（JSESSIONID），
	 * 所以只需要将登录成功返回的cookie(从响应头中获取)复制到各个HttpClient使用即可。
	 */
	@Test
	public void test7(){
		//用户登录
		String verifyUrl = "http://localhost:8080/test_httpclient_cookie/testHttpClientCookie/login?username=zhangsan&pwd=123456";
		Map<String,String> userInfo = new HashMap<String, String>();
		userInfo.put("username", "zhangsan");
		userInfo.put("pwd", "123456");
		//result = "{\"code\":\"1\",\"jSessionId\":\""+ session.getId() +"\"}";
		String reslut = HttpClientUtil.httpClientLogin(verifyUrl, userInfo);
		JSONObject jsonObject = JSONObject.fromObject(reslut);
		String JSESSIONID = (String)jsonObject.get("jSessionId");
		if("1".equals(jsonObject.get("code"))){
			System.out.println("JSESSIONID:" + JSESSIONID);
		}
		
		//登录后访问其他资源
		String url = "http://localhost:8080/test_httpclient_cookie/testHttpClientCookie/getResource1";
		Map<String,String> newsInfo = new HashMap<String, String>();
		newsInfo.put("newsId", "123abc");
		
		
		BasicClientCookie clientCookie = new BasicClientCookie("JSESSIONID", JSESSIONID);
		clientCookie.setDomain("localhost");
		clientCookie.setPath("/");
		CookieStore cookieStore = HttpClientUtil.buildCookieStore(clientCookie );
		
		reslut = HttpClientUtil.doGet(url, newsInfo, cookieStore );
		System.out.println(reslut);
	}
	
	/**
	 * HttpClientUtil.doPostJson
	 */
	@Test
	public void test6(){
		String url = "http://localhost:8080/test_httpclient/testHttpClientUtil/test18";
		//写死的json
		//String json = "{\"username\" : \"zhagnsan\", \"pwd\" : \"123456\"}";
		Map<String, Object> paramsMap = new HashMap<String, Object>();
		paramsMap.put("username", "zhangsan");
		paramsMap.put("pwd", "123456");
		List<String> bookList = new ArrayList<String>();
		bookList.add("JAVA");
		bookList.add("C");
		bookList.add("C++");
		bookList.add("C#");
		paramsMap.put("bookList", bookList);
		JSONObject jsonObject = JSONObject.fromObject(paramsMap);
		String reslut = HttpClientUtil.doPostJson(url, jsonObject );
		System.out.println(reslut);
		
		System.out.println("------------------------------------------------------");
		
		url = "https://localhost:8443/test_httpclient/testHttpClientUtil/test18";
		reslut = HttpClientUtil.doPostJson(url, jsonObject );
		System.out.println(reslut);
	}
	
	/**
	 * HttpClientUtil.doPostMultipart
	 */
	@Test
	public void test5(){
		String url = "http://localhost:8080/test_httpclient/testHttpClient/fileUpload";
		Map<String, Object> paramsMap = new HashMap<String, Object>();
		paramsMap.put("username", "zhangsan");
		paramsMap.put("pwd", "123456");
		File file = new File("C:\\Users\\HRF\\Desktop\\TestPicture\\china.txt", "");
		paramsMap.put("file", file);
		String reslut1 = HttpClientUtil.doPostMultipart(url, paramsMap );
		System.out.println(reslut1);
		
		System.out.println("------------------------------------------------------");
		
		url = "https://localhost:8443/test_httpclient/testHttpClient/fileUpload";
		String reslut2 = HttpClientUtil.doPostMultipart(url, paramsMap );
		System.out.println(reslut2);
		
	}
	
	/**
	 * HttpClientUtil.doPost
	 */
	@Test
	public void test4(){
		String url = "http://localhost:8080/test_httpclient/testHttpClientUtil/test17";
		String reslut = HttpClientUtil.doPost(url);
		System.out.println(reslut);
		
		System.out.println("------------------------------------------------------");
		
		url = "https://localhost:8443/test_httpclient/testHttpClientUtil/test17";
		reslut = HttpClientUtil.doPost(url);
		System.out.println(reslut);
	}
	
	/**
	 * HttpClientUtil.doPost(url, paramsMap );
	 */
	@Test
	public void test3(){
		String url = "http://localhost:8080/test_httpclient/testHttpClientUtil/test17";
		Map<String, String> paramsMap = new HashMap<String, String>();
		paramsMap.put("username", "zhangsan");
		paramsMap.put("pwd", "123456");
		String reslut = HttpClientUtil.doPost(url, paramsMap );
		System.out.println(reslut);
		
		System.out.println("------------------------------------------------------");
		
		url = "https://localhost:8443/test_httpclient/testHttpClientUtil/test17";
		reslut = HttpClientUtil.doPost(url, paramsMap );
		System.out.println(reslut);
		
	}
	
	/**
	 * HttpClientUtil.doGet(url, paramsMap )
	 */
	@Test
	public void test2(){
		String url = "http://localhost:8080/test_httpclient/testHttpClientUtil/test17";
		Map<String, String> paramsMap = new HashMap<String, String>();
		paramsMap.put("username", "zhangsan");
		paramsMap.put("pwd", "123456");
		String reslut = HttpClientUtil.doGet(url, paramsMap );
		System.out.println(reslut);
		
		System.out.println("------------------------------------------------------");
		
		url = "https://localhost:8443/test_httpclient/testHttpClientUtil/test17";
		reslut = HttpClientUtil.doGet(url, paramsMap );
		System.out.println(reslut);
		
	}
	
	/**
	 * HttpClientUtil.doGet
	 */
	@Test
	public void test1(){
		String url = "http://localhost:8080/test_httpclient/testHttpClientUtil/test17";
		String reslut = HttpClientUtil.doGet(url);
		System.out.println(reslut);
		
		System.out.println("------------------------------------------------------");
		
		url = "https://localhost:8443/test_httpclient/testHttpClientUtil/test17";
		reslut = HttpClientUtil.doGet(url);
		System.out.println(reslut);
	}
	
	
}