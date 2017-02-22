package com.milepost.system.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.junit.Test;

public class TestJsonUtil {

	@Test
	public void testJsonArray2List(){
		String json = readFileContent("C:/Users/HRF/Desktop/TestPicture/jsonArray.txt");
		System.out.println("源json字符串：");
		System.out.println(json);
		System.out.println("---------------------");
		List<Object> list = JsonUtil.jsonArray2List(json);
		System.out.println("转换之后的map.toString()：");
		System.out.println(list.toString());
		System.out.println("---------------------");
		
		System.out.println("list[0]:" + list.get(0));
		
		System.out.println("list[2].age:" + ((Map<String,Object>)list.get(2)).get("age"));
		
		List<Object> hobbyList = (List<Object>)((Map<String, Object>)list.get(3)).get("hobby");
		System.out.println("list[3].hobby[1]:" + hobbyList.get(1));
		
		List<Object> abList = (List<Object>) ((Map<String,Object>)((List<Object>)list.get(4)).get(2)).get("ab");
		System.out.println("list[4][2].ab[1]:" + abList.get(1));
		/**
		 	list[0]:小明
			list[2].age:23
			list[3].hobby[1]:write
			list[4][2].ab[1]:bb 
		 */
	}
	
	@Test
	public void testJsonObject2Map(){
		String json = readFileContent("C:/Users/HRF/Desktop/TestPicture/jsonObject.txt");
		System.out.println("源json字符串：");
		System.out.println(json);
		System.out.println("---------------------");
		Map<String, Object> map = JsonUtil.jsonObject2Map(json);
		System.out.println("转换之后的map.toString()：");
		System.out.println(map.toString());
		System.out.println("---------------------");
		System.out.println("username:" + map.get("username"));
		
		Map<String, Object> personalMap = ((Map<String, Object>)map.get("personal"));
		System.out.println("personal.height:" + personalMap.get("height"));
		
		List<Object> hobbyList = (List<Object>)personalMap.get("hobby");
		System.out.println("personal.hobby[1]:" + hobbyList.get(1));
		
		List<Object> bookList = (List<Object>)map.get("book");
		List<Object> nameList = (List<Object>) ((Map<String,Object>)bookList.get(0)).get("name");
		System.out.println("book[0].name[1]:" + nameList.get(1));
		
		Map<String,Object> nameMap = (Map<String,Object>)bookList.get(2);
		System.out.println("book[2].price:" + nameMap.get("price"));
		/**
			username:张三
			personal.height:185
			personal.hobby[1]:write
			book[0].name[1]:C语言"基础
			book[2].price:33.3
		 */
	}
	
	@Test
	public void testReadFileContent(){
		System.out.println(readFileContent("C:/Users/HRF/Desktop/TestPicture/jsondata.txt"));
	}

	/**
	 * 读取一个文件中的内容到程序
	 * @param filePath 文件绝对路径
	 * @return
	 */
	public String readFileContent(String filePath) {
		StringBuffer result = new StringBuffer();
		FileInputStream fis = null;
		try {
			File file = new File(filePath);
			fis = new FileInputStream(file);
			byte[] b = new byte[1024];// 读取到的数据要写入的数组。
			int len;// 每次读入到byte中的字节的长度
			String str = "";
			while ((len = fis.read(b)) != -1) {
				str = new String(b, 0, len, "GBK");//这里默认使用UTF-8编码，可以指定编码
				result.append(str);
			}
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
		}
		return result.toString();
	}
}
