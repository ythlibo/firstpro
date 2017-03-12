package com.milepost.system.sqlparser;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * mysql数据库的分页实现
 * @author HRF
 */
public class MysqlSQLPageHandleImpl implements SQLPageHandle {

	private static Logger logger = LoggerFactory.getLogger(MysqlSQLPageHandleImpl.class);
	
	/**
	 * 将传入的SQL做分页处理
	 * 
	 * @param String
	 *            oldSql 原SQL
	 * @param int
	 *            pageNo 第几页，传入<=0的数表示第一页
	 * @param int
	 *            pageSize 每页数量，传入<=0的数表示不分页
	 */
	public String handlerPagingSQL(String oldSql, int pageNo, int pageSize){
		boolean isDebug = logger.isDebugEnabled();
		if (isDebug) {
			logger.debug("HandlerPagingSQL parameter [oldSql="+ oldSql +"; pageNo="+ pageNo +"; pageSize="+ pageSize +"].");
			logger.debug("Before handlerPagingSQL sql ["+ oldSql +"].");
		}
		StringBuffer sql = new StringBuffer(oldSql);
		if(pageNo<=0){
			pageNo = 1;
		}
		if (pageSize > 0) {
			int startResult = (pageNo - 1)*pageSize;
			if (startResult <= 0) {
				sql.append(" limit ").append(pageSize);
			} else {
				sql.append(" limit ").append(startResult).append(",").append(pageSize);
			}
		}
		String newSql = sql.toString();
		if (isDebug) {
			logger.debug("After handlerPagingSQL sql ["+ newSql +"].");
		}
		return newSql;
	}

}
