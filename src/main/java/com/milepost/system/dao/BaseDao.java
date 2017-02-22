package com.milepost.system.dao;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.RowMapper;

import com.milepost.system.sqlparser.Page;

/**
 * @author HRF
 */
public interface BaseDao {

	

	/**
	 * 查询不分页的列表
	 * @param sql
	 * @param rowMapper 自己实现的RowMapper，用来指定如何封装结果集
	 * @return
	 * @throws DataAccessException
	 */
	public <T> List<T> queryForList(String sql, RowMapper<T> rowMapper) throws DataAccessException;
	
	/**
	 * 查询分页的列表
	 * @param sql
	 * @param pageNo 页码
	 * @param pageSize 每页记录数
	 * @param rowMapper 自己实现的RowMapper，用来指定如何封装结果集
	 * @return
	 * @throws DataAccessException
	 */
	public <T> Page<T> queryForListPagination(String sql, int pageNo, int pageSize, RowMapper<T> rowMapper) throws DataAccessException;
	
	/**
	 * 查询不分页的列表
	 * @param sql
	 * @param requiredType
	 * @return 结果集中存放指定的类型的元素
	 * @throws DataAccessException
	 */
	public <T> List<T> queryForList(String sql, Class<T> requiredType) throws DataAccessException;
	
	/**
	 * 查询分页的列表
	 * @param sql
	 * @param pageNo 页码
	 * @param pageSize 每页记录数
	 * @param requiredType 
	 * @return Page中的结果集中存放指定的类型的元素
	 * @throws DataAccessException
	 */
	public <T> Page<T> queryForListPagination(String sql, int pageNo, int pageSize, Class<T> requiredType) throws DataAccessException;
	
	/**
	 * 查询不分页的列表
	 * @param sql
	 * @return 
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryForList(String sql) throws DataAccessException;
	
	/**
	 * 查询分页的列表
	 * @param sql
	 * @param pageNo 页码
	 * @param pageSize 每页记录数
	 * @return Page 对象，可封装了结果集和各种分页信息，其中结果集是List<Map<String, Object>>类型
	 * @throws DataAccessException
	 */
	public Page<Map<String, Object>> queryForListPagination(String sql, int pageNo, int pageSize) throws DataAccessException;
	
	
	/**
	 * 单行查询
	 * @param sql
	 * @param rowMapper 自己实现的RowMapper，用来指定如何封装结果集
	 * @return
	 * @throws DataAccessException
	 */
	public <T> T queryWithRowMapper(String sql, RowMapper<T> rowMapper) throws DataAccessException;
	
	/**
	 * 单行查询
	 * @param sql
	 * @param requiredType
	 * @return 指定的类型
	 * @throws DataAccessException
	 */
	public <T> T queryForBean(String sql, Class<T> requiredType) throws DataAccessException;
	
	/**
	 * 单行查询
	 * @param sql 
	 * @return Map<String, Object>的key为sql中列的别名的大写形式
	 * @throws DataAccessException
	 */
	public Map<String, Object> queryForMap(String sql) throws DataAccessException;
	
	/**
	 * 单值查询
	 * @param sql
	 * @return 
	 * @throws DataAccessException
	 */
	public String queryForString(String sql) throws DataAccessException;
	
	/**
	 * 单值查询
	 * @param sql
	 * @return 
	 * @throws DataAccessException
	 */
	public int queryForInt(String sql) throws DataAccessException;

	/**
	 * 单值查询
	 * @param sql
	 * @return
	 * @throws DataAccessException
	 */
	public long queryForLong(String sql) throws DataAccessException;
	
	/**
	 * 单值查询，返回指定的类型
	 * @param sql
	 * @param requiredType 指定的返回值类型
	 * @return
	 * @throws DataAccessException
	 */
	public <T> T queryForObject(String sql, Class<T> requiredType) throws DataAccessException;
	
	
	/**
	 * 执行一个sql语句，sql语句通常为DML（data manipulation language 数据库操纵语言）语句，即SELECT（除外）、UPDATE、INSERT、DELETE
	 * 
	 * @param sql
	 * @return sql语句执行结果影响的行数 
	 * @throws DataAccessException
	 * 
	 * @see int org.springframework.jdbc.core.JdbcTemplate.update(String sql)
	 *      throws DataAccessException
	 */
	public int updateOrInsertOrDelete(final String sql) throws DataAccessException;
	
