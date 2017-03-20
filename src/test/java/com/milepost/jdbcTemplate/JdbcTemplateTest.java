package com.milepost.jdbcTemplate;

import static org.junit.Assert.assertEquals;

import java.sql.DatabaseMetaData;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.ColumnMapRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SqlParameterValue;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.JdbcUtils;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.milepost.demo.entity.Department;
import com.milepost.demo.entity.Employee;
import com.milepost.system.util.DateUtil;
import com.milepost.system.util.UUIDGenerator;

/**
 * 测试spring 的 JdbcTemplate
 * @author HRF
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:/applicationContext.xml", "classpath*:/springmvc-servlet.xml" })
public class JdbcTemplateTest {

	/**
	 * 只允许使用“?”占位符的sql语句
	 */
	private JdbcTemplate jdbcTemplate;
	
	/**
	 * 只允许使用命名参数的sql语句，
	 */
	@Autowired 
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	
	@Before
	public void setUp(){
		//由namedParameterJdbcTemplate获取jdbcTemplate
		jdbcTemplate = (JdbcTemplate)namedParameterJdbcTemplate.getJdbcOperations();
	}
	
	/**
	 * 要实现在sql语句中指定命名参数，需要使用SqlParameterSource的两个实现类
	 * MapSqlParameterSource、BeanPropertySqlParameterSource，也可以简单的使用Map
	 * 
	 * BeanPropertySqlParameterSource
	 */
	@Test
	public void testBeanPropertySqlParameterSource(){
		Employee employee = new Employee();
		employee.setId("bef73490bfc246fab4342601995dab9d");
		java.util.Date date = DateUtil.str2Date(DateUtil.DEFAULT_PARSERS,"2017-02-13");
		employee.setBirth(date);
		BeanPropertySqlParameterSource paramSource = new BeanPropertySqlParameterSource(employee);
		
		List<Map<String, Object>> result = namedParameterJdbcTemplate.queryForList(
				"SELECT ID,LAST_NAME ,BIRTH,CREATE_TIME FROM EMPLOYEE WHERE ID = :id AND birth = :birth", paramSource);
		for(Map<String, Object> map : result){
			System.out.println(map);
		}
	}
	
	/**
	 * MapSqlParameterSource
	 * @throws Exception
	 */
	@Test
	public void testNamedByMapSqlParameterSource() throws Exception {
		MapSqlParameterSource parms = new MapSqlParameterSource();
		parms.addValue("id", "bef73490bfc246fab4342601995dab9d");
		java.util.Date date = DateUtil.str2Date(DateUtil.DEFAULT_PARSERS,"2017-02-13");
		System.out.println(date);
		parms.addValue("birth", date);//可以设置Date对象
		List<Map<String, Object>> result = namedParameterJdbcTemplate.queryForList(
				"SELECT ID,LAST_NAME ,BIRTH,CREATE_TIME FROM EMPLOYEE WHERE ID = :id AND birth = :birth", parms);
		for(Map<String, Object> map : result){
			System.out.println(map);
		}
	}
	
	/**
	 * 可以直接使用Map作为代替SqlParameterSource来指定参数
	 */
	@Test
	public void testNamedByMap(){
		//String sql = "SELECT * FROM EMPLOYEE WHERE ID = :id AND birth = :birth";
		String sql = "SELECT * FROM EMPLOYEE WHERE ID = :id AND birth = :birth";
		/*String sql = "SELECT * FROM EMPLOYEE WHERE ID = :id AND LAST_NAME = :lastName";
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("id", "046aca8a50ac439bb1af93ff8e9f93f5");
		//java.util.Date date = DateUtil.str2Date(DateUtil.DEFAULT_PARSERS,"2017-02-13");
		//paramMap.put("birth", date);
		paramMap.put("lastName", "DD");
		List<Map<String, Object>> result = namedParameterJdbcTemplate.queryForList(sql, paramMap);
		for(Map<String, Object> map : result){
			System.out.println(map);
		}*/
		
		//在select语句中，只要sql中有命名参数了，parameterMap中就必须包含此k-v对，并且v!=null,
		//paramMap.put("birth", null);//抛出“using fallback method instead: java.sql.SQLException: 不支持的特性”异常
		/*String sql1 = "SELECT * FROM EMPLOYEE WHERE BIRTH = :birth";
		Map<String, Object> paramMap1 = new HashMap<String, Object>();
		//java.util.Date date = DateUtil.str2Date(DateUtil.DEFAULT_PARSERS,"2017-02-13");
		//paramMap.put("birth", date);
		paramMap1.put("birth", null);
		List<Map<String, Object>> result1 = namedParameterJdbcTemplate.queryForList(sql1, paramMap1);
		for(Map<String, Object> map : result1){
			System.out.println(map);
		}*/
		
		//paramMap.containsKey("xxx")==false
		Map<String, Object> paramMap2 = new HashMap<String, Object>();
		paramMap2.put("id", "61c48f83951e4e6ba7ace54c0196c8b3");
		java.util.Date date = DateUtil.str2Date(DateUtil.DEFAULT_PARSERS,"2017-02-13 02:23:16");
		//paramMap2.put("birth", date);
		List<Map<String, Object>> result2 = namedParameterJdbcTemplate.queryForList(sql, paramMap2);
		for(Map<String, Object> map : result2){
			System.out.println(map);
		}
	}
	
	/**
	 * 可以使用list来给in关键字赋值
	 * @throws Exception
	 */
	@Test
	public void testQueryForListWithParamMapAndList() throws Exception {
		/*String sql = "SELECT last_name FROM EMPLOYEE where id in (:ids)";
		MapSqlParameterSource parms = new MapSqlParameterSource();
		parms.addValue("ids", Arrays.asList(new Object[] { "bef73490bfc246fab4342601995dab9d", "47fd75df8b4444ac8514614c96b92028"}));
		List<Map<String,Object>> list = namedParameterJdbcTemplate.queryForList(sql, parms);
		System.out.println(list);*/
		String sql = "SELECT last_name FROM EMPLOYEE where id in (:ids)";
		Map<String, Object> parms = new HashMap<String, Object>();
		parms.put("ids", Arrays.asList(new Object[] { "bef73490bfc246fab4342601995dab9d", "47fd75df8b4444ac8514614c96b92028"}));
		List<Map<String,Object>> list = namedParameterJdbcTemplate.queryForList(sql, parms);
		System.out.println(list);
	}
	
	/**
	 * 两列的in关键字
	 */
	@Test
	public void testQueryForListWithParamMapAndListOfExpressionLists() throws Exception {
		MapSqlParameterSource parms = new MapSqlParameterSource();
		List<Object[]> l1 = new ArrayList<Object[]>();
		l1.add(new Object[] { "47fd75df8b4444ac8514614c96b92028", 5 });
		l1.add(new Object[] { "b8c9194434cd4e338ddbb85e2a7e8242", 35 });
		parms.addValue("multiExpressionList", l1);
		List<Map<String,Object>> list = namedParameterJdbcTemplate.queryForList("select * from employee where (id,age) in (:multiExpressionList)",parms);
		System.out.println(list);
	}
	
	
	@Test
	public void testMapDelete(){
		//与select语句相同
		String sql = "delete from employee where id = :id";
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("id", UUIDGenerator.getUUID());
		int affectedRows = namedParameterJdbcTemplate.update(sql, paramMap);
		System.out.println(affectedRows);
	}
	
	/**
	 * update语句与insert语句相同，只要sql语句中有命名参数了，就必须保证
	 * 1.paramMap3.containsKey("xxx") == true;
	 * 当paramMap3.containsKey("xxx") == true && paramMap3.get("xxx") == null时
	 * 能修改成功，xxx列被修改成了null，但是也会报异常，
	 * 因为我们在正常开发过程中，update语句中可能会包含select语句，如：
	 * update b   set   (ClientName)    =   (SELECT name FROM a WHERE b.id = a.id)
	 * 所以，为了处理sql语句的简洁性，我们把update语句也按select同样的处理方式，
	 * 即保证paramMap.get("xxx") != null;
	 */
	@Test
	public void testMapUpdate(){
		//有参数
		/*String sql = "update employee set last_name = :lastName, birth = :birth where id = :id";
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("id", "04dcdea0482a4dd1871bc09d3d5c8ffc");
		paramMap.put("lastName", "zhangsan");
		paramMap.put("birth", new java.util.Date());
		int affectedRows = namedParameterJdbcTemplate.update(sql, paramMap);
		System.out.println(affectedRows);*/
		
		//paramMap2.put("lastName", null);//抛出异常，但是仍能更改成功，参数为null的一列更改成了null
		/*String sql2 = "update employee set last_name = :lastName, birth = :birth where id = :id";
		Map<String, Object> paramMap2 = new HashMap<String, Object>();
		paramMap2.put("id", "04dcdea0482a4dd1871bc09d3d5c8ffc");
		paramMap2.put("lastName", null);
		paramMap2.put("birth", new java.util.Date());
		int affectedRows2 = namedParameterJdbcTemplate.update(sql2, paramMap2);
		System.out.println(affectedRows2);*/
		
		//paramMap2.put("lastName", null);//抛出异常，但是仍能更改成功，参数为null的一列更改成了null
		String sql3 = "update employee set last_name = :lastName, birth = :birth where id = :id";
		Map<String, Object> paramMap3 = new HashMap<String, Object>();
		paramMap3.put("id", "04dcdea0482a4dd1871bc09d3d5c8ffc");
		paramMap3.put("birth", new java.util.Date());
		int affectedRows3 = namedParameterJdbcTemplate.update(sql3, paramMap3);
		System.out.println(affectedRows3);
	}
	
	/**
	 * insert语句，只要sql语句中有命名参数了，就必须保证
	 * 1.paramMap3.containsKey("xxx") == true;
	 * 当paramMap3.containsKey("xxx") == true && paramMap3.get("xxx") == null时
	 * 能插入成功，xxx列被插入了null，但是也会报异常，
	 * 因为我们在正常开发过程中，insert语句中可能会包含select语句，如：
	 * insert into employee (id, last_name, birth)
	 * 	select 'f', last_name, birth from employee where id = '8103aa6ae372439c977546487556a270';
	 * 所以，为了处理sql语句的简洁性，我们把insert语句也按select同样的处理方式，
	 * 即保证paramMap.get("xxx") != null;
	 */
	@Test
	public void testMapInsert(){
		//有参数
		/*String sql = "insert into employee (id, last_name, birth)"
					+ " values(:id, :lastName, :birth)";
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("id", UUIDGenerator.getUUID());
		paramMap.put("lastName", "zhangsan");
		paramMap.put("birth", new java.util.Date());
		int affectedRows = namedParameterJdbcTemplate.update(sql, paramMap);
		System.out.println(affectedRows);*/

		//paramMap2.put("lastName", null);//抛出异常，但是仍能插入成功，参数为null的一列插入了null
		/*String sql2 = "insert into employee (id, last_name, birth)"
					+ " values(:id, :lastName, :birth)";
		Map<String, Object> paramMap2 = new HashMap<String, Object>();
		paramMap2.put("id", UUIDGenerator.getUUID());
		paramMap2.put("lastName", null);
		paramMap2.put("birth", new java.util.Date());
		int affectedRows2 = namedParameterJdbcTemplate.update(sql2, paramMap2);
		System.out.println(affectedRows2);*/
		
		//没有key
		String sql3 = "insert into employee (id, last_name, birth)"
					+ " values(:id, :lastName, :birth)";
		Map<String, Object> paramMap3 = new HashMap<String, Object>();
		paramMap3.put("id", UUIDGenerator.getUUID());
		paramMap3.put("birth", new java.util.Date());
		int affectedRows3 = namedParameterJdbcTemplate.update(sql3, paramMap3);
		System.out.println(affectedRows3);
		
	}
	
	@Test
	public void testMapInsert1(){
		String sql3 = "insert into employee (id, last_name, birth)"
				+ " values(:id, 'a[a' || :lastName || 'b]b', :birth)";
		Map<String, Object> paramMap3 = new HashMap<String, Object>();
		paramMap3.put("id", UUIDGenerator.getUUID());
		paramMap3.put("lastName", "aa");
		paramMap3.put("birth", new java.util.Date());
		int affectedRows3 = namedParameterJdbcTemplate.update(sql3, paramMap3);
		System.out.println(affectedRows3);
	}
	
	/**
	 * 插入字面意义上的单引号，sql中两个单引号=一个单引号，所以，判断一个字符串是否字面意义上的，
	 * 即是否被单引号包含，值需要判断这个字符前面是否有奇数个单引号，是则是被单引号包含，
	 */
	@Test
	public void testEscape(){
		//String sql3 = "insert into employee (id, last_name, birth) values(:id, ':lastName-', :birth)";//ok
		String sql3 = "insert into employee (id, last_name, birth) values(:id, '-''sfasd''fdsa''-', :birth)";//ok
		Map<String, Object> paramMap3 = new HashMap<String, Object>();
		paramMap3.put("id", UUIDGenerator.getUUID());
		paramMap3.put("birth", new java.util.Date());
		paramMap3.put("lastName", "s");
		int affectedRows3 = namedParameterJdbcTemplate.update(sql3, paramMap3);
		System.out.println(affectedRows3);
	}
	
	/**
	 * 确定占位符
	 * 占位符的左边必须是一个非字面意义上的冒号“:”，邮编可能是   “,”  “)”  “T”  “空字符串，即sql语句的结尾”  “”
	 * 因此可以用正则来匹配，然后       
	 */
	@Test
	public void testPlaceholder(){
		//String sql3 = "insert into employee (id, last_name, birth) values(:id, ':lastName-', :birth)";//ok
		String sql3 = "insert into employee (id, last_name, birth) values(:id, '-''sfasd''fdsa''-', :birth)";//ok
		Map<String, Object> paramMap3 = new HashMap<String, Object>();
		paramMap3.put("id", UUIDGenerator.getUUID());
		paramMap3.put("birth", new java.util.Date());
		paramMap3.put("lastName", "s");
		int affectedRows3 = namedParameterJdbcTemplate.update(sql3, paramMap3);
		System.out.println(affectedRows3);
	}
	
	/**
	 * select语句，只要sql语句中有命名参数了，就必须保证
	 * 1.paramMap3.get("xxx") != null;
	 */
	@Test
	public void testMapSelect(){
		//paramMap.put("id", "f18c08cb343e40769c6ebcba899ad37d");
		String sql = "select * from employee where id = :id";
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("id", "f18c08cb343e40769c6ebcba899ad37d");
		List<Map<String, Object>> list = namedParameterJdbcTemplate.queryForList(sql, paramMap);
		System.out.println(list);
		
		//paramMap2.put("birth", null);//抛出“JDBC 3.0 getParameterType call not supported - using fallback method instead: java.sql.SQLException: 不支持的特性”异常
		/*String sql2 = "select * from employee where id = :id or  birth = :birth";
		Map<String, Object> paramMap2 = new HashMap<String, Object>();
		paramMap2.put("id", "f18c08cb343e40769c6ebcba899ad37d");
		paramMap2.put("birth", null);
		List<Map<String, Object>> list2 = namedParameterJdbcTemplate.queryForList(sql2, paramMap2);
		System.out.println(list2);*/
		
		//没有key，“No value supplied for the SQL parameter 'birth': No value registered for key 'birth'”
		/*String sql3 = "select * from employee where id = :id or birth = :birth";
		Map<String, Object> paramMap3 = new HashMap<String, Object>();
		paramMap3.put("id", "f18c08cb343e40769c6ebcba899ad37d");
		List<Map<String, Object>> list3 = namedParameterJdbcTemplate.queryForList(sql3, paramMap3);
		System.out.println(list3);*/
	}
	
	@Test
	public void test11() throws Exception{
		Exception exception = new InvalidDataAccessApiUsageException("No value supplied for the SQL parameter 'birth': No value registered for key 'birth'");
		throw exception;
	}
	
	
	/**
	 * public BeanPropertyRowMapper(Class<T> mappedClass)
	 * 不设置checkFullyPopulated，如果结果集中的字段不能完全支持bean的属性，则会有一部分为null的
	 * @throws Exception
	 */
	@Test
	public void testMappingNullValue() throws Exception {
		BeanPropertyRowMapper<Employee> mapper = new BeanPropertyRowMapper<Employee>(Employee.class);
		List<Employee> result = jdbcTemplate.query(
				"SELECT ID,LAST_NAME ,null as AGE,BIRTH,CREATE_TIME FROM EMPLOYEE", mapper);
		for(Employee employee : result){
			System.out.println(employee + (employee.getBirth().getTime()+""));
		}
	}
	
	
	/**
	 * public BeanPropertyRowMapper(Class<T> mappedClass, boolean checkFullyPopulated)
	 * checkFullyPopulated：是否严格验证所有bean属性已从相应的数据库字段映射，如果否会抛出异常，默认值为false
	 * @throws Exception
	 */
	@Test
	public void testMappingWithNoUnpopulatedFieldsFound() throws Exception {
		List<Employee> result = jdbcTemplate.query(
				"SELECT ID,LAST_NAME,AGE,EMAIL,BIRTH,CREATE_TIME FROM EMPLOYEE",
				new BeanPropertyRowMapper<>(Employee.class, true));//抛出异常
		for(Employee employee : result){
			System.out.println(employee);
		}
	}
	
	/**
	 * public BeanPropertyRowMapper(Class<T> mappedClass)
	 * BeanPropertyRowMapper是RowMapper的一个实现
	 * 将结果集自动的映射成POJO对象，不支持级联对象(内嵌对象)映射，即这里的department查询出是null，
	 * 要支持级联映射，见http://www.iteye.com/problems/73548
	 * @throws Exception
	 */
	@Test
	public void testStaticQueryWithRowMapper() throws Exception {
		List<Employee> result = jdbcTemplate.query(
				"SELECT ID,LAST_NAME,AGE,EMAIL,BIRTH,CREATE_TIME,REMARK,DEPARTMENT_ID as \"DEPARTMENT.ID\"  FROM EMPLOYEE",
				new BeanPropertyRowMapper<>(Employee.class));
		new BeanPropertyRowMapper<>(Employee.class,false);
		for(Employee employee : result){
			System.out.println(employee);
		}
	}
	
	
	/**
	 * 通过RowMapper，自己实现结果集的封装过程，支持queryForObject、query，不支持queryForList
	 * @throws Exception
	 */
	@Test
	public void testQuery1() {
		String sql = "SELECT * FROM EMPLOYEE WHERE ID IN (?,?)";
		List<Employee> list = jdbcTemplate.query(sql, new Object[]{"bef73490bfc246fab4342601995dab9d","5c02a7b51262439695d97599f5ae0bf0"},
				new RowMapper<Employee>() {
			@Override
			public Employee mapRow(ResultSet rs, int rowNum) throws SQLException {
				Employee employee = new Employee();
				employee.setId(rs.getString("id"));
				employee.setLastName(rs.getString("last_name"));
				employee.setAge(rs.getInt("age"));
				employee.setEmail(rs.getString("email"));
				employee.setBirth(rs.getDate("birth"));
				employee.setCreateTime(rs.getDate("create_time"));
				employee.setRemark(rs.getString("remark"));
				Department department = new Department(rs.getString("department_id"), null);
				employee.setDepartment(department  );
				return employee;
			}
		});
		System.out.println(list);
	}
	
	/**
	 * 可以查询出基本数据类型，也可以查询包装类，在我机器上运行没通过，可能是因为版本问题
	 * @throws Exception
	 */
	@Test
	public void testQueryForIntAndIntPrimitive() throws Exception {
		String sql = "SELECT AGE FROM EMPLOYEE WHERE ID = 'bef73490bfc246fab4342601995dab9d'";
		int i = jdbcTemplate.queryForObject(sql, Integer.class).intValue();
		//int j = jdbcTemplate.queryForObject(sql, int.class);//error
		System.out.println(i +"-");
	}
	

	/**
	 * 通过RowMapper，自己实现结果集的封装过程，支持queryForObject、query，不支持queryForList
	 * @throws Exception
	 */
	@Test
	public void testQueryForPojoWithRowMapper() throws Exception {
		String sql = "SELECT ID,LAST_NAME,EMAIL,BIRTH,CREATE_TIME,DEPARTMENT_ID FROM EMPLOYEE WHERE ID IN ('bef73490bfc246fab4342601995dab9d')";
		Employee employee = (Employee) jdbcTemplate.queryForObject(sql, new RowMapper<Employee>() {
			@Override
			public Employee mapRow(ResultSet rs, int rowNum) throws SQLException {
				Employee employee = new Employee();
				employee.setId(rs.getString("id"));
				employee.setLastName(rs.getString("last_name"));
				employee.setEmail(rs.getString("email"));
				employee.setBirth(rs.getDate("birth"));
				employee.setCreateTime(rs.getDate("create_time"));
				Department department = new Department(rs.getString("department_id"), null);
				employee.setDepartment(department  );
				return employee;
			}
		});
		System.out.println(employee);//Employee [id=bef73490bfc246fab4342601995dab9d, lastName=张三-, email=zhangsan-@milepost.com, birth=2017-02-13, createTime=2016-11-11, department=Department [id=01, name=null]]
	}
	
	@Test
	public void testQueryForObjectWithRowMapper() throws Exception {
		String sql = "SELECT BIRTH FROM EMPLOYEE WHERE ID IN ('5c02a7b51262439695d97599f5ae0bf0') ORDER BY LAST_NAME";
		java.util.Date birth = (java.util.Date) jdbcTemplate.queryForObject(sql, new RowMapper<java.util.Date>() {
			@Override
			public java.util.Date mapRow(ResultSet rs, int rowNum) throws SQLException {
				return rs.getDate(1);
			}
		});
		System.out.println(birth);
	}
	
	
	/**
	 * Object object = jdbcTemplate.queryForObject(sql, String.class);
	 * 单值查询，如果结果集是多列、多行，会抛出异常
	 * @throws Exception
	 */
	@Test
	public void testQueryForObjectThrowsIncorrectResultSizeForMoreThanOneRow() throws Exception {
		//String sql = "SELECT LAST_NAME FROM EMPLOYEE WHERE ID IN ('bef73490bfc246fab4342601995dab9d','5c02a7b51262439695d97599f5ae0bf0') ORDER BY LAST_NAME";//error
		//String sql = "SELECT * FROM EMPLOYEE WHERE ID IN ('bef73490bfc246fab4342601995dab9d') ORDER BY LAST_NAME";//error
		String sql = "SELECT LAST_NAME FROM EMPLOYEE WHERE ID IN ('5c02a7b51262439695d97599f5ae0bf0') ORDER BY LAST_NAME";
		Object object = jdbcTemplate.queryForObject(sql, String.class);
		System.out.println(object);
	}
	
	
	@Test
	public void testQueryForMapWithArgsAndSingleRowAndColumn() throws Exception {
		String sql = "SELECT * FROM EMPLOYEE WHERE ID = ?";
		Map<String, Object> map = jdbcTemplate.queryForMap(sql, new Object[] {"5c02a7b51262439695d97599f5ae0bf0"});
		System.out.println(map);
	}
	
	/**
	 * Map<String, Object> map = jdbcTemplate.queryForMap(sql);
	 * 单行查询，如果结果集是多行，会抛出异常
	 * @throws Exception
	 */
	@Test
	public void testQueryForMapWithSingleRowAndColumn() throws Exception {
		//String sql = "SELECT * FROM EMPLOYEE WHERE ID IN ('bef73490bfc246fab4342601995dab9d','5c02a7b51262439695d97599f5ae0bf0') ORDER BY LAST_NAME";//error
		String sql = "SELECT * FROM EMPLOYEE WHERE ID IN ('5c02a7b51262439695d97599f5ae0bf0') ORDER BY LAST_NAME";
		Map<String, Object> map = jdbcTemplate.queryForMap(sql);
		System.out.println(map);
	}
	
	/**
	 * List<Integer> li = jdbcTemplate.queryForList(sql, new Object[] {"bef73490bfc246fab4342601995dab9d"}, Integer.class);
	 * 既有查询条件，又指定结果集中元素的类型
	 * @throws Exception
	 */
	@Test
	public void testQueryForListWithArgsAndIntegerElementAndSingleRowAndColumn() throws Exception {
		String sql = "SELECT AGE FROM EMPLOYEE WHERE ID IN(?)";
		List<Integer> li = jdbcTemplate.queryForList(sql, new Object[] {"bef73490bfc246fab4342601995dab9d"}, Integer.class);
		System.out.println(li.get(0).intValue());
	}
	
	/**
	 * List<Map<String, Object>> li = jdbcTemplate.queryForList(sql, new Object[] {3});
	 * 通过queryForList的第二个参数来指定sql语句的参数，这个参数是Object类型的数据，也可以是Object类型的不定个参数
	 * @throws Exception
	 */
	@Test
	public void testQueryForListWithArgs() throws Exception {
		String sql = "SELECT * FROM EMPLOYEE WHERE ID IN(?,?)";
//		List<Map<String, Object>> li = jdbcTemplate.queryForList(sql, new Object[] {"bef73490bfc246fab4342601995dab9d","5c02a7b51262439695d97599f5ae0bf0"});
		List<Map<String, Object>> li = jdbcTemplate.queryForList(sql, "bef73490bfc246fab4342601995dab9d","5c02a7b51262439695d97599f5ae0bf0");
		System.out.println(li);//[{ID=5c02a7b51262439695d97599f5ae0bf0, LAST_NAME=王五, EMAIL=wangwu@milepost.com, BIRTH=2017-11-11 00:00:00.0, CREATE_TIME=2017-11-11 11:11:11.0, DEPARTMENT_ID=01, AGE=33}, {ID=bef73490bfc246fab4342601995dab9d, LAST_NAME=张三-, EMAIL=zhangsan-@milepost.com, BIRTH=2017-02-13 00:00:00.0, CREATE_TIME=2016-11-11 11:11:11.0, DEPARTMENT_ID=01, AGE=23}]
	}
	
	
	/**
	 * List<Date> li = jdbcTemplate.queryForList(sql, Date.class);
	 * 可以通过queryForList的第二个参数来指定结果集List中的元素类型
	 * @throws Exception
	 */
	@Test
	public void testQueryForListWithIntegerElement() throws Exception {
		String sql = "SELECT BIRTH FROM EMPLOYEE WHERE ID IN ('bef73490bfc246fab4342601995dab9d')";
		List<Date> li = jdbcTemplate.queryForList(sql, Date.class);
		System.out.println(li.get(0).getTime());
	}
	
	
	@Test
	public void testQueryForList() throws Exception {
		String sql = "SELECT * FROM EMPLOYEE WHERE ID IN ('0')";
		List<Map<String, Object>> li = jdbcTemplate.queryForList(sql);
		System.out.println(li == null);//即时没有查询出来0条数据，list也不是null
		System.out.println(li);
	}
	
	/**
	 * 从数据库中获取一条记录, 实际得到对应的一个对象
	 * 注意不是调用 queryForObject(String sql, Class<Employee> requiredType, Object... args) 方法!
	 * 而需要调用 queryForObject(String sql, RowMapper<Employee> rowMapper, Object... args)
	 * 1. 其中的 RowMapper 指定如何去映射结果集的行, 常用的实现类为 BeanPropertyRowMapper
	 * 2. 使用 SQL 中列的别名完成列名和类的属性名的映射. 例如 last_name lastName
	 * 3. 不支持级联属性. JdbcTemplate 到底是一个 JDBC 的小工具, 而不是 ORM 框架
	 */
	@Test
	public void testQueryForObject(){
		String sql = "SELECT ID,LAST_NAME lastName,EMAIL,BIRTH, CREATE_TIME createTime, DEPARTMENT_ID \"department.id\" FROM EMPLOYEE WHERE ID = ?";
		RowMapper<Employee> rowMapper = new BeanPropertyRowMapper<Employee>(Employee.class);
		Employee employee = jdbcTemplate.queryForObject(sql, rowMapper, "bef73490bfc246fab4342601995dab9d");
		System.out.println(employee);//查询出来的employy对象中不包含department对象
	}
	
	/**
	 * RowMapper有以下两个常用的实现
	 * 1、BeanPropertyRowMapper 映射成实体类
	 * 2、ColumnMapRowMapper 映射成列的别名
	 * 
	 */
	@Test
	public void testBeanPropertyRowMapper(){
		String sql = "SELECT ID,LAST_NAME lastName,EMAIL,BIRTH, CREATE_TIME createTime, DEPARTMENT_ID \"department.id\" FROM EMPLOYEE WHERE ID = ?";
		RowMapper<Employee> rowMapper = new BeanPropertyRowMapper<Employee>(Employee.class);
		Employee employee = jdbcTemplate.queryForObject(sql, rowMapper, "bef73490bfc246fab4342601995dab9d");
		System.out.println(employee);//查询出来的employy对象中不包含department对象
	}
	
	@Test
	public void testRowMapperColumnMapRowMapper(){
		String sql = "SELECT ID,LAST_NAME lastName,EMAIL,BIRTH, CREATE_TIME, DEPARTMENT_ID \"department.id\" FROM EMPLOYEE WHERE ID = ?";
		RowMapper rowMapper = new ColumnMapRowMapper();
		Map<String, Object> map = jdbcTemplate.queryForObject(sql, rowMapper, "bef73490bfc246fab4342601995dab9d");
		System.out.println(map);//查询出来的employy对象中不包含department对象
	}
	
	
	/**
	 * 列表查询
	 */
	@Test
	public void testList(){
		String sql = "select * from employee";
		List<Map<String, Object>> list = jdbcTemplate.queryForList(sql);
		for(Map<String, Object> map : list){
			System.out.println(map);
		}
		
		RowMapper<Employee> rowMapper = new BeanPropertyRowMapper<Employee>(Employee.class);
		List<Employee> list1 = jdbcTemplate.query(sql, rowMapper);
		for(Employee employee : list1){
			System.out.println(employee);
		}
		
		List<Employee> list2 = jdbcTemplate.query(sql, new RowMapper<Employee>(){
			@Override
			public Employee mapRow(ResultSet rs, int rowNum) throws SQLException {
				Employee employee = new Employee();
				employee.setId(rs.getString("id"));
				employee.setLastName(rs.getString("last_name"));
				employee.setAge(rs.getInt("age"));
				employee.setEmail(rs.getString("email"));
				employee.setBirth(rs.getDate("birth"));
				employee.setCreateTime(rs.getDate("create_time"));
				employee.setRemark(rs.getString("remark"));
				Department department = new Department(rs.getString("department_id"), null);
				employee.setDepartment(department  );
				return employee;
			}
		});
		for(Employee employee : list2){
			System.out.println(employee);
		}
		/**
		 * String sql = "SELECT * FROM EMPLOYEE WHERE ID IN (?,?)";
		List<Employee> list = jdbcTemplate.query(sql, new Object[]{"bef73490bfc246fab4342601995dab9d","5c02a7b51262439695d97599f5ae0bf0"},
				new RowMapper<Employee>() {
			@Override
			public Employee mapRow(ResultSet rs, int rowNum) throws SQLException {
				Employee employee = new Employee();
				employee.setId(rs.getString("id"));
				employee.setLastName(rs.getString("last_name"));
				employee.setAge(rs.getInt("age"));
				employee.setEmail(rs.getString("email"));
				employee.setBirth(rs.getDate("birth"));
				employee.setCreateTime(rs.getDate("create_time"));
				employee.setRemark(rs.getString("remark"));
				Department department = new Department(rs.getString("department_id"), null);
				employee.setDepartment(department  );
				return employee;
			}
		});
		System.out.println(list);
		 */
		
		
		
		
	}
	
	
	/**
	 * 单行查询
	 */
	@Test
	public void testSingleRow(){
		String sql = "select * from employee where id = 'bef73490bfc246fab4342601995dab9d'";
		//sql = "select * from employee";
		Map<String, Object> map = jdbcTemplate.queryForMap(sql);
		System.out.println(map);
		
		RowMapper<Employee> rowMapper = new BeanPropertyRowMapper<Employee>(Employee.class);
		Employee employee = jdbcTemplate.queryForObject(sql, rowMapper);
		System.out.println(employee);
		
		Employee employee1 = (Employee) jdbcTemplate.queryForObject(sql, new RowMapper<Employee>() {
			@Override
			public Employee mapRow(ResultSet rs, int rowNum) throws SQLException {
				Employee employee = new Employee();
				employee.setId(rs.getString("id"));
				employee.setLastName(rs.getString("last_name"));
				employee.setEmail(rs.getString("email"));
				employee.setBirth(rs.getDate("birth"));
				employee.setCreateTime(rs.getDate("create_time"));
				Department department = new Department(rs.getString("department_id"), null);
				employee.setDepartment(department  );
				return employee;
			}
		});
		System.out.println(employee1);
		
		
	}
	
	/**
	 * 测试单值查询
	 */
	@Test
	public void testSingleValue(){
		String sql = "SELECT COUNT(*) FROM EMPLOYEE";
		Long count = jdbcTemplate.queryForObject(sql, Long.class);
		System.out.println(count);
		
		sql = "SELECT COUNT(*) FROM EMPLOYEE where id = 'bef73490bfc246fab4342601995dab9d'";
		int count1 = jdbcTemplate.queryForObject(sql, Integer.class);
		System.out.println(count1);
		
		sql = "SELECT last_name FROM EMPLOYEE where id = 'bef73490bfc246fab4342601995dab9d'";
		String lastName = jdbcTemplate.queryForObject(sql, String.class);
		System.out.println(lastName);
		
	}
	
	
	
	/**
	 * 执行批量更新: 批量的 INSERT, UPDATE, DELETE
	 * 最后一个参数是 Object[] 的 List 类型: 因为修改一条记录需要一个 Object 的数组, 那么多条不就需要多个 Object 的数组吗
	 */
	@Test
	public void testBatchUpdate(){
		String sql = "INSERT INTO EMPLOYEE(ID,LAST_NAME,EMAIL,BIRTH,CREATE_TIME) "
				+ "	VALUES(?,?,?,TO_DATE(?,'yyyy-mm-dd'),TO_DATE(?,'yyyy-mm-dd hh24:mi:ss'))";
		
		List<Object[]> batchArgs = new ArrayList<Object[]>();
		batchArgs.add(new Object[] {UUIDGenerator.getUUID() ,"AA", "aa@milepost.com", "2016-11-22", "2016-11-22 10:10:10"});
		batchArgs.add(new Object[] {UUIDGenerator.getUUID() ,"BB", "bb@milepost.com", "2016-11-23", "2016-11-23 10:10:10"});
		batchArgs.add(new Object[] {UUIDGenerator.getUUID() ,"CC", "cc@milepost.com", "2016-11-24", "2016-11-24 10:10:10"});
		batchArgs.add(new Object[] {UUIDGenerator.getUUID() ,"DD", "dd@milepost.com", "2016-11-25", "2016-11-25 10:10:10"});
		batchArgs.add(new Object[] {UUIDGenerator.getUUID() ,"EE", "ee@milepost.com", "2016-11-26", "2016-11-26 10:10:10"});
		
		int[] intArray = jdbcTemplate.batchUpdate(sql, batchArgs);
		for(int i : intArray){
			System.out.println(i);
		}
	}
	
	@Test
	public void testBatchUpdate1(){
		String sql = "update EMPLOYEE SET LAST_NAME = ? WHERE id = ?";
		
		List<Object[]> batchArgs = new ArrayList<Object[]>();
		batchArgs.add(new Object[] {"AA", "9be0ef68fddf46efae1ebd72150b98f3"});
		batchArgs.add(new Object[] {"AA", "80c016a564104e2dade67196701f240"});
		
		int[] intArray = jdbcTemplate.batchUpdate(sql, batchArgs);
		for(int i : intArray){
			System.out.println(i);
		}
	}
	
	/**
	 * 执行 INSERT,UPDATE，DELETE
	 */
	@Test
	public void testDelete(){
		String sql = "DELETE FROM EMPLOYEE WHERE ID = ?";
		jdbcTemplate.update(sql, "32c2d241fa13471183408ac88a04720e");
	}
	
	/**
	 * SqlParameterValue(Types.VARCHAR, 2, "zhangsan-@milepost.com")
	 * 					参数类型，			类书位置，	参数值
	 */
	@Test
	public void testSqlParameterValue(){
		String sql = "	UPDATE EMPLOYEE SET "
					+ "	LAST_NAME = ?, " 
					+ "	EMAIL = ?, " 
					+ "	BIRTH = ?, " 
					+ "	CREATE_TIME = TO_DATE(?, 'yyyy-mm-dd hh24:mi:ss') "
					+ "	WHERE ID = ?";
		
		jdbcTemplate.update(sql, "张三-",new SqlParameterValue(Types.VARCHAR, 2, "zhangsan-@milepost.com"),
					new SqlParameterValue(Types.DATE, 3, new Date(new java.util.Date().getTime())),"2016-11-11 11:11:11","bef73490bfc246fab4342601995dab9d");
	}
	
	@Test
	public void testUpdate(){
		String sql = "	UPDATE EMPLOYEE SET "
					+ "	LAST_NAME = ?, " 
					+ "	EMAIL = ?, " 
					+ "	BIRTH = TO_DATE(?, 'yyyy-mm-dd'), " 
					+ "	CREATE_TIME = TO_DATE(?, 'yyyy-mm-dd hh24:mi:ss') "
					+ "	WHERE ID = ?";
		
		jdbcTemplate.update(sql, "张三-","zhangsan-@milepost.com","2016-11-11","2016-11-11 11:11:11","32c2d241fa13471183408ac88a04720e");
	}
	
	@Test
	public void testInsert(){
		String sql = "INSERT INTO EMPLOYEE(ID,LAST_NAME,AGE,EMAIL,BIRTH,REMARK,CREATE_TIME,DEPARTMENT_ID) VALUES(?,?,?,?,TO_DATE(?,'yyyy-mm-dd'),?,TO_DATE(?,'yyyy-mm-dd hh24:mi:ss'),?)";
		jdbcTemplate.update(sql, UUIDGenerator.getUUID(),"小王",23,"xiaowang@milepost.com","2017-11-11","努力学习","2017-11-11 11:11:11","01");
	}
	
	/**
	 * 也可以插入java.util.Date()对象，和Clob
	 */
	@Test
	public void testInsert2(){
		String sql = "INSERT INTO EMPLOYEE(ID,LAST_NAME,AGE,EMAIL,BIRTH,REMARK,CREATE_TIME,DEPARTMENT_ID) VALUES(?,?,?,?,?,?,?,?)";
		jdbcTemplate.update(sql, UUIDGenerator.getUUID(),"小李",23,"xiaoli@milepost.com",new java.util.Date(),"努力学习",new java.util.Date(),"01");
	}
	
	/**
	 * 测试字面意义上的占位符(?)
	 */
	@Test
	public void testInsert3(){
		String sql = "INSERT INTO EMPLOYEE(ID,LAST_NAME,AGE,EMAIL,BIRTH,REMARK,CREATE_TIME,DEPARTMENT_ID) "
				+ "	VALUES(?,'小张-?-',?,?,?,?,?,?)";
		jdbcTemplate.update(sql, UUIDGenerator.getUUID(),23,"xiaoli@milepost.com",new java.util.Date(),"努力学习",new java.util.Date(),"01");
	}
	
	
	/**
	 * JdbcUtils.convertUnderscoreNameToPropertyName
	 * 将下划线的名称转换成属性名称，即下划线转驼峰
	 */
	@Test
	public void convertUnderscoreNameToPropertyName() {
		assertEquals("myName", JdbcUtils.convertUnderscoreNameToPropertyName("MY_NAME"));
		assertEquals("yourName", JdbcUtils.convertUnderscoreNameToPropertyName("yOUR_nAME"));
		assertEquals("AName", JdbcUtils.convertUnderscoreNameToPropertyName("a_name"));
		assertEquals("someoneElsesName", JdbcUtils.convertUnderscoreNameToPropertyName("someone_elses_name"));
	}

	/**
	 * jdbcTemplate获取数据库信息
	 * @throws SQLException
	 */
	@Test
	public void testGetDatabaseInfo() throws SQLException{
		DatabaseMetaData databaseMetaData = jdbcTemplate.getDataSource().getConnection().getMetaData();  
        System.out.println(databaseMetaData.getDatabaseProductName());//Oracle、MySQL
        System.out.println(databaseMetaData.getDatabaseProductVersion());//Oracle Database 11g Release 11.1.0.0.0 - Production、 5.7.3-m13  
	}
	
}
