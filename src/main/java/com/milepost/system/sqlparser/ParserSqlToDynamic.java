package com.milepost.system.sqlparser;

import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 根据参数，把sql语句解析成动态的
 * @author HRF
 */
public class ParserSqlToDynamic {
	
	private static Logger logger = LoggerFactory.getLogger(ParserSqlToDynamic.class);
	
	/**
	 * 把一个查询语句转换成count(*)，用于查询数据总数
	 * @param sql
	 * @return
	 */
	public static String parserSelect2Count(String sql){
		if(sql == null)return sql;
		sql = sql.trim();
		//这样就不支持group by了
		//sql = Pattern.compile("(?i)^select.*?from").matcher(sql).replaceAll("select count(*) totalRows from");
		sql = "select count(*) totalRows from ("+ sql +") tmp"; 
		return sql;
	}
	
	/**
	 * 计算sub在str中出现的次数，
	 * A null or empty ("") String input into str will returns 0.
	 * StringUtils.countMatches(null, *)       = 0
	 *  StringUtils.countMatches("", *)         = 0
	 *  StringUtils.countMatches("abba", null)  = 0
	 *  StringUtils.countMatches("abba", "a")   = 2
	 * 
	 * @param str
	 * @param sub
	 * @return
	 */
	/*public static int countMatches(String str, String sub){
	    return StringUtils.countMatches(str, sub);
	}*/
	
	/**
	 * 解析sql，根据paramMap，移除可选参数
	 * @param sql
	 * @param paramMap
	 * @return
	 * @throws Exception 
	 */
	public static String parserSql(String sql, Map<String,?> paramMap) throws Exception{
		boolean isDebug = logger.isDebugEnabled();
		if (isDebug) {
			logger.debug("ParserSql parameter " + paramMap + ".");
			logger.debug("Before parserSql sql ["+ sql +"].");
		}
		if(sql == null){
			return null;
		}
		char[] sqlCharArray = sql.toCharArray();
		//获取参数集合
		ParsedSql parsedSql = NamedParameterUtils.parseSqlStatement(sql);
		List<String> paramList = parsedSql.getParameterNames();
		//遍历参数集合，单独处理每个参数
		for(int i=0; i<paramList.size(); i++){
			//参数名
			String paramName = paramList.get(i);
			//获取参数的起始索引和结尾索引
			int startIndex = parsedSql.getParameterIndexes(i)[0];
			int endIndex = parsedSql.getParameterIndexes(i)[1];
			//获取参数的类型，可选参数为true，必选参数为false
			boolean paramType = getParamType(sqlCharArray,startIndex,endIndex);
			if(paramType){
				if (isDebug) {
					logger.debug(paramName + " is optional.");
				}
				//如果是可选参数，则判断paramMap中是否存在此参数，判断规则为paramMap.get(paramName) != null
				if(paramMap!=null && paramMap.get(paramName) != null){
					//如果存在参数值，则将sql语句的字符数组中的“[”和“]”赋值为空格字符
					//参数左侧的“[”
					spaceLeft(startIndex,"[",sqlCharArray);
					//参数右侧的“]”
					spaceRight(endIndex,"]",sqlCharArray);
				}else{
					//如果不存在参数值，则将“[”、“]”和被“[]”包括的所有字符替换成空格，
					//参数左侧的“[”和之前的字符
					spaceLeft(startIndex,"[*",sqlCharArray);
					//参数右侧的“]”、参数占位符本身和之前的字符，这里j=startIndex，而不是j=endIndex
					spaceRight(startIndex,"*]",sqlCharArray);
					
					//如果是insert语句，需要把值对应的列名也去掉，
					//insert into employee (id, last_name, birth) values(:id, :lastName, :birth)
					Pattern pattern = Pattern.compile("(?i)^insert.*\\(.*\\).*values.*\\(.*"); 
			        Matcher matcher = pattern.matcher(sql); 
			        //以“insert”开始 && 含有列名部分，
					if(sql.toLowerCase().trim().startsWith("insert") && matcher.matches()){
						spaceInsertColumn(startIndex, sql, sqlCharArray);
					}
				}
			}else{
				if (isDebug) {
					logger.debug(paramName + " is required.");
				}
				//如果是必选参数，则可以判断paramMap.get(paramName) != null，然后抛出相应的异常，也可以什么都不做，然后让springjdbc自己处理是否抛出异常
				if(paramMap==null || paramMap.get(paramName) == null){
					throw new Exception("In parameter map" + paramMap + ", there is not a value for parameter '" + paramName + "'.");
				}
			}
		}
		String newSql = String.valueOf(sqlCharArray);
		if (isDebug) {
			logger.debug("After parserSql sql ["+ newSql +"].");
		}
		return newSql;
	}

