package com.milepost.demo.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.milepost.system.sqlparser.Page;
import com.milepost.system.util.DateUtil;
import com.milepost.system.util.UUIDGenerator;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:/applicationContext.xml", "classpath*:/springmvc-servlet.xml" })
public class EmployeeServiceTest {
	
	@Autowired
	private EmployeeService employeeService;
	
	@Test
	public void testQueryForListPagination() throws Exception {
		Map<String, Object> paramMap = new HashMap<String,Object>();
		paramMap.put("id", UUIDGenerator.getUUID());
		//paramMap.put("lastName", "%MM%");
		//paramMap.put("email", "%MM%");
		Page<Map<String, Object>> page = employeeService.queryForListPagination(paramMap, 1, 3);
		List<Map<String, Object>> employeeList = page.getResultList();
		for(Map<String,Object> employeeMap : employeeList){
			System.out.println(employeeMap);
		}
		System.out.println("page.getPageNo()=" + page.getPageNo());
		System.out.println("page.getPageSize()=" + page.getPageSize());
		System.out.println("page.getStartIndex()=" + page.getStartIndex());
		System.out.println("page.getLastIndex()=" + page.getLastIndex());
		System.out.println("page.getTotalPages()=" + page.getTotalPages());
		System.out.println("page.getTotalRows()=" + page.getTotalRows());
	}
	
	@Test
	public void testQueryForList() throws Exception {
		Map<String, Object> paramMap = new HashMap<String,Object>();
		paramMap.put("id", UUIDGenerator.getUUID());
		//paramMap.put("lastName", "%MM%");
		//paramMap.put("email", "%MM%");
		List<Map<String,Object>> employeeList = employeeService.queryForList(paramMap);
		for(Map<String,Object> employeeMap : employeeList){
			System.out.println(employeeMap);
		}
	}
	
	@Test
	public void testAddTrans() throws Exception {
		Map<String, Object> paramMap1 = new HashMap<String,Object>();
		paramMap1.put("id", UUIDGenerator.getUUID());
		paramMap1.put("lastName", "NN");
		paramMap1.put("email", "NN@milepost.com");
		paramMap1.put("birth", DateUtil.str2Date("1990-01-01"));
		paramMap1.put("createTime", new Date());
		paramMap1.put("departmentId", "01");
		paramMap1.put("remark", "努力学习！");
		
		Map<String, Object> paramMap2 = new HashMap<String,Object>();
		paramMap2.put("id", UUIDGenerator.getUUID());
		paramMap2.put("lastName", "OO");
		paramMap2.put("email", "OO@milepost.com");
		paramMap2.put("birth", DateUtil.str2Date("1990-01-01"));
		paramMap2.put("createTime", new Date());
		paramMap2.put("departmentId", "01");
		paramMap2.put("remark", "努力学习！");
		employeeService.addNoTrans(paramMap1, paramMap2);
	}
	
	
	@Test
	public void testAdd() throws Exception {
		Map<String, Object> paramMap = new HashMap<String,Object>();
		paramMap.put("id", UUIDGenerator.getUUID());
		paramMap.put("lastName", "MM");
		paramMap.put("email", "MM@milepost.com");
		paramMap.put("birth", DateUtil.str2Date("1990-01-01"));
		paramMap.put("createTime", new Date());
		paramMap.put("departmentId", "01");
		paramMap.put("remark", "努力学习！");
		int affectedRows = employeeService.add(paramMap);
		System.out.println("affectedRows:" + affectedRows);
	}
}
