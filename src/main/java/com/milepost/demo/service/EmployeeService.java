package com.milepost.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.milepost.demo.entity.Employee;
import com.milepost.demo.mapper.EmployeeMapper;

@Service
public class EmployeeService {

	@Autowired
	private EmployeeMapper employeeMapper;

	@Transactional
	public void update(Employee employee) {
		employeeMapper.update(employee);
	}

	@Transactional
	public void delete(String id) {
		employeeMapper.delete(id);
	}

	@Transactional
	public Employee findById(String id) {
		System.out.println("");
		return employeeMapper.findById(id);
	}

	@Transactional
	public List<Employee> findAll() {
		return employeeMapper.findAll();
	}

	/**
	 * 测试事务，有事务注解
	 * 
	 * @param employee1
	 * @param employee2
	 */
	@Transactional
	public void addWithTrans(Employee employee1, Employee employee2) {
		employeeMapper.add(employee1);
		int i = 5 / 0;
		employeeMapper.add(employee2);
	}

	/**
	 * 测试事务，没有事务注解
	 * 
	 * @param employee1
	 * @param employee2
	 */
	public void addNoTrans(Employee employee1, Employee employee2) {
		employeeMapper.add(employee1);
		int i = 5 / 0;
		employeeMapper.add(employee2);
	}

	@Transactional
	public void add(Employee employee) {
		employeeMapper.add(employee);
	}
}
