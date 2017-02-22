package com.milepost.system.util;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.regex.Pattern;

import javax.xml.parsers.DocumentBuilderFactory;

import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class TestParseXmlUtil {

	/**
	 * @throws UnsupportedEncodingException 
	 */
	@Test
	public void testJwStat1() throws UnsupportedEncodingException{
		String charset = "UTF-8";
		//String methodName = "Get军委热力站一次线";
//		String methodName = "Get军委热力站二次线";
//		String methodName = "Get军委热力站生活水";
//		String methodName = "Get军委热力站采暖";
		String methodName = "Get热力站一次线";//2623(<3min)
//		String methodName = "Get热力站一次线7日累计供热量历史";
//		String methodName = "Get热力站二次线";//4350(<6min)
//		String methodName = "Get热力站生活水";//1606(<2min)
		//String methodName = "Get热力站采暖";//577(<1min)
		//热源有：43(<1min)
		String url = "http://192.168.57.238:8080/XZService1.asmx/" + URLEncoder.encode(methodName, "UTF-8");
		String Stations = "ALL"; 
		//String Stations = "10号名邸"; 
		String queryString = URLEncoder.encode("Stations", "UTF-8") +"="+ Stations;//[Stations]：必须参数
		url = url + "?"+ queryString;
		HttpClientUtil httpClientUtil = new HttpClientUtil();
		String response = httpClientUtil.doGet(url);
		System.out.println(response);
		
		List<Map<String, String>> list = ParseXmlUtil.xmlStr2ListMap(response);
		for(Map<String, String> map : list){
			for(Map.Entry<String, String> entry : map.entrySet()){
				System.out.println(entry.getKey() + ":" + entry.getValue());
			}
			System.out.println("----------------");
		}
	}
	
	/**
	 * Get全网数据
	 * 
	 * @throws UnsupportedEncodingException 
	 */
	@Test
	public void testAllNet() throws UnsupportedEncodingException{
		String charset = "UTF-8";
		String methodName = "Get全网数据";
		String url = "http://192.168.57.238:8080/XZService1.asmx/" + URLEncoder.encode(methodName, "UTF-8");
		HttpClientUtil httpClientUtil = new HttpClientUtil();
		String response = httpClientUtil.doGet(url);
		System.out.println(response);
		
		List<Map<String, String>> list = ParseXmlUtil.xmlStr2ListMap(response);
		for(Map<String, String> map : list){
			for(Map.Entry<String, String> entry : map.entrySet()){
				System.out.println(entry.getKey() + ":" + entry.getValue());
			}
			System.out.println("----------------");
		}
	}
	
	
	/**
	 * Get全热源厂数据
	 * 
	 * 数据示例：
<?xml version="1.0" encoding="utf-8"?>
<ArrayOfHeatSource xmlns:xsd="http://www.w3.org/2001/XMLSchema" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns="http://tempuri.org/">
  <HeatSource>
    <热源名称>东郊供热厂</热源名称>
    <机组名>8-10号</机组名>
    <采集时间>2016-12-27T11:13:00</采集时间>
    <供压>0.43</供压>
    <回压>0.34</回压>
    <供温>81.4</供温>
    <回温>45.4</回温>
    <瞬时热量>0.0</瞬时热量>
    <瞬时补水量>0.0</瞬时补水量>
    <瞬时供水流量>0.0</瞬时供水流量>
    <瞬时回水流量 xsi:nil="true" />
    <累计热量>3228</累计热量>
    <累计供水流量>52760</累计供水流量>
    <累计回水流量 xsi:nil="true" />
    <累计补水量>6562</累计补水量>
  </HeatSource>
  <HeatSource>
    <热源名称>东郊供热厂</热源名称>
    <机组名>1-7号</机组名>
    <采集时间>2016-12-27T11:13:00</采集时间>
    <供压>0.48</供压>
    <回压>0.34</回压>
    <供温>83.9</供温>
    <回温>44.8</回温>
    <瞬时热量>252.2</瞬时热量>
    <瞬时补水量>0.2</瞬时补水量>
    <瞬时供水流量>1536.6</瞬时供水流量>
    <瞬时回水流量 xsi:nil="true" />
    <累计热量>267258</累计热量>
    <累计供水流量>1792149</累计供水流量>
    <累计回水流量 xsi:nil="true" />
    <累计补水量>25446</累计补水量>
  </HeatSource>
  ...
</ArrayOfHeatSource>
	 * 43()
	 * @throws UnsupportedEncodingException 
	 */
	@Test
	public void testHeatSource() throws UnsupportedEncodingException{
		String charset = "UTF-8";
		String methodName = "Get全热源厂数据";
		String url = "http://192.168.57.238:8080/XZService1.asmx/" + URLEncoder.encode(methodName, "UTF-8");
//		String heatSource = "ALL"; 
		String heatSource = "东郊供热厂"; 
		String queryString = URLEncoder.encode("heatSource", "UTF-8") +"="+ heatSource;//[heatSource]：必须参数
		url = url + "?"+ queryString;
		HttpClientUtil httpClientUtil = new HttpClientUtil();
		String response = httpClientUtil.doGet(url);
		System.out.println(response);
		
		List<Map<String, String>> list = ParseXmlUtil.xmlStr2ListMap(response);
		for(Map<String, String> map : list){
			for(Map.Entry<String, String> entry : map.entrySet()){
				System.out.println(entry.getKey() + ":" + entry.getValue());
			}
			System.out.println("----------------");
		}
	}
	
	@Test
	public void test11() throws UnsupportedEncodingException{
		String queryString = "heatSource=东郊供热厂";
		System.out.println(URLEncoder.encode(queryString,"UTF-8"));
	}
	
	/**
	 * Get监测点实时气象
	 * 
	 * 数据示例：
<?xml version="1.0" encoding="utf-8"?>
<监测点实时气象 xmlns:xsd="http://www.w3.org/2001/XMLSchema" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns="http://tempuri.org/">
  <时间>2016-10-31T08:00:00</时间>
  <天安门>0.9</天安门>
  <古观象台>0.2</古观象台>
  <四惠桥>0.5</四惠桥>
  <官园>0.2</官园>
	....
</监测点实时气象>
	 * 
	 * @throws UnsupportedEncodingException 
	 */
	@Test
	public void testMonitoredPoint() throws UnsupportedEncodingException{
		String charset = "UTF-8";
		String methodName = "Get监测点实时气象";
		String url = "http://192.168.57.238:8080/XZService1.asmx/" + URLEncoder.encode(methodName, "UTF-8");
		
		HttpClientUtil httpClientUtil = new HttpClientUtil();
		String response = httpClientUtil.doGet(url);
		System.out.println(response);
		

		Map<String, String> map = ParseXmlUtil.xmlStr2Map(response);
		for(Map.Entry<String, String> entry : map.entrySet()){
			System.out.println(entry.getKey() + ":" + entry.getValue());
		}
	}
	/**
	 * Get当日预报	
	 * 
	 * 数据示例：
<?xml version="1.0" encoding="utf-8"?>
<当日预报 xmlns:xsd="http://www.w3.org/2001/XMLSchema" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns="http://tempuri.org/">
  <日期>2016-12-27T00:00:00</日期>
  <预报内容>今天白天          天气状况：晴间多云          风向风力：北转南风2、3级          最高气温：2℃          相对湿度：30%                        今天夜间          天气状况：晴转多云          风向风力：南转北风1、2级          最低气温：-7℃          相对湿度：85%              明天白天          天气状况：多云转晴          风向风力：偏北风1、2级转3、4级          最高气温：3℃          相对湿度：20%          日平均气温：-2℃              实   况          02点相对湿度：29%          最高气温：朝阳4.5℃ 观象台4.3℃                     日平均气温：朝阳 -0.6℃  观象台 -1.0℃提示：由于受冷空气影响，气温下降明显，今天夜间最低气温-7℃，未来2-3天最低气温-6到-7℃，预计31日气温回升。请注意防范。</预报内容>
</当日预报>
	 * 
	 * @throws UnsupportedEncodingException 
	 */
	@Test
	public void testCurrentDay() throws UnsupportedEncodingException{
		String charset = "UTF-8";
		String methodName = "Get当日预报";
		String url = "http://192.168.57.238:8080/XZService1.asmx/" + URLEncoder.encode(methodName, "UTF-8");
		
		HttpClientUtil httpClientUtil = new HttpClientUtil();
		String response = httpClientUtil.doGet(url);
		System.out.println(response);

		Map<String, String> map = ParseXmlUtil.xmlStr2Map(response);
		for(Map.Entry<String, String> entry : map.entrySet()){
			System.out.println(entry.getKey() + ":" + entry.getValue());
		}
	}
	
	
	
	/**
	 * Get历史预报	
	 * 
	 * 数据示例：
<?xml version="1.0" encoding="utf-8"?>
<历史预报 xmlns:xsd="http://www.w3.org/2001/XMLSchema" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns="http://tempuri.org/">
  <日期>2016-01-01T00:00:00</日期>
  <六时预报>今天白天          天气状况：轻到中度霾，南部重度霾          风向风力：北转南风2级          最高气温：5℃          相对湿度：40%                        今天夜间          天气状况：晴间多云，有轻雾          风向风力：南转北风1、2级          最低气温：-4℃          相对湿度：90%              明天白天          天气状况：轻到中度霾，南部重度霾          风向风力：北转东风2级          最高气温：6℃          相对湿度：40%          日平均气温：0℃              实   况          02点相对湿度：72%          最高气温：朝阳4.0℃ 观象台5.3℃                     日平均气温：朝阳 -1.5℃  观象台 -1.4℃提示：目前，大部分地区能见度在1.5-8公里，南部、东部地区能见度1.5-3公里。</六时预报>
  <十七时预报>今天夜间          天气状况：中度霾，南部重度霾          风向风力：南转北风1、2级          最低气温：-4℃          相对湿度：90%                        明天白天          天气状况：轻到中度霾，南部重度霾          风向风力：北转东风2级          最高气温：7℃          相对湿度：40%              明天夜间          天气状况：雾          风向风力：偏东风1、2级          最低气温：-4℃          相对湿度：95%          日平均气温：1℃              实   况          14点相对湿度：38%          最低气温： 朝阳 -6.9℃ 观象台 -4.9℃提示：预计今天夜间到明天上午能见度在1-2公里左右，请注意防范！</十七时预报>
  <预报平均>0.0</预报平均>
  <朝阳平均>-2.4</朝阳平均>
  <观象台平均>-1.1</观象台平均>
  <预报最高>5.0</预报最高>
  <预报最低>-4.0</预报最低>
  <实际最高>0.6</实际最高>
  <实际最低>-5.4</实际最低>
</历史预报>
	 * 
	 * @throws UnsupportedEncodingException 
	 */
	@Test
	public void testHistory() throws UnsupportedEncodingException{
		String charset = "UTF-8";
		String methodName = "Get历史预报";
		String url = "http://192.168.57.238:8080/XZService1.asmx/" + URLEncoder.encode(methodName, "UTF-8");
		String dateTime = "2016-01-01"; 
		String queryString = URLEncoder.encode("日期", "UTF-8") +"="+dateTime;//[日期]：必须参数
		url = url + "?"+ queryString;
		
		HttpClientUtil httpClientUtil = new HttpClientUtil();
		String response = httpClientUtil.doGet(url);
		System.out.println(response);
		
		Map<String, String> map = ParseXmlUtil.xmlStr2Map(response);
		for(Map.Entry<String, String> entry : map.entrySet()){
			System.out.println(entry.getKey() + ":" + entry.getValue());
		}
	}
	
	
	/**
	 * Get未来七天预报
	 * 
	 * 数据示例：
<?xml version="1.0" encoding="utf-8"?>
<ArrayOf未来七天预报 xmlns:xsd="http://www.w3.org/2001/XMLSchema" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns="http://tempuri.org/">
  <未来七天预报>
    <日期>2016-12-27T00:00:00</日期>
    <天气>晴转多云</天气>
    <风向>北转南</风向>
    <最低温>-7.0</最低温>
    <最高温>2.0</最高温>
    <风力>2、3</风力>
  </未来七天预报>
  <未来七天预报>
    <日期>2016-12-28T00:00:00</日期>
    <天气>多云转晴</天气>
    <风向>偏北</风向>
    <最低温>-7.0</最低温>
    <最高温>3.0</最高温>
    <风力>1、2级转3、4级</风力>
  </未来七天预报>
  ...
</ArrayOf未来七天预报>
	 * 
	 * 
	 * @throws UnsupportedEncodingException 
	 */
	@Test
	public void test7Days() throws UnsupportedEncodingException{
		String charset = "UTF-8";
		String methodName = "Get七天预报";
		String url = "http://192.168.57.238:8080/XZService1.asmx/" + URLEncoder.encode(methodName, "UTF-8");
		HttpClientUtil httpClientUtil = new HttpClientUtil();
		String response = httpClientUtil.doGet(url);
		//System.out.println(response);
		
		List<Map<String, String>> list = ParseXmlUtil.xmlStr2ListMap(response);
		for(Map<String, String> map : list){
			for(Map.Entry<String, String> entry : map.entrySet()){
				System.out.println(entry.getKey() + ":" + entry.getValue());
			}
			System.out.println("----------------");
		}
	}
	//------------------------------------------------------以上是接口的测试
	
	@Test
	public void testAll() throws UnsupportedEncodingException {
		String charset = "UTF-8";
		String methodName = "Get七天预报";
		String url = "http://192.168.57.238:8080/XZService1.asmx/" + URLEncoder.encode(methodName, "UTF-8");//七天预报
		System.out.println("url:" + url);
		HttpClientUtil httpClientUtil = new HttpClientUtil();
		String response = httpClientUtil.doGet(url);
		//String response = readFileContent("C:\\Users\\HRF\\Desktop\\TestPicture\\test2.xml", "UTF-8");
		response = trimXmlStr(response);
		// 字符串转换Document
		Document document = xmlStr2Document(response);

		// 获取Document的根元素
		Element rootEle = getRootEle(document);
		String rootEleName = rootEle.getTagName();
		System.out.print("<" + rootEleName);

		// 获取根元素的属性集合
		NamedNodeMap attrMap = rootEle.getAttributes();
		for (int i = 0; i < attrMap.getLength(); i++) {
			Node attrNode = attrMap.item(i);
			System.out.print(" " + attrNode.getNodeName() + "=\"" + attrNode.getTextContent() + "\"");
		}
		System.out.println(">");

		// 获取根元素的子元素
		NodeList childNodes = rootEle.getChildNodes();
		for (int i = 0; i < childNodes.getLength(); i++) {
			// 打印一个元素的名称、属性、内容、
			printNodeinfo(childNodes.item(i));
		}
		System.out.println("</" + rootEleName + ">");
	}

	
	
	@Test
	public void testTrimXmlStr() {
		String source = readFileContent("C:\\Users\\HRF\\Desktop\\TestPicture\\test1.xml", "UTF-8");
		System.out.println(trimXmlStr(source));
	}

	/**
	 * 将xmlStr中的换行、回车、空白字符去掉
	 * 
	 * @param response
	 * @return
	 */
	private String trimXmlStr(String response) {
		String temp = response;
		temp = Pattern.compile("\\r").matcher(temp).replaceAll("");
		temp = Pattern.compile("\\n").matcher(temp).replaceAll("");
		temp = Pattern.compile("(\\s)+<").matcher(temp).replaceAll("<");// “<”前面的空白字符
		temp = Pattern.compile(">(\\s)+").matcher(temp).replaceAll(">");// “>”后面的空白字符
		return temp;
	}

	/**
	 * 打印一个节点的信息
	 * 
	 * @param item
	 */
	private void printNodeinfo(Node node) {
		short nodeType = node.getNodeType();
		// 如果当前这个节点类型是文本节点，则直接打印文本
		if (nodeType == Node.TEXT_NODE) {
			System.out.println(node.getNodeValue());
			return;
		}
		String nodeName = node.getNodeName();
		System.out.print("<" + nodeName);
		NamedNodeMap attrMap = node.getAttributes();
		if (attrMap != null) {
			for (int i = 0; i < attrMap.getLength(); i++) {
				Node attrNode = attrMap.item(i);
				System.out.print(" " + attrNode.getNodeName() + "=\"" + attrNode.getTextContent() + "\"");
			}
		}
		System.out.println(">");

		NodeList childNodes = node.getChildNodes();
		if (childNodes.getLength() == 0) {
			System.out.println("	" + node.getTextContent());
		}
		for (int i = 0; i < childNodes.getLength(); i++) {
			printNodeinfo(childNodes.item(i));
		}
		System.out.println("</" + nodeName + ">");
	}

	/**
	 * 获取document的根元素
	 * 
	 * @param document
	 * @return
	 */
	private Element getRootEle(Document document) {
		return document.getDocumentElement();// 获取根元素
	}

	/**
	 * 将字符串形式的xml转化成org.w3c.dom.Document对象
	 * 
	 * @param xmlStr
	 * @return 转换不成功则返回null
	 */
	public Document xmlStr2Document(String xmlStr) {
		String encoding = getEncoding(xmlStr);
		encoding = null == encoding ? "utf-8" : encoding;
		Document doc = null;
		try {
			InputStream stream = new ByteArrayInputStream(xmlStr.getBytes(encoding));
			doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(stream);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return doc;
	}

	/**
	 * 获取字符串形式的xml的编码，即 <?xml version="1.0" encoding="utf-8"?>encoding属性值
	 * 
	 * @param xmlStr
	 * @return 没有指定编码则返回null
	 */
	private String getEncoding(String xmlStr) {
		String result = null;
		String xml = xmlStr.trim().toUpperCase();
		if (xml.startsWith("<?XML")) {
			int end = xml.indexOf("?>");
			String sub = xml.substring(0, end);
			StringTokenizer tokens = new StringTokenizer(sub, " =\"\'");// 以空格、=、"、'分割字符串，
			while (tokens.hasMoreTokens()) {
				String token = tokens.nextToken();
				if ("ENCODING".equalsIgnoreCase(token)) {
					if (tokens.hasMoreTokens()) {
						result = tokens.nextToken();
					}
					break;
				}
			}
		}
		return result;
	}

	@Test
	public void testReadFileContent() {
		String response = readFileContent("C:\\Users\\HRF\\Desktop\\TestPicture\\test.xml", "UTF-8");
		System.out.println(response);
	}

	/**
	 * 读取一个文件中的内容到程序
	 * 
	 * @param filePath
	 *            文件绝对路径
	 * @param charset
	 *            文件字符集编码
	 * @return
	 */

	public String readFileContent(String filePath, String charset) {
		StringBuffer result = new StringBuffer();
		FileInputStream fis = null;
		BufferedReader br = null;
		InputStreamReader isr = null;
		try {
			File file = new File(filePath);
			fis = new FileInputStream(file);
			isr = new InputStreamReader(fis);
			br = new BufferedReader(isr);
			String line = br.readLine();// 使用BufferedReader，，InputStreamReader
			while (line != null) {
				result.append(line);
				line = br.readLine();
			}

			// 这样有时候中文会乱码
			// byte[] b = new byte[1024];// 读取到的数据要写入的数组。
			// int len;// 每次读入到byte中的字节的长度
			// String str = "";
			// while ((len = fis.read(b)) != -1) {
			// str = new String(b, 0, len, charset);
			// result.append(str);
			// }
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (fis != null) {
				try {
					fis.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (isr != null) {
				try {
					isr.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return result.toString();
	}
}