	/**
	 * 将insert语句中指定参数对应的列名部分变成空格" " 
	 * @param startIndex
	 * @param sql
	 */
	private static void spaceInsertColumn(int startIndex, String sql, char[] sqlCharArray) {
		//注意，这里是用sql语句重新获取的字符数组，因为在方法操作过程中，需要用到insert语句的值部分，所以不能使用被处理过的sqlCharArray
		char[] oldSqlCharArray = sql.toCharArray();
		int quotationCount = 0;
		//insert语句值的索引，从1开始
		int valueIndex = 1;
		//insert语句值的左侧括号的索引
		int valueLeftBracketIndex = 0;
		for(int j=(startIndex-1); j>=0; j--){
			char c = oldSqlCharArray[j];
			if(c == '\''){
				quotationCount ++;
				continue;
			}else if(c==',' && quotationCount%2==0){//非字面意义上的“,”，即没被单引号包含的“,”
				valueIndex ++;
				continue;
			}else if(c == '(' && quotationCount%2==0){//非字面意义上的“(”，即没被单引号包含的“,”
				valueLeftBracketIndex = j;
				break;
			}
		}

		//insert语句列的索引，从1开始
		int columnIndex = 0;
		quotationCount = 0;
		//根据valueIndex去掉insert语句中对应的列名
		for(int j=0; j<valueLeftBracketIndex; j++){
			char c = oldSqlCharArray[j];
			if(c == '\''){
				quotationCount ++;
			}else if(c=='(' && quotationCount%2==0){
				columnIndex ++;
			}else if(c==',' && quotationCount%2==0){
				columnIndex ++;
			}
			//insert into employee(id,last_name,email) values('"+ UUIDGenerator.getUUID() +"','last_name-1','email-1')";
			if(columnIndex == valueIndex){
				for(int k=(j+1); k<valueLeftBracketIndex; k++){
					char cc = oldSqlCharArray[k];
					if(cc == ')'){
						//从k向左找“,”，如果找到则替换成空格
						for(int l=(k-1); l>=0; l--){
							//注意，这里使用的是替换好的sqlCharArray，不是oldSqlCharArray
							char ccc = sqlCharArray[l];
							if(ccc == '('){
								break;
							}else if(ccc == ','){
								sqlCharArray[l] = ' ';
								break;
							}
						}
						break;
					}else if(cc == ','){
						sqlCharArray[k] = ' ';
						break;
					}else{
						sqlCharArray[k] = ' ';
					}
				}
				break;
			}
		}
	}

	/**
	 * 将指定索引右侧的指定字符替换成空格" "
	 * @param index
	 * @param targetStr 
	 * 	有两种取值，“]”表示单一的“]”字符，“*]”表示“]”字符和他左侧的任意字符。
	 * @param sqlCharArray
	 */
	private static void spaceRight(int index, String targetStr, char[] sqlCharArray) {
		int quotationCount = 0;
		//这里要先if后for，不能把if放在for里面，那样会造成每次循环都要进行判断
		if("*]".equals(targetStr)){
			for(int j=index; j<sqlCharArray.length; j++){
				char c = sqlCharArray[j];
				sqlCharArray[j] = ' ';
				if(c == '\''){
					quotationCount ++;
					continue; 
				}else if(c==']' && quotationCount%2==0){
					break;
				}
			}
		}else{
			for(int j=index; j<sqlCharArray.length; j++){
				char c = sqlCharArray[j];
				if(c == '\''){
					quotationCount ++;
					continue;
				}else if(c==']' && quotationCount%2==0){
					sqlCharArray[j] = ' ';
					break;
				}
			}
		}
	}

