package com.milepost.system.util;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.regex.Pattern;

import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class ParseXmlUtil {

	/**
	 * 将xmlStr转成List<Map<String, String>>
	 * @param xmlStr 
	 * 示例：
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
	 * @return 获取失败则返回一个空的list
	 */
	public static List<Map<String, String>> xmlStr2ListMap(String xmlStr) {
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		try {
			//清空字符串中的格式化字符
			xmlStr = trimXmlStr(xmlStr);
			//字符串转Document对象
			Document document = xmlStr2Document(xmlStr);
			//获取Document的根元素
			Element rootEle = getRootEle(document);
			//System.out.println("rootEle.tagName:" + rootEle.getTagName());
			
			//获取一级子元素
			NodeList oneChildNodeList = rootEle.getChildNodes();

			for(int i=0; i<oneChildNodeList.getLength(); i++){
				//一级子元素节点
				Node oneChildNode = oneChildNodeList.item(i);
				NodeList twoChildNodeList = oneChildNode.getChildNodes();
				Map<String, String> map = new HashMap<String, String>();;
				for(int j=0; j<twoChildNodeList.getLength(); j++){
					Node twoChildNode = twoChildNodeList.item(j);
					String nodeName = twoChildNode.getNodeName();
					Node firstChildNode = twoChildNode.getFirstChild();
					String nodeValue = "";
					if(firstChildNode != null){
						nodeValue = firstChildNode.getNodeValue();
					}
					map.put(nodeName, nodeValue);
				}
				list.add(map);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	
	/**
	 * 将xmlStr转成Map<String, String>
	 * @param xmlStr 
	 * 示例：
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
	 * @return 获取失败则返回一个空的map
	 */
	public static Map<String, String> xmlStr2Map(String xmlStr) {
		Map<String, String> map = new HashMap<String, String>();
		
		try {
			//清空字符串中的格式化字符
			xmlStr = trimXmlStr(xmlStr);
			//字符串转Document对象
			Document document = xmlStr2Document(xmlStr);
			//获取Document的根元素
			Element rootEle = getRootEle(document);
			//System.out.println("rootEle.tagName:" + rootEle.getTagName());
			
			//获取一级子元素
			NodeList oneChildNodeList = rootEle.getChildNodes();
			
			for(int i=0; i<oneChildNodeList.getLength(); i++){
				Node oneChildNode = oneChildNodeList.item(i);
				String nodeName = oneChildNode.getNodeName();
				Node firstChildNode = oneChildNode.getFirstChild();
				String nodeValue = "";
				if(firstChildNode != null){
					nodeValue = firstChildNode.getNodeValue();
				}
				map.put(nodeName, nodeValue);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}
	
	/**
	 * 将xmlStr中的换行、回车、空白字符去掉
	 * 
	 * @param response
	 * @return
	 */
	private static String trimXmlStr(String response) {
		String temp = response;
		temp = Pattern.compile("\\r").matcher(temp).replaceAll("");
		temp = Pattern.compile("\\n").matcher(temp).replaceAll("");
		temp = Pattern.compile("(\\s)+<").matcher(temp).replaceAll("<");// “<”前面的空白字符
		temp = Pattern.compile(">(\\s)+").matcher(temp).replaceAll(">");// “>”后面的空白字符
		return temp;
	}
	
	/**
	 * 将字符串形式的xml转化成org.w3c.dom.Document对象
	 * 
	 * @param xmlStr
	 * @return 转换不成功则返回null
	 */
	private static Document xmlStr2Document(String xmlStr) {
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
	private static String getEncoding(String xmlStr) {
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
	
	/**
	 * 获取document的根元素
	 * 
	 * @param document
	 * @return
	 */
	private static Element getRootEle(Document document) {
		return document.getDocumentElement();// 获取根元素
	}
}
