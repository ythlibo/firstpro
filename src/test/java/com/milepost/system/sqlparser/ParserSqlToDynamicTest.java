package com.milepost.system.sqlparser;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

public class ParserSqlToDynamicTest {

	/**
	 * 测试Map中参数不全
	 * @throws Exception
	 */
	@Test
	public void testParserSqlWithParamMap1() throws Exception{
//		Map<String,String> paramMap = new HashMap<String, String>();
//		paramMap.put("id", "-id-");
//		String sql = "select * from employee where [id = :id and ]last_name = 'aa[:lastName]:bb' and age = :age";
//		System.out.println(ParserSqlToDynamic.parserSql(sql, paramMap));
//		System.out.println(sql);
		
//		Map<String,String> paramMap = new HashMap<String, String>();
//		paramMap.put("lastName", "-lastName-");
//		String sql = "select * from employee where [id = :id and ][last_name = '[' + 'dd' + :lastName +']' and] [age = :age]";
//		System.out.println(ParserSqlToDynamic.parserSql(sql, paramMap));
//		System.out.println(sql);
		
//		Map<String,String> paramMap = new HashMap<String, String>();
//		paramMap.put("id", "-id-");
//		paramMap.put("lastName", "-lastName-");
//		String sql = "[select * from employee where id = :id and] last_name = :lastName [and age = :age]";
//		System.out.println(ParserSqlToDynamic.parserSql(sql, paramMap));
//		System.out.println(sql);
		
//		Map<String,String> paramMap = new HashMap<String, String>();
//		paramMap.put("lastName", "-lastName-");
//		//String sql = "select * from employee where [id = :id and ][last_name = ''[:lastName and ]age = :age";//error，不支持“[]”嵌套
//		String sql = "select * from employee where [id = :id and ][last_name = '[' + :lastName and ]age = :age";
//		System.out.println(ParserSqlToDynamic.parserSql(sql, paramMap));
//		System.out.println(sql);
		
//		Map<String,String> paramMap = new HashMap<String, String>();
//		paramMap.put("lastName", "-lastName-");
//		paramMap.put("age", "-age-");
//		String sql = "select * from employee where [id = :id and ] birth = '['+']' + '' and [last_name = '[' + :lastName and ][age = :age]";
//		System.out.println(ParserSqlToDynamic.parserSql(sql, paramMap));
//		System.out.println(sql);
		
//		Map<String,String> paramMap = new HashMap<String, String>();
//		paramMap.put("id", "-id-");
//		paramMap.put("lastName", "-lastName-");
//		String sql = "[:aaselect * ]from employee where [id = :id and ] birth = '['+']' + '' and [last_name = '[' + :lastName and ]age = :age";
//		System.out.println(ParserSqlToDynamic.parserSql(sql, paramMap));
//		System.out.println(sql);
		
//		Map<String,String> paramMap = new HashMap<String, String>();
//		paramMap.put("lastName", "-lastName-");
//		paramMap.put("id", "-id-");
//		String sql = "delete from employee where [id = :id and ] birth = '['+']' + '' and [last_name = '[' + :lastName and ]age = :age";
//		System.out.println(ParserSqlToDynamic.parserSql(sql, paramMap));
//		System.out.println(sql);
		
//		Map<String,String> paramMap = new HashMap<String, String>();
//		paramMap.put("lastName", "-lastName-");
//		paramMap.put("birth", "-birth-");
//		String sql = "update employee set [last_name = :lastName + 'aa',] birth = :birth where id = :id and last_name = :lastName";
//		System.out.println(ParserSqlToDynamic.parserSql(sql, paramMap));
//		System.out.println(sql);
		
//		Map<String,String> paramMap = new HashMap<String, String>();
//		paramMap.put("lastName", "-lastName-");
//		String sql = "update employee set [last_name = :lastName + 'aa',] birth = :birth where id = :id [and last_name = :lastName]";
//		System.out.println(ParserSqlToDynamic.parserSql(sql, paramMap));
//		System.out.println(sql);
		
//		Map<String,String> paramMap = new HashMap<String, String>();
//		paramMap.put("id", "-id-");
//		String sql = "update employee set [last_name = :lastName + ']aa',] birth = :birth where id = '[' + :id + '[' [and last_name = :lastName]";
//		System.out.println(ParserSqlToDynamic.parserSql(sql, paramMap));
//		System.out.println(sql);
		
//		Map<String,String> paramMap = new HashMap<String, String>();
//		paramMap.put("id", "-id-");
//		String sql = "insert into employee (id, last_name) values(:id, :lastName)";
//		System.out.println(ParserSqlToDynamic.parserSql(sql, paramMap));
//		System.out.println(sql);
		
//		Map<String,String> paramMap = new HashMap<String, String>();
//		paramMap.put("lastName", "-lastName-");
//		paramMap.put("id", "-id-");
//		String sql = "insert into employee (id, last_name) values([:id, ]:lastName)";
//		System.out.println(ParserSqlToDynamic.parserSql(sql, paramMap));
//		System.out.println(sql);
		
//		Map<String,String> paramMap = new HashMap<String, String>();
//		paramMap.put("lastName", "-lastName-");
//		paramMap.put("id", "-id-");
//		String sql = "insert into employee (id, last_name) values(:id[, :lastName])";
//		System.out.println(ParserSqlToDynamic.parserSql(sql, paramMap));
//		System.out.println(sql);
		
//		Map<String,String> paramMap = new HashMap<String, String>();
//		paramMap.put("id", "-id-");
//		paramMap.put("lastName", "-lastName-");
//		String sql = "insert into employee (id, last_name, email) values(:id[, :lastName][, :email])";
//		System.out.println(ParserSqlToDynamic.parserSql(sql, paramMap));
//		System.out.println(sql);
		
//		Map<String,String> paramMap = new HashMap<String, String>();
//		paramMap.put("lastName", "-lastName-");
//		paramMap.put("remark", "-remark-");
//		String sql = "insert into employee (id, last_name, birht, age, remark) values([:id,] :lastName, [:birth, ]:age[, :remark])";
//		System.out.println(ParserSqlToDynamic.parserSql(sql, paramMap));
//		System.out.println(sql);
		
//		Map<String,String> paramMap = new HashMap<String, String>();
//		paramMap.put("id", "-id-");
//		String sql = "insert into employee(id) values([:id])";
//		System.out.println(ParserSqlToDynamic.parserSql(sql, paramMap));
//		System.out.println(sql);
		
//		Map<String,String> paramMap = new HashMap<String, String>();
//		paramMap.put("lastName", "-lastName-");
//		String sql = "insert into employee(id,last_name) values([:id,] [:lastName])";
//		System.out.println(ParserSqlToDynamic.parserSql(sql, paramMap));
//		System.out.println(sql);
		
//		Map<String,String> paramMap = new HashMap<String, String>();
//		paramMap.put("lastName", "-lastName-");
//		String sql = "insert into employee values([:id,] [:lastName])";
//		System.out.println(ParserSqlToDynamic.parserSql(sql, paramMap));
//		System.out.println(sql);
		
//		Map<String,String> paramMap = new HashMap<String, String>();
//		paramMap.put("lastName", "-lastName-");
//		String sql = "insert into employee values([:id,] :lastName)";
//		System.out.println(ParserSqlToDynamic.parserSql(sql, paramMap));
//		System.out.println(sql);
		
//		Map<String,String> paramMap = new HashMap<String, String>();
//		paramMap.put("id", "-id-");
//		paramMap.put("lastName", "-lastName-");
//		paramMap.put("age", "-age-");
//		String sql = "insert into employee (id, last_name, birht, age, remark) values([']'+:id+']',] '['+:lastName+']', [''+:birth,+''] :age[,']'+ :remark])";
//		System.out.println(ParserSqlToDynamic.parserSql(sql, paramMap));
//		System.out.println(sql);
		
//		Map<String,String> paramMap = new HashMap<String, String>();
//		paramMap.put("id", "-id-");
//		paramMap.put("lastName", "-lastName-");
//		String sql = "insert into employee (id, last_name, birht, age, remark) values('['+:id+']', '[afd'+:lastName+'adsf]', [''+':birth'+','+''] :age,']'+ :remark)";
//		System.out.println(ParserSqlToDynamic.parserSql(sql, paramMap));
//		System.out.println(sql);
		
//		Map<String,String> paramMap = new HashMap<String, String>();
//		paramMap.put("email", "-email-");
//		String sql = "insert into employee(id,last_name, email, birth, create_time, age, remark) "
//				+ "values('"+ UUIDGenerator.getUUID() +"'[, :lastName][, :email][, :birth][, :createTime][, :age][, :remark])";
//		System.out.println(ParserSqlToDynamic.parserSql(sql, paramMap));
//		System.out.println(sql);
		
		Map<String,String> paramMap = new HashMap<String, String>();
		paramMap.put("id", "-id-");
		paramMap.put("lastName", "-lastName-");
		String sql = "update EMPLOYEE SET id = '-' || :id || '-', LAST_NAME = '-' || :lastName || '-'[, email = :email][, birth = :birth][, create_time = :createTime][, age = :age] [, remark = :remark] WHERE id = :id";
		System.out.println(ParserSqlToDynamic.parserSql(sql, paramMap));
		System.out.println(sql);
		
		
	}
	@Test
	public void testParserSqlWithParamMap() throws Exception{
		Map<String,String> paramMap = new HashMap<String, String>();
		paramMap.put("id", "-id-");
		paramMap.put("lastName", "-lastName-");
		paramMap.put("email", "-email-");
		paramMap.put("birth", "-birth-");
		paramMap.put("age", "-age-");
		paramMap.put("remark", "-remark-");
		paramMap.put("createTime", "-createTime-");
		paramMap.put("departmentId", "-departmentId-");
		
//		String sql = "select * from employee where [id = :id and ]last_name = 'aa[:lastName]:bb' and age = :age";
//		System.out.println(ParserSqlToDynamic.parserSql(sql, paramMap));
//		System.out.println(sql);
		
//		String sql = "select * from employee where [id = :id and ][last_name = '[' + 'dd' + :lastName +']' and] age = :age";
//		System.out.println(ParserSqlToDynamic.parserSql(sql, paramMap));
//		System.out.println(sql);
		
//		String sql = "[select * from employee where id = :id and] last_name = :lastName [and age = :age]";
//		System.out.println(ParserSqlToDynamic.parserSql(sql, paramMap));
//		System.out.println(sql);
		
		//String sql = "select * from employee where [id = :id and ][last_name = ''[:lastName and ]age = :age";//error，不支持“[]”嵌套
//		String sql = "select * from employee where [id = :id and ][last_name = '[' + :lastName and ]age = :age";
//		System.out.println(ParserSqlToDynamic.parserSql(sql, paramMap));
//		System.out.println(sql);
		
//		String sql = "select * from employee where [id = :id and ] birth = '['+']' + '' and [last_name = '[' + :lastName and ]age = :age";
//		System.out.println(ParserSqlToDynamic.parserSql(sql, paramMap));
//		System.out.println(sql);
		
//		String sql = "[:aaselect * ]from employee where [id = :id and ] birth = '['+']' + '' and [last_name = '[' + :lastName and ]age = :age";
//		System.out.println(ParserSqlToDynamic.parserSql(sql, paramMap));
//		System.out.println(sql);
		
//		String sql = "delete from employee where [id = :id and ] birth = '['+']' + '' and [last_name = '[' + :lastName and ]age = :age";
//		System.out.println(ParserSqlToDynamic.parserSql(sql, paramMap));
//		System.out.println(sql);
		
//		String sql = "update employee set [last_name = :lastName + 'aa',] birth = :birth where id = :id [and last_name = :lastName]";
//		System.out.println(ParserSqlToDynamic.parserSql(sql, paramMap));
//		System.out.println(sql);
		
//		String sql = "update employee set [last_name = :lastName + 'aa',] birth = :birth where id = :id [and last_name = :lastName]";
//		System.out.println(ParserSqlToDynamic.parserSql(sql, paramMap));
//		System.out.println(sql);
		
//		String sql = "update employee set [last_name = :lastName + ']aa',] birth = :birth where id = '[' + :id + '[' [and last_name = :lastName]";
//		System.out.println(ParserSqlToDynamic.parserSql(sql, paramMap));
//		System.out.println(sql);
		
//		String sql = "insert into employee (id, last_name) values(:id, :lastName)";
//		System.out.println(ParserSqlToDynamic.parserSql(sql, paramMap));
//		System.out.println(sql);
		
//		String sql = "insert into employee (id, last_name) values([:id, ]:lastName)";
//		System.out.println(ParserSqlToDynamic.parserSql(sql, paramMap));
//		System.out.println(sql);
		
//		String sql = "insert into employee (id, last_name) values(:id[, :lastName])";
//		System.out.println(ParserSqlToDynamic.parserSql(sql, paramMap));
//		System.out.println(sql);
		
//		String sql = "insert into employee (id, last_name, email) values(:id[, :lastName], email)";
//		System.out.println(ParserSqlToDynamic.parserSql(sql, paramMap));
//		System.out.println(sql);
		
//		String sql = "insert into employee (id, last_name, birht, age, remark) values([:id,] :lastName, :birth, :age[, :remark])";
//		System.out.println(ParserSqlToDynamic.parserSql(sql, paramMap));
//		System.out.println(sql);
		
//		String sql = "insert into employee(id) values([:id])";
//		System.out.println(ParserSqlToDynamic.parserSql(sql, paramMap));
//		System.out.println(sql);
		
//		String sql = "insert into employee(id,last_name) values([:id,] [:lastName])";
//		System.out.println(ParserSqlToDynamic.parserSql(sql, paramMap));
//		System.out.println(sql);
		
//		String sql = "insert into employee values([:id,] [:lastName])";
//		System.out.println(ParserSqlToDynamic.parserSql(sql, paramMap));
//		System.out.println(sql);
		
//		String sql = "insert into employee values([:id,] :lastName)";
//		System.out.println(ParserSqlToDynamic.parserSql(sql, paramMap));
//		System.out.println(sql);
		
//		String sql = "insert into employee (id, last_name, birht, age, remark) values([']'+:id+']',] '['+:lastName+']', [''+:birth,+''] :age[,']'+ :remark])";
//		System.out.println(ParserSqlToDynamic.parserSql(sql, paramMap));
//		System.out.println(sql);
		
//		String sql = "insert into employee (id, last_name, birht, age, remark) values('['+:id+']', '[afd'+:lastName+'adsf]', [''+':birth'+','+''] :age,']'+ :remark)";
//		System.out.println(ParserSqlToDynamic.parserSql(sql, paramMap));
//		System.out.println(sql);
		
//		String sql = "insert into employee(id,last_name, email, birth, create_time, age, remark) "
//				+ "values('"+ UUIDGenerator.getUUID() +"'[, :lastName][, :email][, :birth][, :createTime][, :age][, :remark])";
//		System.out.println(ParserSqlToDynamic.parserSql(sql, paramMap));
//		System.out.println(sql);
		
	}
	
