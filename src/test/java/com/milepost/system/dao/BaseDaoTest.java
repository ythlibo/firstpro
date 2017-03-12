package com.milepost.system.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.milepost.demo.dao.EmployeeDao;
import com.milepost.demo.entity.Department;
import com.milepost.demo.entity.Employee;
import com.milepost.system.sqlparser.Page;
import com.milepost.system.util.DateUtil;
import com.milepost.system.util.UUIDGenerator;

/**
 * 测试BaseDao
 * @author HRF
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:/applicationContext.xml", "classpath*:/springmvc-servlet.xml" })
public class BaseDaoTest {

	@Resource(name="employeeDao")
	private EmployeeDao employeeDao;
	
	@Test
	public void testQueryForList3() throws Exception{
		String sql = "SELECT * FROM EMPLOYEE "
				+ "where [id = :id and ]"
				+ "[last_name like '%' || :lastName || '%' and] "
				+ "[email like '%' || :email || '%' and ]"
				+ "age <= :age "
				+ "ORDER BY ID asc";
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("id", "id-AA");
		paramMap.put("lastName", "AA");
		paramMap.put("email", "A");
		paramMap.put("age", 8);
		
		RowMapper<Employee> rowMapper = new RowMapper<Employee>() {
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
		};
		List<Employee> list = employeeDao.queryForList(sql, paramMap, rowMapper);
		System.out.println(list);
	}
	
	@Test
	public void testQueryForList3_1(){
		String sql = "SELECT * FROM EMPloyee ORDER BY ID asc";
		RowMapper<Employee> rowMapper = new RowMapper<Employee>() {
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
		};
		List<Employee> list = employeeDao.queryForList(sql,rowMapper);
		System.out.println(list);
	}
	
	@Test
	public void testQueryForListPagination3(){
		String sql = "SELECT * FROM EMPLOYEE ORDER BY ID asc";
		int pageNo = 1;
		int pageSize = 3;
		RowMapper<Employee> rowMapper = new RowMapper<Employee>() {
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
		};
		
		Page<Employee> page = employeeDao.queryForListPagination(sql, pageNo, pageSize, rowMapper);
		System.out.println("每页"+ pageSize +";"+ "第"+ pageNo +"页");
		System.out.println(page);
		
		pageNo = 2;
		pageSize = 3;
		page = employeeDao.queryForListPagination(sql, pageNo, pageSize, rowMapper);
		System.out.println("每页"+ pageSize +";"+ "第"+ pageNo +"页");
		System.out.println(page);
	}
	
	@Test
	public void testQueryForListPagination3_1() throws Exception{
		String sql = "SELECT * FROM EMPLOYEE "
				+ "where [id = :id and ]"
				+ "[last_name like '%' || :lastName || '%' and] "
				+ "[email like '%' || :email || '%' and ]"
				+ "age <= :age "
				+ "ORDER BY ID asc";
		Map<String, Object> paramMap = new HashMap<String, Object>();
//		paramMap.put("id", "id-AA");
//		paramMap.put("lastName", "AA");
//		paramMap.put("email", "A");
		paramMap.put("age", 8);
		int pageNo = 1;
		int pageSize = 3;
		RowMapper<Employee> rowMapper = new RowMapper<Employee>() {
			@Override
			public Employee mapRow(ResultSet rs, int rowNum) throws SQLException {
				Employee employee = new Employee();
				employee.setId(rs.getString("id"));
				employee.setLastName(rs.getString("last_name"));
				employee.setEmail(rs.getString("email"));
				employee.setBirth(rs.getDate("birth"));
				employee.setCreateTime(rs.getDate("create_time"));
				Department department = new Department(rs.getString("department_id"), null);
				employee.setDepartment(department);
				return employee;
			}
		};
		
		Page<Employee> page = employeeDao.queryForListPagination(sql, paramMap, pageNo, pageSize, rowMapper);
		System.out.println("每页"+ pageSize +";"+ "第"+ pageNo +"页");
		System.out.println(page);
		
//		pageNo = 4;
//		pageSize = 3;
//		Page<Employee> page = employeeDao.queryForListPagination(sql, paramMap, pageNo, pageSize, rowMapper);
//		System.out.println("每页"+ pageSize +";"+ "第"+ pageNo +"页");
//		System.out.println(page);
	}
	
	@Test
	public void testQueryForList2(){
		String sql = "SELECT * FROM EMPloyee ORDER BY ID asc";
		List<Employee> list = employeeDao.queryForList(sql,Employee.class);
		System.out.println(list);
	}
	
	@Test
	public void testQueryForList2_1() throws Exception{
		String sql = "SELECT * FROM EMPLOYEE "
				+ "where [id = :id and ]"
				+ "[last_name like '%' || :lastName || '%' and] "
				+ "[email like '%' || :email || '%' and ]"
				+ "age <= :age "
				+ "ORDER BY ID asc";
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("id", "id-AA");
		paramMap.put("lastName", "AA");
		paramMap.put("email", "A");
		paramMap.put("age", 8);
		List<Employee> list = employeeDao.queryForList(sql, paramMap, Employee.class);
		System.out.println(list);
	}
	
	@Test
	public void testQueryForListPagination2(){
		String sql = "SELECT * FROM EMPLOYEE  ORDER BY ID asc";
		int pageNo = 1;
		int pageSize = 3;
		
		Page<Employee> page = employeeDao.queryForListPagination(sql, pageNo, pageSize, Employee.class);
		System.out.println("每页"+ pageSize +";"+ "第"+ pageNo +"页");
		System.out.println(page);
		
		pageNo = 2;
		pageSize = 3;
		page = employeeDao.queryForListPagination(sql, pageNo, pageSize, Employee.class);
		System.out.println("每页"+ pageSize +";"+ "第"+ pageNo +"页");
		System.out.println(page);
		
		pageNo = 5;
		pageSize = 3;
		page = employeeDao.queryForListPagination(sql, pageNo, pageSize, Employee.class);
		System.out.println("每页"+ pageSize +";"+ "第"+ pageNo +"页");
		System.out.println(page);
		
		pageNo = 6;
		pageSize = 3;
		page = employeeDao.queryForListPagination(sql, pageNo, pageSize, Employee.class);
		System.out.println("每页"+ pageSize +";"+ "第"+ pageNo +"页");
		System.out.println(page);
		
		pageNo = 0;
		pageSize = 3;
		page = employeeDao.queryForListPagination(sql, pageNo, pageSize, Employee.class);
		System.out.println("每页"+ pageSize +";"+ "第"+ pageNo +"页");
		System.out.println(page);
		
		pageNo = 1;
		pageSize = 0;
		page = employeeDao.queryForListPagination(sql, pageNo, pageSize, Employee.class);
		System.out.println("每页"+ pageSize +";"+ "第"+ pageNo +"页");
		System.out.println(page);
		
		pageNo = 1;
		pageSize = 20;
		page = employeeDao.queryForListPagination(sql, pageNo, pageSize, Employee.class);
		System.out.println("每页"+ pageSize +";"+ "第"+ pageNo +"页");
		System.out.println(page);
		
		pageNo = 1;
		pageSize = 14;
		page = employeeDao.queryForListPagination(sql, pageNo, pageSize, Employee.class);
		System.out.println("每页"+ pageSize +";"+ "第"+ pageNo +"页");
		System.out.println(page);
		
		pageNo = 1;
		pageSize = 15;
		page = employeeDao.queryForListPagination(sql, pageNo, pageSize, Employee.class);
		System.out.println("每页"+ pageSize +";"+ "第"+ pageNo +"页");
		System.out.println(page);
	}
	
	@Test
	public void testQueryForListPagination2_1() throws Exception{
		String sql = "SELECT * FROM EMPLOYEE "
				+ "where [id = :id and ]"
				+ "[last_name like '%' || :lastName || '%' and] "
				+ "[email like '%' || :email || '%' and ]"
				+ "age <= :age "
				+ "ORDER BY ID asc";
		int pageNo = 1;
		int pageSize = 3;
		Map<String, Object> paramMap = new HashMap<String, Object>();
//		paramMap.put("id", "id-AA");
//		paramMap.put("lastName", "AA");
//		paramMap.put("email", "A");
		paramMap.put("age", 8);
		Page<Employee> page = employeeDao.queryForListPagination(sql, paramMap, pageNo, pageSize, Employee.class);
		System.out.println("每页"+ pageSize +";"+ "第"+ pageNo +"页");
		System.out.println(page);
	}
	
	@Test
	public void testQueryForList1(){
		String sql = "SELECT * FROM EMPloyee ORDER BY ID asc";
		List<Map<String, Object>> list = employeeDao.queryForList(sql);
		System.out.println(list);
	}
	
	@Test
	public void testQueryForList1_1() throws Exception{
		String sql = "SELECT * FROM EMPloyee where [id = :id ]"
				+ "[and last_name = :lastName ]"
				+ "[and email like '%' || :email || '%'"
				+ "and] [age <= :age ]"
				+ " ORDER BY ID asc";
		Map<String, Object> paramMap = new HashMap<String, Object>();
		//paramMap.put("id", "id-AA");
		//paramMap.put("lastName", "AA");
		//paramMap.put("email", "A");
		paramMap.put("age", 8);
		List<Map<String, Object>> list = employeeDao.queryForList(sql, paramMap);
		System.out.println(list);
	}
	
	@Test
	public void testQueryForListPagination1GroupBy(){
		String sql = "SELECT DEPARTMENT_ID FROM EMPLOYEE GROUP BY DEPARTMENT_ID ORDER BY DEPARTMENT_ID asc";
		int pageNo = 2;
		int pageSize = 3;
		
		Page<Map<String, Object>> page = employeeDao.queryForListPagination(sql, pageNo, pageSize);
		System.out.println("每页"+ pageSize +";"+ "第"+ pageNo +"页");
		System.out.println(page);
	}	
	
	@Test
	public void testQueryForListPagination1(){
		//String sql = "SELECT * FROM TEST_DEPARTMENT ORDER BY ID asc";
		String sql = "SELECT * FROM EMPLOYEE ORDER BY ID asc";
		int pageNo = 1;
		int pageSize = 3;
		
//		Page<Map<String, Object>> page = employeeDao.queryForListPagination(sql, pageNo, pageSize);
//		System.out.println("每页"+ pageSize +";"+ "第"+ pageNo +"页");
//		System.out.println(page);
//		Date birth = (Date)(page.getRows().get(0).get("BIRTH"));
//		System.out.println(birth.getTime());//1480003200000
//		
//		pageNo =2;
//		page = employeeDao.queryForListPagination(sql, pageNo, pageSize);
//		System.out.println("每页"+ pageSize +";"+ "第"+ pageNo +"页");
//		System.out.println(page);
//
//		pageNo =3;
//		page = employeeDao.queryForListPagination(sql, pageNo, pageSize);
//		System.out.println("每页"+ pageSize +";"+ "第"+ pageNo +"页");
//		System.out.println(page);
//
//		pageNo =4;
//		page = employeeDao.queryForListPagination(sql, pageNo, pageSize);
//		System.out.println("每页"+ pageSize +";"+ "第"+ pageNo +"页");
//		System.out.println(page);
//
//		pageNo =5;
//		page = employeeDao.queryForListPagination(sql, pageNo, pageSize);
//		System.out.println("每页"+ pageSize +";"+ "第"+ pageNo +"页");
//		System.out.println(page);
//
//		pageNo =6;
//		page = employeeDao.queryForListPagination(sql, pageNo, pageSize);
//		System.out.println("每页"+ pageSize +";"+ "第"+ pageNo +"页");
//		System.out.println(page);
		
		pageNo = 1;
		pageSize = 20;
		Page<Map<String, Object>> page = employeeDao.queryForListPagination(sql, pageNo, pageSize);
		System.out.println("每页"+ pageSize +";"+ "第"+ pageNo +"页");
		System.out.println(page);
		
		pageNo = 0;
		pageSize = 3;
		page = employeeDao.queryForListPagination(sql, pageNo, pageSize);
		System.out.println("每页"+ pageSize +";"+ "第"+ pageNo +"页");
		System.out.println(page);
		
		pageNo = 1;
		pageSize = 0;
		page = employeeDao.queryForListPagination(sql, pageNo, pageSize);
		System.out.println("每页"+ pageSize +";"+ "第"+ pageNo +"页");
		System.out.println(page);
		
	}
	
	
	@Test
	public void testQueryForListPagination1_1() throws Exception{
		String sql = null;
		Map<String, Object> paramMap = new HashMap<String, Object>();
		int pageNo = 0;
		int pageSize = 0;

		//全部是必须参数，全部有值
//		sql = "SELECT * FROM EMPLOYEE "
//						+ "where id = :id or "
//						+ " last_name like '%' || :lastName || '%' or"
//						+ " email like '%' || :email || '%' or"
//						+ " age <= :age "
//						+ "ORDER BY ID asc";
//		pageNo = 2;
//		pageSize = 3;
//		paramMap.put("id", "id-AA");
//		paramMap.put("lastName", "A");
//		paramMap.put("email", "A");
//		paramMap.put("age", 8);
//
//		Page<Map<String, Object>> page = employeeDao.queryForListPagination(sql, paramMap, pageNo, pageSize);
//		System.out.println("每页"+ pageSize +";"+ "第"+ pageNo +"页");
//		System.out.println(page);
//		Date birth = (Date)(page.getRows().get(0).get("BIRTH"));
//		System.out.println(birth.getTime());
		
		//必须参数，没有值，将抛出异常
//		sql = "SELECT * FROM EMPLOYEE "
//						+ "where id = :id or "
//						+ " last_name like '%' || :lastName || '%' or"
//						+ " email like '%' || :email || '%' or"
//						+ " age <= :age "
//						+ "ORDER BY ID asc";
//		pageNo = 2;
//		pageSize = 3;
//		paramMap.put("id", "id-AA");
//		paramMap.put("lastName", "AA");
//
//		Page<Map<String, Object>> page = employeeDao.queryForListPagination(sql, paramMap, pageNo, pageSize);
//		System.out.println("每页"+ pageSize +";"+ "第"+ pageNo +"页");
//		System.out.println(page);
//		Date birth = (Date)(page.getRows().get(0).get("BIRTH"));
//		System.out.println(birth.getTime());
		
		//必须参数，部分参数没有值
		sql = "SELECT * FROM EMPLOYEE "
						+ "where id = :id or "
						+ " [last_name like '%' || :lastName || '%' and]"
						+ " [email like '%' || :email || '%' or]"
						+ " [age <= :age ]"
						+ "ORDER BY ID asc";
		pageNo = 2;
		pageSize = 3;
		paramMap.put("id", "id-AA");
		paramMap.put("age", "9");

		Page<Map<String, Object>> page = employeeDao.queryForListPagination(sql, paramMap, pageNo, pageSize);
		System.out.println("每页"+ pageSize +";"+ "第"+ pageNo +"页");
		System.out.println(page);
		Date birth = (Date)(page.getRows().get(0).get("BIRTH"));
		System.out.println(birth.getTime());
	}
	
	@Test
	public void testQueryWithRowMapper(){
		String sql = "select * from employee where id = 'id-AA'";
		//自己实现RowMapper，指定如何封装结果集
		RowMapper<Employee> rowMapper = new RowMapper<Employee>() {
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
		};
		Employee employee = employeeDao.queryWithRowMapper(sql, rowMapper);
		System.out.println(employee);
	}
	
	@Test
	public void testQueryWithRowMapper1() throws Exception{
		String sql = "select * from employee where [id = :id or] birth = :birth [or age = :age]";
		//自己实现RowMapper，指定如何封装结果集
		RowMapper<Employee> rowMapper = new RowMapper<Employee>() {
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
		};
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("birth", DateUtil.str2Date("2010-01-01"));
		Employee employee = employeeDao.queryWithRowMapper(sql, paramMap, rowMapper);
		System.out.println(employee);
	}
	
	@Test
	public void testQueryForBean(){
		String sql = "select * from employee where id = 'id-AA'";
		Employee employee = employeeDao.queryForBean(sql, Employee.class);
		System.out.println(employee);
	}
	
	@Test
	public void testQueryForBean1() throws Exception{
		String sql = "select * from employee where [id = :id or] birth = :birth [or age = :age]";
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("birth", DateUtil.str2Date("2010-01-01"));
		Employee employee = employeeDao.queryForBean(sql, paramMap, Employee.class);
		System.out.println(employee);
	}
	
	@Test
	public void testQueryForMap(){
		String sql = "select * from employee where id = 'c'";
		Map<String, Object> map = employeeDao.queryForMap(sql);
		System.out.println(map);
	}
	
	@Test
	public void testQueryForMap1() throws Exception{
		String sql = "select * from employee where [id = :id or] birth = :birth [or age = :age]";
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("birth", DateUtil.str2Date("2010-01-01"));
		Map<String, Object> map = employeeDao.queryForMap(sql, paramMap);
		System.out.println(map);
	}
	
	@Test
	public void testQueryForString(){
		String sql = "select last_name from employee where id = 'id-AA'";
		String lastName = employeeDao.queryForString(sql);
		System.out.println(lastName);
	}
	
	@Test
	public void testQueryForString1() throws Exception{
		String sql = "select last_name,id from employee where [id = :id or] birth = :birth [or age = :age]";
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("birth", DateUtil.str2Date("2010-01-01"));
		String lastName = employeeDao.queryForString(sql, paramMap);
		System.out.println(lastName);
	}
	
	@Test
	public void testQueryForInt(){
		String sql = "select count(*) from employee where 1=1";
		long count = employeeDao.queryForLong(sql);
		System.out.println(count);
	}
	
	@Test
	public void testQueryForInt1() throws Exception{
		String sql = "select count(*) from employee where birth >= :birth [or last_name like '%'+ :lastName +'%']";
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("birth", DateUtil.str2Date("2010-01-01"));
		int count = employeeDao.queryForInt(sql, paramMap);
		System.out.println(count);
	}
	
	@Test
	public void testQueryForLong(){
		String sql = "select count(*) from employee where 1=2";
		long count = employeeDao.queryForLong(sql);
		System.out.println(count);
	}
	
	@Test
	public void testQueryForLong1() throws Exception{
		String sql = "select count(*) from employee where birth >= :birth [or last_name like '%'+ :lastName +'%']";
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("birth", DateUtil.str2Date("2010-01-01"));
		long count = employeeDao.queryForLong(sql, paramMap);
		System.out.println(count);
	}
	
	@Test
	public void testQueryForObject(){
		String sql = "select last_name from employee where id = 'c'";
		String lastName = employeeDao.queryForObject(sql, String.class);
		System.out.println(lastName);
	}
	
	@Test
	public void testQueryForObject1() throws Exception{
		String sql = "select last_name from employee where id = :id [and last_name = :lastName] and birth = :birth";
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("id", "id-AA");
		paramMap.put("birth", DateUtil.str2Date("2010-01-01"));
		String lastName = employeeDao.queryForObject(sql, paramMap, String.class);
		System.out.println(lastName);
	}
	
	
	@Test
	public void testUpdateOrInsertOrDelete(){
		String sql = "delete from employee where id = 'b'";
		int affectedRows = employeeDao.updateOrInsertOrDelete(sql);
		System.out.println(affectedRows);
	}
	
	@Test
	public void testDelete() throws Exception{
		String sql = null;
		Map<String, Object> paramMap = new HashMap<String, Object>();
		int affectedRows = 0;
		
		//全部是必须参数，全部有值
//		sql = "delete from employee where id = :id and last_name = :lastName and create_time >= :createTime";
//		paramMap.put("id", "aa");
//		paramMap.put("lastName", "aa");
//		paramMap.put("createTime", DateUtil.str2Date("2017-01-01 01:01:01"));
//		affectedRows = employeeDao.updateOrInsertOrDelete(sql, paramMap);
//		System.out.println(affectedRows);
		
		//全部有值
//		sql = "delete from employee where [id = :id and] last_name = :lastName [and create_time >= :createTime]";
//		paramMap.put("id", "---529991b52f444371a0112c15c35885cd--1-");
//		paramMap.put("lastName", "--李四--");
//		paramMap.put("createTime", DateUtil.str2Date("2010-10-01 00:00:00"));
//		affectedRows = employeeDao.updateOrInsertOrDelete(sql, paramMap);
//		System.out.println(affectedRows);
		
		//全部没有有值
//		sql = "delete from employee where id = :id [and last_name = :lastName] [and create_time >= :createTime]";
//		paramMap.put("id", "ceed42ffd8d748e98f46fea8329bc4cf");
//		affectedRows = employeeDao.updateOrInsertOrDelete(sql, paramMap);
//		System.out.println(affectedRows);

		//必选参数没有值，将抛出异常
//		sql = "delete from employee where id = :id [and last_name = :lastName] [and create_time >= :createTime]";
//		paramMap.put("lastName", "斯蒂芬");
//		affectedRows = employeeDao.updateOrInsertOrDelete(sql, paramMap);
//		System.out.println(affectedRows);
		
		//delete 语句中包含select语句
		sql = "delete from employee where id = (select id from employee where [create_time = :createTime or] last_name like '%' || :lastName || '%' [or id = :id ][or birth = :birht ])";
		paramMap.put("lastName", "斯蒂芬");
		affectedRows = employeeDao.updateOrInsertOrDelete(sql, paramMap);
		System.out.println(affectedRows);
	}
	
	
	@Test
	public void testUpdate() throws Exception{
		String sql = null;
		Map<String, Object> paramMap = new HashMap<String, Object>();
		int affectedRows = 0;
		
		//全部有值
//		sql = "update EMPLOYEE SET id = '-' || :id || '-', LAST_NAME = '-' || :lastName || '-'[, email = :email][, birth = :birth][, create_time = :createTime][, age = :age] [, remark = :remark] WHERE id = :id";
//		paramMap.clear();
//		paramMap.put("id", "529991b52f444371a0112c15c35885cd");
//		paramMap.put("lastName", "zhang");
//		paramMap.put("email", "zhang@milepost.com");
//		paramMap.put("birth", DateUtil.str2Date("2016-10-10"));
//		paramMap.put("createTime", DateUtil.str2Date("2010-10-10"));
//		paramMap.put("remark", "努力学习");
//		paramMap.put("age", 23);
//		affectedRows = employeeDao.updateOrInsertOrDelete(sql, paramMap);
//		System.out.println(affectedRows);
		
		//全部没有值
//		sql = "update EMPLOYEE SET id = '-' || :id || '-', LAST_NAME = '-' || :lastName || '-'[, email = :email][, birth = :birth][, create_time = :createTime][, age = :age] [, remark = :remark] WHERE id = :id";
//		paramMap.clear();
//		paramMap.put("id", "-529991b52f444371a0112c15c35885cd-");
//		paramMap.put("lastName", "zhang22");
//		affectedRows = employeeDao.updateOrInsertOrDelete(sql, paramMap);
//		System.out.println(affectedRows);
		
		//必须参数没有值，抛出异常
//		sql = "update EMPLOYEE SET id = '-' || :id || '-', LAST_NAME = '-' || :lastName || '-'[, email = :email][, birth = :birth][, create_time = :createTime][, age = :age] [, remark = :remark] WHERE id = :id";
//		paramMap.clear();
//		paramMap.put("id", "-529991b52f444371a0112c15c35885cd-");
//		paramMap.put("email", "zhang@milepost.com");
//		paramMap.put("birth", DateUtil.str2Date("2016-10-10"));
//		paramMap.put("remark", "努力学习");
//		paramMap.put("age", 23);
//		affectedRows = employeeDao.updateOrInsertOrDelete(sql, paramMap);
//		System.out.println(affectedRows);
		
		//一部分有值的
//		sql = "update EMPLOYEE SET id = '-' || :id || '-', LAST_NAME = '-' || :lastName || '-'[, email = :email][, birth = :birth][, create_time = :createTime][, age = :age] [, remark = :remark] WHERE id = :id";
//		paramMap.clear();
//		paramMap.put("id", "--529991b52f444371a0112c15c35885cd--");
//		paramMap.put("lastName", "李四");
//		paramMap.put("email", "zhang@milepost.com");
//		paramMap.put("age", 23);
//		affectedRows = employeeDao.updateOrInsertOrDelete(sql, paramMap);
//		System.out.println(affectedRows);
		
		//update中有select语句
//		sql = "update EMPLOYEE SET id = '-' || :id || '-', LAST_NAME = '-' || :lastName || '-'[, email = :email][, birth = :birth][, create_time = :createTime][, age = :age] [, remark = :remark] WHERE id = (select id from employee where last_name = :lastName [and remark = :remark])";
//		paramMap.clear();
//		paramMap.put("id", "--529991b52f444371a0112c15c35885cd--1");
//		paramMap.put("lastName", "-李四-");
//		paramMap.put("email", "zhang@milepost.com1");
//		paramMap.put("age", 231);
//		affectedRows = employeeDao.updateOrInsertOrDelete(sql, paramMap);
//		System.out.println(affectedRows);
		
		//全部是必须参数
		sql = "update EMPLOYEE SET id = '-' || :id || '-', LAST_NAME = '-' || :lastName || '-' WHERE id = :id";
		paramMap.clear();
		paramMap.put("id", "a593435ba721412b985abb92264ac1bf");
		paramMap.put("lastName", "-李四1-");
		affectedRows = employeeDao.updateOrInsertOrDelete(sql, paramMap);
		System.out.println(affectedRows);
		
	}
	
	@Test
	public void testInsert() throws Exception{
		Map<String, Object> paramMap = new HashMap<String, Object>();
		String sql = null;
		int affectedRows = 0;
		//参数全部存在
//		sql = "insert into employee(id,last_name, email, birth, create_time, age, remark) "
//				+ "values(:id [, :lastName][, :email][, :birth][, :createTime][, :age][, :remark])";
//		paramMap.clear();
//		paramMap.put("id", UUIDGenerator.getUUID());
//		paramMap.put("lastName", "张三");
//		paramMap.put("email", "zhangsan@milepost.com");
//		paramMap.put("birth", DateUtil.str2Date("1990-01-01"));
//		paramMap.put("createTime", DateUtil.str2Date("1990-01-01 01:01:01"));
//		paramMap.put("age", 23);
//		paramMap.put("remark", "努力学习");
//		affectedRows = employeeDao.updateOrInsertOrDelete(sql, paramMap);
//		System.out.println(affectedRows);
//		
		//参数全部不存在,，只插入一列必须参数id
//		sql = "insert into employee(id,last_name, email, birth, create_time, age, remark) "
//				+ "values(:id [, :lastName][, :email][, :birth][, :createTime][, :age][, :remark])";
//		paramMap.clear();
//		paramMap.put("id", UUIDGenerator.getUUID());
//		affectedRows = employeeDao.updateOrInsertOrDelete(sql, paramMap);
//		System.out.println(affectedRows);
		
		//一个必须参数，但是没有参数值，将抛出异常
//		sql = "insert into employee(id,last_name, email, birth, create_time, age, remark) "
//				+ "values(:id , :lastName[, :email], :birth[, :createTime][, :age][, :remark])";
//		paramMap.clear();
//		paramMap.put("id", UUIDGenerator.getUUID());
//		affectedRows = employeeDao.updateOrInsertOrDelete(sql, paramMap);
//		System.out.println(affectedRows);
		
		//insert语句不存在列名部分的，这时sql语句中不能包含可选参数，否则会报错，因为没有列明默认是按照位置插入所有列
		sql = "insert into employee "
				+ "values(:id, [:lastName,] [:email,] :birth, [:createTime,] :departmentId[, :age][, :remark])";
		paramMap.clear();
		paramMap.put("id", UUIDGenerator.getUUID());
		paramMap.put("lastName", "张三");
		paramMap.put("email", "zhangsan@milepost.com");
		paramMap.put("birth", DateUtil.str2Date("1990-01-01"));
		paramMap.put("createTime", DateUtil.str2Date("1990-01-01 01:01:01"));
		paramMap.put("age", 23);
		paramMap.put("remark", "努力学习");
		paramMap.put("departmentId", "01");
		affectedRows = employeeDao.updateOrInsertOrDelete(sql, paramMap);
		System.out.println(affectedRows);
	}
	
	@Test
	public void testBatchUpdateOrInsertOrDelete() {
		String[] sqlArray  = new String[2];
		sqlArray[0] = "insert into employee(id,last_name,email,DEPARTMENT_ID) values('b','b','b','01')";
		sqlArray[1] = "insert into employee(id,last_name,email,DEPARTMENT_ID) values('c','c','c','01')";
		int[] intArray = employeeDao.batchUpdateOrInsertOrDelete(sqlArray);
		for(int i : intArray){
			System.out.println(i);
		}
	}
	
	@Test
	public void testBatchUpdateOrInsertOrDelete1() {
		String sql = "insert into employee(id,last_name,email,DEPARTMENT_ID) values(?,?,?,?)";
		Object[] objects1 = new Object[]{UUIDGenerator.getUUID(),"HH","HH@milepost.com","01"};
		Object[] objects2 = new Object[]{UUIDGenerator.getUUID(),"II@milepost.com","02"};
		Object[] objects3 = new Object[]{UUIDGenerator.getUUID(),"JJ@milepost.com","03"};
		List<Object[]> batchArgs = new ArrayList<Object[]>();
		batchArgs.add(objects1);
		batchArgs.add(objects2);
		batchArgs.add(objects3);
		int[] intArray = employeeDao.batchUpdateOrInsertOrDelete(sql, batchArgs);
		for(int i : intArray){
			System.out.println(i);
		}
	}
	
	@Test
	public void testBatchUpdateOrInsertOrDelete2() {
		String sql = "insert into employee(id,last_name, email, birth, create_time, age, remark, department_id) values(?,?,?,?,?,?,?,?)";
//		Object[] objects1 = new Object[]{"id-AA","AA","AA@milepost.com",DateUtil.str2Date("2010-01-01"),DateUtil.str2Date("2010-01-01 01:01:01"),23,"努力学习","01"};
//		Object[] objects2 = new Object[]{"id-BB","BB","BB@milepost.com",DateUtil.str2Date("2010-02-02"),DateUtil.str2Date("2010-02-02 02:02:02"),23,"努力学习","02"};
//		Object[] objects3 = new Object[]{"id-CC","CC","CC@milepost.com",DateUtil.str2Date("2010-03-03"),DateUtil.str2Date("2010-03-03 03:03:03"),23,"努力学习","04"};
//		Object[] objects4 = new Object[]{"id-DD","DD","DD@milepost.com",DateUtil.str2Date("2010-04-04"),DateUtil.str2Date("2010-04-04 04:04:04"),23,"努力学习","04"};
//		Object[] objects5 = new Object[]{"id-EE","EE","EE@milepost.com",DateUtil.str2Date("2010-05-05"),DateUtil.str2Date("2010-05-05 05:05:05"),23,"努力学习","05"};
		Object[] objects6 = new Object[]{"id-FF","FF","FF@milepost.com",DateUtil.str2Date("2010-06-06"),DateUtil.str2Date("2010-06-06 06:06:06"),23,"努力学习","06"};
		Object[] objects7 = new Object[]{"id-GG","GG","GG@milepost.com",DateUtil.str2Date("2010-06-07"),DateUtil.str2Date("2010-07-07 07:07:07"),23,"努力学习","07"};
		Object[] objects8 = new Object[]{"id-HH","HH","HH@milepost.com",DateUtil.str2Date("2010-06-08"),DateUtil.str2Date("2010-08-08 08:08:08"),23,"努力学习","08"};
		Object[] objects9 = new Object[]{"id-II","II","II@milepost.com",DateUtil.str2Date("2010-06-09"),DateUtil.str2Date("2010-09-09 09:09:09"),23,"努力学习","09"};
		Object[] objects10 = new Object[]{"id-JJ","JJ","JJ@milepost.com",DateUtil.str2Date("2010-06-10"),DateUtil.str2Date("2010-10-10 10:10:10"),23,"努力学习","10"};
		Object[] objects11 = new Object[]{"id-KK","KK","KK@milepost.com",DateUtil.str2Date("2010-06-11"),DateUtil.str2Date("2010-11-11 11:11:11"),23,"努力学习","11"};
		List<Object[]> batchArgs = new ArrayList<Object[]>();
		batchArgs.add(objects6);
		batchArgs.add(objects7);
		batchArgs.add(objects8);
		batchArgs.add(objects9);
		batchArgs.add(objects10);
		batchArgs.add(objects11);
		int[] argTypes = new int[]{Types.VARCHAR,Types.VARCHAR,Types.VARCHAR,Types.DATE,Types.TIMESTAMP,Types.NUMERIC,Types.VARCHAR,Types.VARCHAR};
		int[] intArray = employeeDao.batchUpdateOrInsertOrDelete(sql, batchArgs, argTypes);
		for(int i : intArray){
			System.out.println(i);
		}
	}

}
