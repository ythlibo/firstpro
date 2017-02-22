package com.milepost.demo.mapper;

import java.util.List;

import com.milepost.demo.entity.Employee;

public interface EmployeeMapper {
	void add(Employee employee);
	void update(Employee employee);
	void delete(String id);
	Employee findById(String id);
	List<Employee> findAll();
}
