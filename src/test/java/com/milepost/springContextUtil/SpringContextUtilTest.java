package com.milepost.springContextUtil;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.milepost.demo.service.EmployeeService;
import com.milepost.system.util.SpringContextUtil;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:/applicationContext.xml", "classpath*:/springmvc-servlet.xml" })
public class SpringContextUtilTest {
	
	@Test
	public void test() throws Exception{
		System.out.println(SpringContextUtil.getApplicationContext());
		EmployeeService employeeService = (EmployeeService) SpringContextUtil.getBean("employeeService");
		System.out.println(employeeService.queryForList(null ));
	}
	
}
