package com.milepost.system.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.security.KeyStore;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.net.ssl.SSLContext;

import org.apache.http.HeaderIterator;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.CookieStore;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContexts;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.json.JSONObject;

/**
 * HTTP 请求工具类， 参见 http://blog.csdn.net/happylee6688/article/details/47148227
 * http://www.cnblogs.com/YDDMAX/p/5380131.html 在此基础上改进了一些不合理的地方
 * 
 * @author HRF
 */
public class HttpClientUtil {
	
	private static Logger logger = LoggerFactory.getLogger(HttpClientUtil.class);
	
	private static PoolingHttpClientConnectionManager connMgr;
	private static RequestConfig requestConfig;
	private static final int MAX_TIMEOUT = 7000;// ms
	private static final String keyStoreFilePath = "D:\\Tomcat7\\httpsFile\\tomcat.keystore";
	private static final String keyStorePassword = "password";
	
	static {
		// 设置truststore
		SSLContext sslcontext = null;
		try {
			sslcontext = SSLContexts.custom()
					.loadTrustMaterial(getKeyStore(keyStoreFilePath, keyStorePassword), new TrustSelfSignedStrategy())
					.build();
		} catch (Exception e) {
			logger.error("HttpClientUtil static block occur exception", e);
		}
		// 创建连接的Registry
		Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory> create()
				.register("http", PlainConnectionSocketFactory.INSTANCE)
				.register("https", new SSLConnectionSocketFactory(sslcontext)).build();
		connMgr = new PoolingHttpClientConnectionManager(socketFactoryRegistry);
		// 最大连接数
		connMgr.setMaxTotal(100);
		connMgr.setDefaultMaxPerRoute(connMgr.getMaxTotal());

		RequestConfig.Builder configBuilder = RequestConfig.custom();
		// 设置连接超时
		configBuilder.setConnectTimeout(MAX_TIMEOUT);
		// 设置读取超时
		configBuilder.setSocketTimeout(MAX_TIMEOUT);
		// 设置从连接池获取连接实例的超时
		configBuilder.setConnectionRequestTimeout(MAX_TIMEOUT);
		// 在提交请求之前 测试连接是否可用
		configBuilder.setStaleConnectionCheckEnabled(true);

		requestConfig = configBuilder.build();
	}
	
