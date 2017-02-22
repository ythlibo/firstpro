package com.milepost.system.sqlparser;

import org.junit.Test;

import com.milepost.system.sqlparser.MysqlSQLPageHandleImpl;
import com.milepost.system.sqlparser.OracleSQLPageHandleImpl;
import com.milepost.system.sqlparser.SQLPageHandle;

public class SQLPageHandleTest {

	@Test
	public void testOracleSQLPageHandleImpl() {
		SQLPageHandle sqlPageHandle = new OracleSQLPageHandleImpl();
		String oldSql = "SELECT * FROM T_PA_ORG ORDER BY ID ASC";
		int pageNo = 1;
		int pageSize = 3;
		String newSql = sqlPageHandle.handlerPagingSQL(oldSql, pageNo, pageSize);
		System.out.println(newSql);
		/*
		pageNo = 2;
		pageSize = 3;
		newSql = sqlPageHandle.handlerPagingSQL(oldSql, pageNo, pageSize);
		System.out.println(newSql);
		
		pageNo = 1000;
		pageSize = 3;
		newSql = sqlPageHandle.handlerPagingSQL(oldSql, pageNo, pageSize);
		System.out.println(newSql);
		
		pageNo = 1;
		pageSize = 10000;
		newSql = sqlPageHandle.handlerPagingSQL(oldSql, pageNo, pageSize);
		System.out.println(newSql);
		
		pageNo = 0;
		pageSize = 3;
		newSql = sqlPageHandle.handlerPagingSQL(oldSql, pageNo, pageSize);
		System.out.println(newSql);
		
		pageNo = 1;
		pageSize = 0;
		newSql = sqlPageHandle.handlerPagingSQL(oldSql, pageNo, pageSize);
		System.out.println(newSql);*/
		
	}
	
	@Test
	public void testMysqlSQLPageHandleImpl() {
		SQLPageHandle sqlPageHandle = new MysqlSQLPageHandleImpl();
		String oldSql = "SELECT * FROM test_department ORDER BY ID ASC";
		int pageNo = 1;
		int pageSize = 3;
		String newSql = sqlPageHandle.handlerPagingSQL(oldSql, pageNo, pageSize);
		System.out.println(newSql);
		
		pageNo = 2;
		pageSize = 3;
		newSql = sqlPageHandle.handlerPagingSQL(oldSql, pageNo, pageSize);
		System.out.println(newSql);
		
		pageNo = 3;
		pageSize = 3;
		newSql = sqlPageHandle.handlerPagingSQL(oldSql, pageNo, pageSize);
		System.out.println(newSql);
		
		pageNo = 4;
		pageSize = 3;
		newSql = sqlPageHandle.handlerPagingSQL(oldSql, pageNo, pageSize);
		System.out.println(newSql);
		
		pageNo = 0;
		pageSize = 3;
		newSql = sqlPageHandle.handlerPagingSQL(oldSql, pageNo, pageSize);
		System.out.println(newSql);

		pageNo = 1;
		pageSize = 0;
		newSql = sqlPageHandle.handlerPagingSQL(oldSql, pageNo, pageSize);
		System.out.println(newSql);

		pageNo = 1;
		pageSize = 10;
		newSql = sqlPageHandle.handlerPagingSQL(oldSql, pageNo, pageSize);
		System.out.println(newSql);
	}
}