	/**
	 * 将指定索引左侧的指定字符替换成空格" "
	 * @param index
	 * @param targetStr 
	 * 	有两种取值，“[”表示单一的“[”字符，“[*”表示“[”字符和他右侧的任意字符。
	 * @param sqlCharArray
	 */
	private static void spaceLeft(int index, String targetStr, char[] sqlCharArray) {
		int quotationCount = 0;
		//这里要先if后for，不能把if放在for里面，那样会造成每次循环都要进行判断
		if("[*".equals(targetStr)){
			for(int j=(index-1); j>=0; j--){
				char c = sqlCharArray[j];
				sqlCharArray[j] = ' ';
				if(c == '\''){
					quotationCount ++;
					continue;
				}else if(c=='[' && quotationCount%2==0){
					break;
				}
			}
		}else{
			for(int j=(index-1); j>=0; j--){
				char c = sqlCharArray[j];
				if(c == '\''){
					quotationCount ++;
					continue;
				}else if(c=='[' && quotationCount%2==0){
					sqlCharArray[j] = ' ';
					break;
				}
			}
		}
	}

	/**
	 * 获取sql语句中，指定参数的类型，可选参返回true，必选参数返回false
	 * @param sqlCharArray
	 * @param startIndex
	 * @param endIndex
	 * @return
	 */
	private static boolean getParamType(char[] sqlCharArray, int startIndex, int endIndex) {
		int quotationCount = 0;//单引号的个数
		boolean result = false;//默认为必选参数
		for(int i=(startIndex-1); i>=0; i--){
			char c = sqlCharArray[i];
			if(c == '\''){
				quotationCount ++;
				continue;
			}else if(c==']' && quotationCount%2==0){//非字面意义上的“]”，即没被单引号包含的“]”，表明这个参数没有被“[]”包含，即是必选参数，直接返回false
				result = false;
				break;
			}else if(c=='[' && quotationCount%2 == 0){//非字面意义上的“[”，即没被单引号包含的“[”，表明这个参数被“[]”包含，即是可选参数，直接返回true
				result = true;
				break;
			}
		}
		return result;
	}
  
/*	public static void main(String[] args) {
		String sql = "select * from employee where [id = :id and] age = :age and birth = '['+ :birth +']' [and last_name = :lastName]";
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("lastName", 11);
		parserSql(sql,paramMap);
		char[] sqlCharArray = sql.toCharArray();
		for(char c : sqlCharArray){
			System.out.print(c + "-");
		}
		System.out.println("");
		System.out.println(String.valueOf(sqlCharArray));
		
		//空白字符
		char[] sqlCharArray = new char[] {'a','b',(char) 32,'d','e'};
		System.out.println(String.valueOf(sqlCharArray));
		
		//验证标识符必须由字母、数字、下划线组成 
//        Pattern pattern = Pattern.compile("[a-z0-9A-Z]+"); 
//        Matcher matcher = pattern.matcher("_"); 
//        boolean flg = matcher.matches(); 
//        System.out.println(flg); 
		
		//insert语句中是否包含列名部分
		String sql = "INSERT into employee (id, last_name, birth) values(:id, :lastName, :birth)";
		Pattern pattern = Pattern.compile("(?i)^insert.*\\(.*\\).*values.*\\(.*"); 
        Matcher matcher = pattern.matcher(sql); 
        System.out.println(matcher.matches());
	}*/
	
	
}