	/**
	 * 执行一个sql语句，sql语句通常为DML（data manipulation language 数据库操纵语言）语句，即SELECT（除外）、UPDATE、INSERT、DELETE。<br>
	 * sql语句中可包含命名参数，即“:xx”，命名参数有两种类型，可选参数和必须参数。<br>
	 * sql语句中可使用“[”、“]”两个字符来指定参数的类型，被“[”、“]”两个包含的命名参数为可选参数，否则为必须参数。<br>
	 * 对于可选参数，当参数Map.get("xxx")==null时，将被框架忽略，即从原sql语句中移除。<br>
	 * 对于必须参数，当参数Map.get("xxx")==null时，框架讲抛出异常。throw new Exception("In parameter map(" + paramMap + "),there is not a value for parameter '" + paramName + "'");<br>
	 * 
	 * @param sql
	 * @param paramMap 参数Map
	 * @return sql语句执行结果影响的行数
	 * @throws DataAccessException
	 * 
	 * @see int org.springframework.jdbc.core.JdbcTemplate.update(String sql)
	 *      throws DataAccessException
	 */
	public int updateOrInsertOrDelete(String sql, Map<String, ?> paramMap) throws Exception;
	
	/**
	 * 批量执行多个sql语句，sql语句通常为DML（data manipulation language 数据库操纵语言）语句，即SELECT（除外）、UPDATE、INSERT、DELETE。
	 * 
	 * @param sqlArray
	 * @return 每个sql语句执行结果影响的行数
	 * @throws DataAccessException
	 * 
	 * @see int[]
	 *      org.springframework.jdbc.core.JdbcTemplate.batchUpdate(String[] sql)
	 *      throws DataAccessException
	 */
	public int[] batchUpdateOrInsertOrDelete(final String[] sqlArray) throws DataAccessException;
	
	/**
	 * 一个sql语句赋予不同的参数，依次执行，sql语句通常为DML（data manipulation language 数据库操纵语言）语句，即SELECT（除外）、UPDATE、INSERT、DELETE。<br>
	 * 注意，sql中不能有命名参数，只能包含jdbc占位符(?)<br>
	 * 
	 * @param sql
	 * @param batchArgs 参数集合
	 * @return 本方法的返回值并不可靠，可能与数据库驱动包和spring的版本有关，有时候只是简单的返回一组-2，但数据确操作成功了。
	 * @throws DataAccessException
	 */
	public int[] batchUpdateOrInsertOrDelete(String sql, List<Object[]> batchArgs) throws DataAccessException;

	/**
	 * 一个sql语句赋予不同的参数，依次执行，sql语句通常为DML（data manipulation language 数据库操纵语言）语句，即SELECT（除外）、UPDATE、INSERT、DELETE。<br>
	 * 注意，sql中不能有命名参数，只能包含jdbc占位符(?)<br>
	 * 
	 * @param sql
	 * @param batchArgs 参数集合
	 * @param argTypes 参数类型
	 * @return 本方法的返回值并不可靠，可能与数据库驱动包和spring的版本有关，有时候只是简单的返回一组-2，但数据确操作成功了。
	 * @throws DataAccessException
	 */
	public int[] batchUpdateOrInsertOrDelete(String sql, List<Object[]> batchArgs, int[] argTypes) throws DataAccessException;
	
	/**
	 * 执行一个sql语句，通常是DDL（data definition language 数据定义语言）语句，即CREATE、ALTER、DROP等，见http://blog.csdn.net/will130/article/details/49805787。
	 * 
	 * @param sql
	 * @throws DataAccessException
	 * 
	 * @see void org.springframework.jdbc.core.JdbcTemplate.execute(String sql)
	 *      throws DataAccessException
	 */
	public void execute(final String sql) throws DataAccessException;

}
