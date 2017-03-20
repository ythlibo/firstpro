package com.milepost.demo.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.milepost.demo.dao.EmployeeDao;
import com.milepost.system.sqlparser.Page;

@Service
public class EmployeeService {

	@Autowired
	private EmployeeDao employeeDao;

	public Page<Map<String, Object>> queryForListPagination(Map<String, Object> paramMap, int pageNo, int pageSize) throws Exception {
		String sql = "SELECT * FROM EMPLOYEE WHERE 1=1 [AND LAST_NAME LIKE :lastName ][AND EMAIL LIKE :email] ORDER BY ID ASC";
		return employeeDao.queryForListPagination(sql, paramMap, pageNo, pageSize);
	}

	public List<Map<String, Object>> queryForList(Map<String, Object> paramMap) throws Exception {
		String sql = "SELECT * FROM EMPLOYEE WHERE 1=1 [AND LAST_NAME LIKE :lastName ][AND EMAIL LIKE :email] ORDER BY ID ASC";
		return employeeDao.queryForList(sql, paramMap);
	}

	/**
	 * 测试事务
	 * @param paramMap1
	 * @param paramMap2
	 * @throws Exception
	 */
	@Transactional
	public void addNoTrans(Map<String, Object> paramMap1, Map<String, Object> paramMap2) throws Exception {
		String sql = "INSERT INTO EMPLOYEE (ID, LAST_NAME, EMAIL, BIRTH, CREATE_TIME, DEPARTMENT_ID, AGE, REMARK) " +
				"VALUES(:id, :lastName, :email, :birth, :createTime, :departmentId[, :age][, :remark])";
		employeeDao.updateOrInsertOrDelete(sql, paramMap1);
		int i = 5 / 0;
		employeeDao.updateOrInsertOrDelete(sql, paramMap2);
	}

	@Transactional
	public int add(Map<String, Object> paramMap) throws Exception {
		String sql = "INSERT INTO EMPLOYEE (ID, LAST_NAME, EMAIL, BIRTH, CREATE_TIME, DEPARTMENT_ID, AGE, REMARK) " +
						"VALUES(:id, :lastName, :email, :birth, :createTime, :departmentId[, :age][, :remark])";
		return employeeDao.updateOrInsertOrDelete(sql, paramMap);
	}
}
