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
	public void testQueryForList2(){
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
	public void testQueryForListPagination2(){
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
	public void testQueryForList1(){
		String sql = "SELECT * FROM EMPloyee ORDER BY ID asc";
		List<Employee> list = employeeDao.queryForList(sql,Employee.class);
		System.out.println(list);
	}
	
	@Test
	public void testQueryForListPagination1(){
		String sql = "SELECT * FROM EMPLOYEE ORDER BY ID asc";
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
	public void testQueryForList(){
		String sql = "SELECT * FROM EMPloyee ORDER BY ID asc";
		List<Map<String, Object>> list = employeeDao.queryForList(sql);
		System.out.println(list);
	}
	
	
	@Test
	public void testQueryForListPagination(){
		//String sql = "SELECT * FROM TEST_DEPARTMENT ORDER BY ID asc";
		String sql = "SELECT * FROM EMPLOYEE ORDER BY ID asc";
		int pageNo = 1;
		int pageSize = 3;
		
		Page<Map<String, Object>> page = employeeDao.queryForListPagination(sql, pageNo, pageSize);
		System.out.println("每页"+ pageSize +";"+ "第"+ pageNo +"页");
		System.out.println(page);
		Date birth = (Date)(page.getRows().get(0).get("BIRTH"));
		System.out.println(birth.getTime());//1480003200000
		
		pageNo =2;
		page = employeeDao.queryForListPagination(sql, pageNo, pageSize);
		System.out.println("每页"+ pageSize +";"+ "第"+ pageNo +"页");
		System.out.println(page);

		pageNo =3;
		page = employeeDao.queryForListPagination(sql, pageNo, pageSize);
		System.out.println("每页"+ pageSize +";"+ "第"+ pageNo +"页");
		System.out.println(page);

		pageNo =4;
		page = employeeDao.queryForListPagination(sql, pageNo, pageSize);
		System.out.println("每页"+ pageSize +";"+ "第"+ pageNo +"页");
		System.out.println(page);

		pageNo =5;
		page = employeeDao.queryForListPagination(sql, pageNo, pageSize);
		System.out.println("每页"+ pageSize +";"+ "第"+ pageNo +"页");
		System.out.println(page);

		pageNo =6;
		page = employeeDao.queryForListPagination(sql, pageNo, pageSize);
		System.out.println("每页"+ pageSize +";"+ "第"+ pageNo +"页");
		System.out.println(page);
		
		/*pageNo = 1;
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
		System.out.println(page);*/
		
	}
	
	
	@Test
	public void testQueryWithRowMapper(){
		String sql = "select * from employee where id = '8103aa6ae372439c977546487556a270'";
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
	public void testQueryForBean(){
		String sql = "select * from employee where id = '8103aa6ae372439c977546487556a270'";
		Employee employee = employeeDao.queryForBean(sql, Employee.class);
		System.out.println(employee);
	}
	
	@Test
	public void testQueryForMap(){
		String sql = "select * from employee where id = 'c'";
		Map<String, Object> map = employeeDao.queryForMap(sql);
		System.out.println(map);
	}
	
	@Test
	public void testQueryForLong(){
		String sql = "select count(*) from employee where 1=2";
		long count = employeeDao.queryForLong(sql);
		System.out.println(count);
	}
	
	@Test
	public void testQueryForObject(){
		String sql = "select last_name from employee where id = 'c'";
		String lastName = employeeDao.queryForObject(sql, String.class);
		System.out.println(lastName);
	}
	
	@Test
	public void testUpdateOrInsertOrDelete(){
		String sql = "delete from employee where id = 'b'";
		int affectedRows = employeeDao.updateOrInsertOrDelete(sql);
		System.out.println(affectedRows);
	}
	
	@Test
	public void testUpdate() throws Exception{
		String sql = "update EMPLOYEE SET id = '-' || :id || '-', LAST_NAME = '-' || :lastName || '-'[, email = :email][, birth = :birth][, create_time = :createTime][, age = :age] [, remark = :remark] WHERE id = :id and last_name like '%'|| :lastName ||'%'";
		Map<String, Object> paramMap = new HashMap<String, Object>();
//		paramMap.put("id", UUIDGenerator.getUUID());
		paramMap.put("id", "b37992f33e8f49b29aca8aacaa28b600");
		paramMap.put("lastName", "zhang");
		paramMap.put("email", "zhang@milepost.com");
		paramMap.put("birth", DateUtil.str2Date("2016-10-10"));
		paramMap.put("createTime", new Date());
		paramMap.put("age", 23);
		int affectedRows = employeeDao.updateOrInsertOrDelete(sql, paramMap);
		System.out.println(affectedRows);
	}
	
	@Test
	public void testInsert() throws Exception{
		String sql = "insert into employee(id,last_name, email, birth, create_time, age, remark) "
				+ "values('"+ UUIDGenerator.getUUID() +"'[, :lastName][, :email][, :birth][, :createTime][, :age][, :remark])";
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("lastName", "张三1");
		paramMap.put("email", "zhangsan1@milepost.com");
		paramMap.put("createTime", new Date());
		paramMap.put("age", 23);
		int affectedRows = employeeDao.updateOrInsertOrDelete(sql, paramMap);
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
		Object[] objects1 = new Object[]{"id-AA","AA","AA@milepost.com",DateUtil.str2Date("2010-01-01"),DateUtil.str2Date("2010-01-01 01:01:01"),23,"努力学习","01"};
		Object[] objects2 = new Object[]{"id-BB","BB","BB@milepost.com",DateUtil.str2Date("2010-02-02"),DateUtil.str2Date("2010-02-02 02:02:02"),23,"努力学习","02"};
		Object[] objects3 = new Object[]{"id-CC","CC","CC@milepost.com",DateUtil.str2Date("2010-03-03"),DateUtil.str2Date("2010-03-03 03:03:03"),23,"努力学习","04"};
		Object[] objects4 = new Object[]{"id-DD","DD","DD@milepost.com",DateUtil.str2Date("2010-04-04"),DateUtil.str2Date("2010-04-04 04:04:04"),23,"努力学习","04"};
		Object[] objects5 = new Object[]{"id-EE","EE","EE@milepost.com",DateUtil.str2Date("2010-05-05"),DateUtil.str2Date("2010-05-05 05:05:05"),23,"努力学习","05"};
		List<Object[]> batchArgs = new ArrayList<Object[]>();
		batchArgs.add(objects1);
		batchArgs.add(objects2);
		batchArgs.add(objects3);
		batchArgs.add(objects4);
		batchArgs.add(objects5);
		int[] argTypes = new int[]{Types.VARCHAR,Types.VARCHAR,Types.VARCHAR,Types.DATE,Types.TIMESTAMP,Types.NUMERIC,Types.VARCHAR,Types.VARCHAR};
		int[] intArray = employeeDao.batchUpdateOrInsertOrDelete(sql, batchArgs, argTypes);
		for(int i : intArray){
			System.out.println(i);
		}
	}

}
