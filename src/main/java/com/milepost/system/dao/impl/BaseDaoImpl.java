package com.milepost.system.dao.impl;

import java.sql.DatabaseMetaData;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.milepost.system.dao.BaseDao;
import com.milepost.system.sqlparser.Page;
import com.milepost.system.sqlparser.ParserSqlToDynamic;
import com.milepost.system.sqlparser.SQLPageHandle;

@Repository
public class BaseDaoImpl implements BaseDao {

	private static Logger logger = LoggerFactory.getLogger(BaseDaoImpl.class);

	/**
	 * 数据库方言
	 */
	private static final String DIALECT_MYSQL = "MySQL";
	private static final String DIALECT_ORACLE = "Oracle";

	// 一个BaseDao的实现类中只能注入一个NamedParameterJdbcTemplate，即一个BaseDao的实现类操作一个数据库，
	// 如果要实现多数据库需要在applicationContext.xml中配置多个NamedParameterJdbcTemplate，并且建立多个BaseDao的实现类

	/**
	 * 只允许使用命名参数“:xxx”的sql语句，
	 */
	@Autowired
	private NamedParameterJdbcTemplate npJdbcTemplate;

	/**
	 * 只允许使用“?”占位符的sql语句
	 */
	// @Autowired，不用注入，可通过NamedParameterJdbcTemplate获取
	// private JdbcTemplate jdbcTemplate;
	private JdbcTemplate getJdbcTemplate() {
		return (JdbcTemplate) npJdbcTemplate.getJdbcOperations();
	}

	/**
	 * 分页
	 */
	@Resource(name = "mysqlSQLPageHandleImpl")
	protected SQLPageHandle mysqlSQLPageHandle;
	@Resource(name = "oracleSQLPageHandleImpl")
	protected SQLPageHandle oracleSQLPageHandle;

	/**
	 * 获取数据库方言，Oracle、MySQL
	 * 
	 * @return
	 */
	private String getDialect() {
		String dialect = null;
		try {
			DatabaseMetaData databaseMetaData = getJdbcTemplate().getDataSource().getConnection().getMetaData();
			dialect = databaseMetaData.getDatabaseProductName();// Oracle、MySQL
		} catch (Exception e) {
			logger.error("GetDialect occur exception", e);
		}
		return dialect;
	}
	
	@Override
	public <T> List<T> queryForList(String sql, RowMapper<T> rowMapper) throws DataAccessException {
		return getJdbcTemplate().query(sql, rowMapper);//注意，这里不是queryForList;
	}

	@Override
	public <T> Page<T> queryForListPagination(String sql, int pageNo, int pageSize, RowMapper<T> rowMapper)
			throws DataAccessException {
		int totalRows = this.queryForInt(ParserSqlToDynamic.parserSelect2Count(sql));
		if(DIALECT_ORACLE.equals(this.getDialect())){
			sql = oracleSQLPageHandle.handlerPagingSQL(sql, pageNo, pageSize);
		}else if(DIALECT_MYSQL.equals(this.getDialect())){
			sql = mysqlSQLPageHandle.handlerPagingSQL(sql, pageNo, pageSize);
		}
		List<T> list = getJdbcTemplate().query(sql, rowMapper);//注意，这里不是queryForList
	    Page<T> page = new Page<T>(pageNo, pageSize, totalRows, list);
	    return page;
	}

	@Override
	public <T> List<T> queryForList(String sql, Class<T> requiredType) throws DataAccessException {
		RowMapper<T> rowMapper = new BeanPropertyRowMapper<T>(requiredType);
		return getJdbcTemplate().query(sql, rowMapper);//注意，这里不是queryForList
	}

