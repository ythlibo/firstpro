package com.milepost.demo;

import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.milepost.demo.entity.Employee;
import com.milepost.demo.service.EmployeeService;
import com.milepost.system.util.UUIDGenerator;

/**
 * 测试MyBatis
 * @author HRF
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:/applicationContext.xml", "classpath*:/springmvc-servlet.xml" })
public class EmployeeTest {

	@Autowired
	private EmployeeService employeeService;
	
	private final String id = "d871e1854b5a41d1bac3c832fb857e83";
	
	@Test
	public void testDelete(){
		employeeService.delete(id);
	}
	
	@Test
	public void testSelectAll(){
		List<Employee> employeeList = employeeService.findAll();
		System.out.println(employeeList);
	}

	@Test
	public void testSelect(){
		Employee employee = employeeService.findById(id);
		System.out.println(employee);
	}
	
	@Test
	public void testUpdate(){
		Employee employee = employeeService.findById(id);
		employee.setLastName("张三1");
		employeeService.update(employee);
	}
	
	@Test
	public void testTrans1(){
		Employee employee = new Employee();
		employee.setId(UUIDGenerator.getUUID());
		employee.setLastName("张三");
		employee.setEmail("zhangsan@milepost.com");
		employee.setBirth(new Date());
		employee.setCreateTime(new Date());
		
		Employee employee2 = new Employee();
		employee2.setId(UUIDGenerator.getUUID());
		employee2.setLastName("张三");
		employee2.setEmail("zhangsan@milepost.com");
		employee2.setBirth(new Date());
		employee2.setCreateTime(new Date());
		
		employeeService.addWithTrans(employee,employee2);
	}
	
	@Test
	public void testTrans2(){
		Employee employee = new Employee();
		employee.setId(UUIDGenerator.getUUID());
		employee.setLastName("张三");
		employee.setEmail("zhangsan@milepost.com");
		employee.setBirth(new Date());
		employee.setCreateTime(new Date());
		
		Employee employee2 = new Employee();
		employee2.setId(UUIDGenerator.getUUID());
		employee2.setLastName("张三");
		employee2.setEmail("zhangsan@milepost.com");
		employee2.setBirth(new Date());
		employee2.setCreateTime(new Date());
		
		employeeService.addNoTrans(employee,employee2);
	}
	
	@Test
	public void testAdd() {
		Employee employee = new Employee();
		employee.setId(id);
		employee.setLastName("李四");
		employee.setEmail("lisi@milepost.com");
		employee.setBirth(new Date());
		employee.setCreateTime(new Date());
		employeeService.add(employee);
	}
}