	/**
	 * 测试Map中参数全
	 * @throws Exception
	 */
	@Test
	public void testParserSqlNoParamMap() throws Exception{
//		String sql = "select * from employee where [id = :id and ]last_name = 'aa[:lastName]:bb' and age = :age";
//		System.out.println(ParserSqlToDynamic.parserSql(sql, null));
//		System.out.println(sql);
		
//		String sql = "select * from employee where [id = :id and ][last_name = '[' + 'dd' + :lastName +']' and] age = :age";
//		System.out.println(ParserSqlToDynamic.parserSql(sql, null));
//		System.out.println(sql);
		
//		String sql = "[select * from employee where id = :id and] last_name = :lastName [and age = :age]";
//		System.out.println(ParserSqlToDynamic.parserSql(sql, null));
//		System.out.println(sql);

		//String sql = "select * from employee where [id = :id and ][last_name = ''[:lastName and ]age = :age";//error，不支持“[]”嵌套
//		String sql = "select * from employee where [id = :id and ][last_name = '[' + :lastName and ]age = :age";
//		System.out.println(ParserSqlToDynamic.parserSql(sql, null));
//		System.out.println(sql);
		
//		String sql = "select * from employee where [id = :id and ] birth = '['+']' + '' and [last_name = '[' + :lastName and ]age = :age";
//		System.out.println(ParserSqlToDynamic.parserSql(sql, null));
//		System.out.println(sql);
		
//		String sql = "[:aaselect * ]from employee where [id = :id and ] birth = '['+']' + '' and [last_name = '[' + :lastName and ]age = :age";
//		System.out.println(ParserSqlToDynamic.parserSql(sql, null));
//		System.out.println(sql);
		
//		String sql = "delete from employee where [id = :id and ] birth = '['+']' + '' and [last_name = '[' + :lastName and ]age = :age";
//		System.out.println(ParserSqlToDynamic.parserSql(sql, null));
//		System.out.println(sql);
		
//		String sql = "update employee set [last_name = :lastName + 'aa',] birth = :birth where id = :id [and last_name = :lastName]";
//		System.out.println(ParserSqlToDynamic.parserSql(sql, null));
//		System.out.println(sql);

//		String sql = "update employee set [last_name = :lastName + 'aa',] birth = :birth where id = :id [and last_name = :lastName]";
//		System.out.println(ParserSqlToDynamic.parserSql(sql, null));
//		System.out.println(sql);
		
//		String sql = "update employee set [last_name = :lastName + ']aa',] birth = :birth where id = '[' + :id + '[' [and last_name = :lastName]";
//		System.out.println(ParserSqlToDynamic.parserSql(sql, null));
//		System.out.println(sql);
		
//		String sql = "insert into employee (id, last_name) values(:id, :lastName)";
//		System.out.println(ParserSqlToDynamic.parserSql(sql, null));
//		System.out.println(sql);
		
//		String sql = "insert into employee (id, last_name) values([:id, ]:lastName)";
//		System.out.println(ParserSqlToDynamic.parserSql(sql, null));
//		System.out.println(sql);
		
//		String sql = "insert into employee (id, last_name) values(:id[, :lastName])";
//		System.out.println(ParserSqlToDynamic.parserSql(sql, null));
//		System.out.println(sql);

//		String sql = "insert into employee (id, last_name, email) values(:id[, :lastName], email)";
//		System.out.println(ParserSqlToDynamic.parserSql(sql, null));
//		System.out.println(sql);
		
//		String sql = "insert into employee (id, last_name, birht, age, remark) values([:id,] :lastName, :birth, :age[, :remark])";
//		System.out.println(ParserSqlToDynamic.parserSql(sql, null));
//		System.out.println(sql);
		
//		String sql = "insert into employee(id) values([:id])";
//		System.out.println(ParserSqlToDynamic.parserSql(sql, null));
//		System.out.println(sql);
		
//		String sql = "insert into employee(id,last_name) values([:id,] [:lastName])";
//		System.out.println(ParserSqlToDynamic.parserSql(sql, null));
//		System.out.println(sql);
		
//		String sql = "insert into employee values([:id,] [:lastName])";
//		System.out.println(ParserSqlToDynamic.parserSql(sql, null));
//		System.out.println(sql);
		
//		String sql = "insert into employee values([:id,] :lastName)";
//		System.out.println(ParserSqlToDynamic.parserSql(sql, null));
//		System.out.println(sql);
		
//		String sql = "insert into employee (id, last_name, birht, age, remark) values([']'+:id+']',] '['+:lastName+']', [''+:birth,+''] :age[,']'+ :remark])";
//		System.out.println(ParserSqlToDynamic.parserSql(sql, null));
//		System.out.println(sql);
		
//		String sql = "insert into employee (id, last_name, birht, age, remark) values('['+:id+']', '[afd'+:lastName+'adsf]', [''+':birth'+','+''] :age,']'+ :remark)";
//		System.out.println(ParserSqlToDynamic.parserSql(sql, null));
//		System.out.println(sql);
		
//		String sql = "insert into employee(id,last_name, email, birth, create_time, age, remark) "
//				+ "values('"+ UUIDGenerator.getUUID() +"'[, :lastName][, :email][, :birth][, :createTime][, :age][, :remark])";
//		System.out.println(ParserSqlToDynamic.parserSql(sql, null));
//		System.out.println(sql);
		
	}
	
	/**
	 * 测试没有参数
	 */
	@Test
	public void testCountMatches(){
		String sql3 = "insert into employee (id, last_name, birth)"
				+ " values(:id, 'a[a' || :lastName || 'b]b', :birth)";
	}
	
	@Test
	public void testParserSelect2Count() {
		String sql = "SELECT asfdsadf,fds*dsf 'select aa from bb' from employee where id = (select aa from bb)";
		System.out.println(ParserSqlToDynamic.parserSelect2Count(sql));
		sql = "  SELECT asfdsadf,fds*dsf from   employee";
		System.out.println(ParserSqlToDynamic.parserSelect2Count(sql));
		sql = "  select * FrOM employee where id in (select id from employee where name = '张三')";
		System.out.println(ParserSqlToDynamic.parserSelect2Count(sql));
		sql = "  select * FrOM employee where id in ("
				+ "\n\rselect id from employee where name = '张三')";
		System.out.println(ParserSqlToDynamic.parserSelect2Count(sql));
	}
}