	@Override
	public <T> Page<T> queryForListPagination(String sql, int pageNo, int pageSize, Class<T> requiredType)
			throws DataAccessException {
		int totalRows = this.queryForInt(ParserSqlToDynamic.parserSelect2Count(sql));
		if(DIALECT_ORACLE.equals(this.getDialect())){
			sql = oracleSQLPageHandle.handlerPagingSQL(sql, pageNo, pageSize);
		}else if(DIALECT_MYSQL.equals(this.getDialect())){
			sql = mysqlSQLPageHandle.handlerPagingSQL(sql, pageNo, pageSize);
		}
		RowMapper<T> rowMapper = new BeanPropertyRowMapper<T>(requiredType);
		List<T> list = getJdbcTemplate().query(sql, rowMapper);//注意，这里不是queryForList
	    Page<T> page = new Page<T>(pageNo, pageSize, totalRows, list);
	    return page;
	}

	@Override
	public List<Map<String, Object>> queryForList(String sql) throws DataAccessException {
		return getJdbcTemplate().queryForList(sql);
	}

	@Override
	public Page<Map<String, Object>> queryForListPagination(String sql, int pageNo, int pageSize)
			throws DataAccessException {
		int totalRows = this.queryForInt(ParserSqlToDynamic.parserSelect2Count(sql));
		if(DIALECT_ORACLE.equals(this.getDialect())){
			sql = oracleSQLPageHandle.handlerPagingSQL(sql, pageNo, pageSize);
		}else if(DIALECT_MYSQL.equals(this.getDialect())){
			sql = mysqlSQLPageHandle.handlerPagingSQL(sql, pageNo, pageSize);
		}
		List<Map<String, Object>> list = getJdbcTemplate().queryForList(sql);
	    Page<Map<String, Object>> page = new Page<Map<String, Object>>(pageNo, pageSize, totalRows, list);
	    return page;
	}

	@Override
	public <T> T queryWithRowMapper(String sql, RowMapper<T> rowMapper) throws DataAccessException {
		return getJdbcTemplate().queryForObject(sql, rowMapper);
	}

	@Override
	public <T> T queryForBean(String sql, Class<T> requiredType) throws DataAccessException {
		RowMapper<T> rowMapper = new BeanPropertyRowMapper<T>(requiredType);
		return getJdbcTemplate().queryForObject(sql, rowMapper);
	}

	@Override
	public Map<String, Object> queryForMap(String sql) throws DataAccessException {
		return getJdbcTemplate().queryForMap(sql);
	}

	@Override
	public String queryForString(String sql) throws DataAccessException {
		return this.queryForObject(sql, String.class);
	}

	@Override
	public int queryForInt(String sql) throws DataAccessException {
		return this.queryForObject(sql, Integer.class);
	}

	@Override
	public long queryForLong(String sql) throws DataAccessException {
		return this.queryForObject(sql, Long.class);
	}

	@Override
	public <T> T queryForObject(String sql, Class<T> requiredType) throws DataAccessException {
		return getJdbcTemplate().queryForObject(sql, requiredType);
	}

	@Override
	public int updateOrInsertOrDelete(final String sql) throws DataAccessException {
		return getJdbcTemplate().update(sql);
	}

	@Override
	public int updateOrInsertOrDelete(String sql, Map<String, ?> paramMap) throws Exception{
		String newSql = ParserSqlToDynamic.parserSql(sql, paramMap);
		if((sql!=null&&sql.equals(newSql)) || paramMap==null || paramMap.isEmpty()){
			return this.updateOrInsertOrDelete(newSql);
		}else{
			return npJdbcTemplate.update(newSql, paramMap);
		}
	}
	
	@Override
	public int[] batchUpdateOrInsertOrDelete(String[] sqlArray) throws DataAccessException {
		return getJdbcTemplate().batchUpdate(sqlArray);
	}
	
	@Override
	public int[] batchUpdateOrInsertOrDelete(String sql, List<Object[]> batchArgs) throws DataAccessException{
		return getJdbcTemplate().batchUpdate(sql, batchArgs);
	}

	@Override
	public int[] batchUpdateOrInsertOrDelete(String sql, List<Object[]> batchArgs, int[] argTypes) throws DataAccessException{
		return getJdbcTemplate().batchUpdate(sql, batchArgs, argTypes);
	}
	
	@Override
	public void execute(final String sql) throws DataAccessException {
		getJdbcTemplate().execute(sql);
	}

}