	/**
	 * 建立CookieStore，如果指定的BasicClientCookie不为null，则加入到其中
	 * @param clientCookie
	 * @return
	 */
	public static CookieStore buildCookieStore(BasicClientCookie clientCookie) {
		CookieStore cookieStore = null;
		try {
			cookieStore = new BasicCookieStore();
			if(clientCookie != null)
				cookieStore.addCookie(clientCookie);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(),e);
		}
		return cookieStore;
	}

	/**
	 * 使用HttpClient访问post方式访问一个接口，提交用户登录信息，进行登录，
	 * 服务端需要返回登录是否成功，如果成功则把用户信息放入session中，并且返回sessionId，即Cookie中的JSESSIONID，
	 * @param verifyUrl
	 * @param userInfo
	 * @return
	 */
	public static String httpClientLogin(String verifyUrl, Map<String, String> userInfo) {
		return doPost(verifyUrl, userInfo);
	}


	/**
	 * 发送 GET 请求（HTTP），不带输入数据
	 * 
	 * @param url
	 * @param cookieStore
	 * @return
	 */
	public static String doGet(String url) {
		return doGet(url, null, null);
	}
	public static String doGet(String url, CookieStore cookieStore) {
		return doGet(url, null, cookieStore);
	}

	public static String doGet(String url, Map<String, String> params) {
		return doGet(url, params, null);
	}

	/**
	 * 发送 GET 请求（HTTP），K-V形式，最终将参数拼接在了url后面
	 * 
	 * @param url
	 * @param params
	 * @param cookieStore
	 * @return
	 */
	public static String doGet(String url, Map<String, String> params, CookieStore cookieStore) {
		StringBuffer param = new StringBuffer();
		int i = 0;
		if (params != null) {
			for (String key : params.keySet()) {
				if (i == 0)
					param.append("?");
				else
					param.append("&");
				param.append(key).append("=").append(params.get(key));
				i++;
			}
		}
		url += param;

		CloseableHttpClient httpClient = HttpClients.custom().setConnectionManager(connMgr)
				.setDefaultRequestConfig(requestConfig).setDefaultCookieStore(cookieStore).build();//当cookieStore为null时，就相当于没调用setDefaultCookieStore这个方法
	
		String result = null;
		HttpGet httpGet = new HttpGet(url);
		CloseableHttpResponse response = null;
		HttpEntity entity = null;

		try {
			httpGet.setConfig(requestConfig);

			response = httpClient.execute(httpGet);
			int statusCode = response.getStatusLine().getStatusCode();

			if (logger.isDebugEnabled()) {
				logger.debug("statusCode : " + statusCode);
			}

			entity = response.getEntity();
			if (entity != null)
				result = EntityUtils.toString(entity, "UTF-8");
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
		} finally {
			releaseResource(response, entity);
		}
		return result;
	}
	/**
	 * 发送 POST 请求（HTTP），不带输入数据
	 * 
	 * @param url
	 * @return
	 */
	public static String doPost(String url) {
		return doPost(url, null, null);
	}
	
	public static String doPost(String url, CookieStore cookieStore) {
		return doPost(url, null, cookieStore);
	}
	
	
	public static String doPost(String url, Map<String, String> params) {
		return doPost(url, params, null);
	}
	
	/**
	 * 发送 POST 请求（HTTP），K-V形式，相当于不包含上传文件的form的post提交
	 * @param url
	 * @param params
	 * @param cookieStore
	 * @return
	 */
	public static String doPost(String url, Map<String, String> params, CookieStore cookieStore) {
		CloseableHttpClient httpClient = HttpClients.custom().setConnectionManager(connMgr)
				.setDefaultRequestConfig(requestConfig).setDefaultCookieStore(cookieStore).build();
		String result = null;
		HttpPost httpPost = new HttpPost(url);
		CloseableHttpResponse response = null;
		HttpEntity entity = null;
		UrlEncodedFormEntity urlEncodedFormEntity = null;

		try {
			httpPost.setConfig(requestConfig);

			if (params != null) {
				List<NameValuePair> pairList = new ArrayList<NameValuePair>(params.size());
				for (Map.Entry<String, String> entry : params.entrySet()) {
					NameValuePair pair = new BasicNameValuePair(entry.getKey(), entry.getValue().toString());
					pairList.add(pair);
				}
				urlEncodedFormEntity = new UrlEncodedFormEntity(pairList, Charset.forName("UTF-8"));
				httpPost.setEntity(urlEncodedFormEntity);
			}

			response = httpClient.execute(httpPost);

			//printAllResponseHeader(response);
			
			int statusCode = response.getStatusLine().getStatusCode();
			if (logger.isDebugEnabled()) {
				logger.debug("statusCode : " + statusCode);
			}

			entity = response.getEntity();
			if (entity != null)
				result = EntityUtils.toString(entity, "UTF-8");
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
		} finally {
			try {
				EntityUtils.consume(urlEncodedFormEntity);
			} catch (Exception e) {
				logger.error(e.getMessage(),e);
			}
			releaseResource(response, entity);
		}
		return result;
	}

	public static String doPostMultipart(String url, Map<String, Object> params) {
		return doPostMultipart(url, params, null);
	}
	/**
	 * 发送 POST 请求（HTTP），K-V形式，相当于包含上传文件的form的post提交
	 * 
	 * @param url
	 * @param params
	 *            参数map，普通的表单字段用String，文件上传用File，Map中不能出现Sting和File之外的Value
	 * @return
	 */
	public static String doPostMultipart(String url, Map<String, Object> params, CookieStore cookieStore) {
		CloseableHttpClient httpClient = HttpClients.custom().setConnectionManager(connMgr)
				.setDefaultRequestConfig(requestConfig).setDefaultCookieStore(cookieStore).build();
		String result = null;
		HttpPost httpPost = new HttpPost(url);
		CloseableHttpResponse response = null;
		HttpEntity entity = null;
		HttpEntity multipartEntity = null;

		try {
			httpPost.setConfig(requestConfig);

			MultipartEntityBuilder multipartEntityBuilder = MultipartEntityBuilder.create();
			multipartEntityBuilder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
			for (Map.Entry<String, Object> entry : params.entrySet()) {
				String key = entry.getKey();
				Object value = entry.getValue();
				if (value instanceof File) {
					multipartEntityBuilder.addBinaryBody(key, (File) value, ContentType.DEFAULT_BINARY,
							((File) value).getName());
				} else if (value instanceof String) {
					multipartEntityBuilder.addTextBody(key, (String) value,ContentType.MULTIPART_FORM_DATA.withCharset("UTF-8"));
				}
				//这里还可以加一个分支，让他支出Map<String,List<?>>类型数据的发送，
			}
			multipartEntity = multipartEntityBuilder.build();
			httpPost.setEntity(multipartEntity);
			response = httpClient.execute(httpPost);
			
			int statusCode = response.getStatusLine().getStatusCode();
			if (logger.isDebugEnabled()) {
				logger.debug("statusCode : " + statusCode);
			}

			entity = response.getEntity();
			if (entity != null)
				result = EntityUtils.toString(entity, "UTF-8");
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
		} finally {
			try {
				EntityUtils.consume(multipartEntity);
			} catch (Exception e) {
				logger.error(e.getMessage(),e);
			}
			releaseResource(response, entity);
		}
		return result;
	}

	public static String doPostJson(String url, JSONObject jsonObject) {
		return doPostJson(url, jsonObject, null);
	}
	/**
	 * 发送 POST 请求（HTTP），JSON形式
	 * 
	 * @param url
	 * @param json，net.sf.json.JSONObject类型，服务端通过流接收(不推荐使用)
	 * @return
	 */
	public static String doPostJson(String url, JSONObject jsonObject, CookieStore cookieStore) {
		CloseableHttpClient httpClient = HttpClients.custom().setConnectionManager(connMgr)
				.setDefaultRequestConfig(requestConfig).setDefaultCookieStore(cookieStore).build();

		String result = null;
		HttpPost httpPost = new HttpPost(url);
		CloseableHttpResponse response = null;
		HttpEntity entity = null;
		StringEntity stringEntity = null;

		try {
			httpPost.setConfig(requestConfig);

			stringEntity = new StringEntity(jsonObject.toString(), "UTF-8");
			stringEntity.setContentEncoding("UTF-8");
			stringEntity.setContentType("application/json");
			httpPost.setEntity(stringEntity);

			response = httpClient.execute(httpPost);

			int statusCode = response.getStatusLine().getStatusCode();
			if (logger.isDebugEnabled()) {
				logger.debug("statusCode : " + statusCode);
			}

			entity = response.getEntity();
			if (entity != null)
				result = EntityUtils.toString(entity, "UTF-8");

		} catch (Exception e) {
			logger.error(e.getMessage(),e);
		} finally {
			try {
				EntityUtils.consume(stringEntity);
			} catch (Exception e) {
				logger.error(e.getMessage(),e);
			}
			releaseResource(response, entity);
		}
		return result;
	}

	/**
	 * 加载KeyStore
	 * 
	 * @param keyStoreFilePath
	 * @param password
	 * @return
	 * @throws Exception
	 */
	private static KeyStore getKeyStore(String keyStoreFilePath, String password) throws Exception {
		KeyStore trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
		FileInputStream fis = new FileInputStream(new File(keyStoreFilePath));
		try {
			password = password == null ? "" : password;
			trustStore.load(fis, password.toCharArray());
		} catch (NoSuchAlgorithmException e) {
			logger.error(e.getMessage(),e);
		} catch (CertificateException e) {
			logger.error(e.getMessage(),e);
		} catch (IOException e) {
			logger.error(e.getMessage(),e);
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
		} finally {
			try {
				fis.close();
			} catch (IOException e) {
				logger.error(e.getMessage(),e);
			}
		}
		return trustStore;
	}

	/**
	 * 关闭相关资源
	 * 
	 * @param response
	 * @param entity
	 */
	private static void releaseResource(CloseableHttpResponse response, HttpEntity entity) {
		try {
			EntityUtils.consume(entity);
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
		}

		try {
			if (response != null) {
				response.close();
			}
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
		}

		// 不能关闭，因为这是在Util的static代码块中初始化的，关闭了就只能使用一次
		// try {
		// if (httpClient != null) {
		// httpClient.close();
		// }
		// } catch (Exception e) {
		// e.printStackTrace();
		// }
	}

	/**
	 * 将一个实体写入文件存入本地
	 * @param entity
	 * @param name
	 * @throws Exception
	 */
	private static void writeToFile(HttpEntity entity, String filePath) throws Exception {
		OutputStream outstream = new FileOutputStream(filePath);
		entity.writeTo(outstream);
	}

	/**
	 * 打印所有的响应头
	 */
	private static void printAllResponseHeader(HttpResponse response){
		System.out.println("【客户端打】印所有的响应头-------------------------");
		HeaderIterator iterator = response.headerIterator();
		while (iterator.hasNext()) {
			System.out.println(iterator.next());
		}
	}
}