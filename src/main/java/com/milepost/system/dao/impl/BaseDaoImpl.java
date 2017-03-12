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

import com.milepost.system.dao.BaseDao;
import com.milepost.system.sqlparser.Page;
import com.milepost.system.sqlparser.ParserSqlToDynamic;
import com.milepost.system.sqlparser.SQLPageHandle;

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
	 * 分页
	 */
	@Resource(name = "mysqlSQLPageHandleImpl")
	protected SQLPageHandle mysqlSQLPageHandle;
	@Resource(name = "oracleSQLPageHandleImpl")
	protected SQLPageHandle oracleSQLPageHandle;

	
	/**
	 * 只允许使用“?”占位符的sql语句
	 */
	// @Autowired，不用注入，可通过NamedParameterJdbcTemplate获取
	// private JdbcTemplate jdbcTemplate;
	private JdbcTemplate getJdbcTemplate() {
		return (JdbcTemplate) npJdbcTemplate.getJdbcOperations();
	}
	
	/**
	 * 对sql语句进行分页处理，暂时支持Oracle和MySql，如果是其他数据库，返回原sql
	 * @param newSql
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	private String handlerPagingSql(String sql, int pageNo, int pageSize) {
		String newSql  = sql;
		String dialect = this.getDialect();
		if(DIALECT_ORACLE.equals(dialect)){
			newSql = oracleSQLPageHandle.handlerPagingSQL(newSql, pageNo, pageSize);
		}else if(DIALECT_MYSQL.equals(dialect)){
			newSql = mysqlSQLPageHandle.handlerPagingSQL(newSql, pageNo, pageSize);
		}else{
			if (logger.isDebugEnabled()) {
				logger.debug("Paging processing for [" + dialect + "] database is not supported.");
			}
		}
		return newSql;
	}
	
	/**
	 * 获取数据库方言，Oracle、MySQL
	 */
	private String getDialect() {
		String dialect = null;
		try {
			DatabaseMetaData databaseMetaData = getJdbcTemplate().getDataSource().getConnection().getMetaData();
			dialect = databaseMetaData.getDatabaseProductName();// Oracle、MySQL
			if (logger.isDebugEnabled()) {
				logger.debug( getClass().getName() + " is injected [" + dialect + "] datasource.");
			}
		} catch (Exception e) {
			logger.error("SetDialect occur exception.", e);
		}
		return dialect;
	}
	
	@Override
	public <T> List<T> queryForList(String sql, RowMapper<T> rowMapper) throws DataAccessException {
		return getJdbcTemplate().query(sql, rowMapper);//注意，这里不是queryForList;
	}
	
	@Override
	public <T> List<T> queryForList(String sql, Map<String, ?> paramMap, RowMapper<T> rowMapper) throws Exception {
		String newSql = ParserSqlToDynamic.parserSql(sql, paramMap);
		if(paramMap==null || paramMap.isEmpty()){
			return this.queryForList(newSql, rowMapper);//注意，这里不是queryForList;
		}else{
			return npJdbcTemplate.query(newSql, paramMap, rowMapper);//注意，这里不是queryForList;
		}
	}

	@Override
	public <T> Page<T> queryForListPagination(String sql, int pageNo, int pageSize, RowMapper<T> rowMapper)
			throws DataAccessException {
		int totalRows = this.queryForInt(ParserSqlToDynamic.parserSelect2Count(sql));
		sql = handlerPagingSql(sql, pageNo, pageSize);
		List<T> list = getJdbcTemplate().query(sql, rowMapper);//注意，这里不是queryForList
	    Page<T> page = new Page<T>(pageNo, pageSize, totalRows, list);
	    return page;
	}
	
	@Override
	public <T> Page<T> queryForListPagination(String sql, Map<String, ?> paramMap, int pageNo, int pageSize, RowMapper<T> rowMapper)
			throws Exception {
		String newSql = ParserSqlToDynamic.parserSql(sql, paramMap);
		if(paramMap==null || paramMap.isEmpty()){
			return this.queryForListPagination(newSql, pageNo, pageSize, rowMapper);
		}else{
			int totalRows = this.queryForInt(ParserSqlToDynamic.parserSelect2Count(newSql), paramMap);
			newSql = handlerPagingSql(newSql, pageNo, pageSize);
			List<T> list = npJdbcTemplate.query(newSql, paramMap, rowMapper);//注意，这里不是queryForList
		    Page<T> page = new Page<T>(pageNo, pageSize, totalRows, list);
		    return page;
		}
	}

	@Override
	public <T> List<T> queryForList(String sql, Class<T> requiredType) throws DataAccessException {
		RowMapper<T> rowMapper = new BeanPropertyRowMapper<T>(requiredType);
		return getJdbcTemplate().query(sql, rowMapper);//注意，这里不是queryForList
	}
	
	@Override
	public <T> List<T> queryForList(String sql, Map<String, ?> paramMap, Class<T> requiredType) throws Exception {
		String newSql = ParserSqlToDynamic.parserSql(sql, paramMap);
		if(paramMap==null || paramMap.isEmpty()){
			return this.queryForList(newSql, requiredType);
		}else{
			RowMapper<T> rowMapper = new BeanPropertyRowMapper<T>(requiredType);
			return npJdbcTemplate.query(newSql, paramMap, rowMapper);//注意，这里不是queryForList
		}
	}
	
	

	@Override
	public <T> Page<T> queryForListPagination(String sql, int pageNo, int pageSize, Class<T> requiredType)
			throws DataAccessException {
		int totalRows = this.queryForInt(ParserSqlToDynamic.parserSelect2Count(sql));
		sql = handlerPagingSql(sql, pageNo, pageSize);
		RowMapper<T> rowMapper = new BeanPropertyRowMapper<T>(requiredType);
		List<T> list = getJdbcTemplate().query(sql, rowMapper);//注意，这里不是queryForList
	    Page<T> page = new Page<T>(pageNo, pageSize, totalRows, list);
	    return page;
	}
	
	@Override
	public <T> Page<T> queryForListPagination(String sql, Map<String, ?> paramMap, int pageNo, int pageSize, Class<T> requiredType) 
			throws Exception{
		String newSql = ParserSqlToDynamic.parserSql(sql, paramMap);
		if(paramMap==null || paramMap.isEmpty()){
			return this.queryForListPagination(newSql, pageNo, pageSize, requiredType);
		}else{
			int totalRows = this.queryForInt(ParserSqlToDynamic.parserSelect2Count(newSql), paramMap);
			newSql = handlerPagingSql(newSql, pageNo, pageSize);
			RowMapper<T> rowMapper = new BeanPropertyRowMapper<T>(requiredType);
			List<T> list = npJdbcTemplate.query(newSql, paramMap, rowMapper);//注意，这里不是queryForList
		    Page<T> page = new Page<T>(pageNo, pageSize, totalRows, list);
		    return page;
		}
	}

	@Override
	public List<Map<String, Object>> queryForList(String sql) throws DataAccessException {
		return getJdbcTemplate().queryForList(sql);
	}

	@Override
	public List<Map<String, Object>> queryForList(String sql, Map<String, ?> paramMap) throws Exception{
		String newSql = ParserSqlToDynamic.parserSql(sql, paramMap);
		if(paramMap==null || paramMap.isEmpty()){
			return this.queryForList(newSql);
		}else{
			return npJdbcTemplate.queryForList(newSql, paramMap);
		}
	}
	
	@Override
	public Page<Map<String, Object>> queryForListPagination(String sql, int pageNo, int pageSize)
			throws DataAccessException {
		int totalRows = this.queryForInt(ParserSqlToDynamic.parserSelect2Count(sql));
		sql = handlerPagingSql(sql, pageNo, pageSize);
		List<Map<String, Object>> list = getJdbcTemplate().queryForList(sql);
	    Page<Map<String, Object>> page = new Page<Map<String, Object>>(pageNo, pageSize, totalRows, list);
	    return page;
	}
	
	@Override
	public Page<Map<String, Object>> queryForListPagination(String sql, Map<String, ?> paramMap, int pageNo, int pageSize) 
			throws Exception{
		String newSql = ParserSqlToDynamic.parserSql(sql, paramMap);
		if(paramMap==null || paramMap.isEmpty()){
			return this.queryForListPagination(newSql, pageNo, pageSize);
		}else{
			int totalRows = this.queryForInt(ParserSqlToDynamic.parserSelect2Count(newSql), paramMap);
			newSql = this.handlerPagingSql(newSql, pageNo, pageSize);
			List<Map<String, Object>> list = npJdbcTemplate.queryForList(newSql, paramMap);
		    Page<Map<String, Object>> page = new Page<Map<String, Object>>(pageNo, pageSize, totalRows, list);
		    return page;
		}
	}

	@Override
	public <T> T queryWithRowMapper(String sql, RowMapper<T> rowMapper) throws DataAccessException {
		return getJdbcTemplate().queryForObject(sql, rowMapper);
	}
	
	@Override
	public <T> T queryWithRowMapper(String sql, Map<String, ?> paramMap, RowMapper<T> rowMapper) throws Exception {
		String newSql = ParserSqlToDynamic.parserSql(sql, paramMap);
		if(paramMap==null || paramMap.isEmpty()){
			return this.queryWithRowMapper(newSql,rowMapper);
		}else{
			return npJdbcTemplate.queryForObject(newSql, paramMap, rowMapper);
		}
	}

	@Override
	public <T> T queryForBean(String sql, Class<T> requiredType) throws DataAccessException {
		RowMapper<T> rowMapper = new BeanPropertyRowMapper<T>(requiredType);
		return getJdbcTemplate().queryForObject(sql, rowMapper);
	}
	
	@Override
	public <T> T queryForBean(String sql, Map<String, ?> paramMap, Class<T> requiredType) throws Exception {
		String newSql = ParserSqlToDynamic.parserSql(sql, paramMap);
		if(paramMap==null || paramMap.isEmpty()){
			return this.queryForBean(newSql,requiredType);
		}else{
			RowMapper<T> rowMapper = new BeanPropertyRowMapper<T>(requiredType);
			return npJdbcTemplate.queryForObject(newSql, paramMap, rowMapper);
		}
	}

	@Override
	public Map<String, Object> queryForMap(String sql) throws DataAccessException {
		return getJdbcTemplate().queryForMap(sql);
	}
	
	@Override
	public Map<String, Object> queryForMap(String sql, Map<String, ?> paramMap) throws Exception {
		String newSql = ParserSqlToDynamic.parserSql(sql, paramMap);
		if(paramMap==null || paramMap.isEmpty()){
			return this.queryForMap(newSql);
		}else{
			return npJdbcTemplate.queryForMap(newSql, paramMap);
		}
	}

	@Override
	public String queryForString(String sql) throws DataAccessException {
		return this.queryForObject(sql, String.class);
	}
	
	@Override
	public String queryForString(String sql, Map<String, ?> paramMap) throws Exception {
		return this.queryForObject(sql, paramMap, String.class);
	}

	@Override
	public int queryForInt(String sql) throws DataAccessException {
		return this.queryForObject(sql, Integer.class);
	}

	public int queryForInt(String sql, Map<String, ?> paramMap) throws Exception{
		return this.queryForObject(sql, paramMap, Integer.class);
	}
	
	@Override
	public long queryForLong(String sql) throws DataAccessException {
		return this.queryForObject(sql, Long.class);
	}

	public long queryForLong(String sql, Map<String, ?> paramMap) throws Exception{
		return this.queryForObject(sql, paramMap, Long.class);
	}
	
	@Override
	public <T> T queryForObject(String sql, Class<T> requiredType) throws DataAccessException {
		return getJdbcTemplate().queryForObject(sql, requiredType);
	}
	
	@Override
	public <T> T queryForObject(String sql, Map<String, ?> paramMap, Class<T> requiredType) throws Exception{
		String newSql = ParserSqlToDynamic.parserSql(sql, paramMap);
		if(paramMap==null || paramMap.isEmpty()){
			return this.queryForObject(newSql, requiredType);
		}else{
			return npJdbcTemplate.queryForObject(newSql, paramMap, requiredType);
		}
	}

	@Override
	public int updateOrInsertOrDelete(String sql) throws DataAccessException {
		return getJdbcTemplate().update(sql);
	}

	@Override
	public int updateOrInsertOrDelete(String sql, Map<String, ?> paramMap) throws Exception{
		String newSql = ParserSqlToDynamic.parserSql(sql, paramMap);
		if(paramMap==null || paramMap.isEmpty()){
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
	public void execute(String sql) throws DataAccessException {
		getJdbcTemplate().execute(sql);
	}
	
}
