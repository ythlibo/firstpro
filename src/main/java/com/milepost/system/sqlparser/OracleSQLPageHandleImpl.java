package com.milepost.system.sqlparser;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * oracle分页实现
 * 
 * @author HRF
 */
public class OracleSQLPageHandleImpl implements SQLPageHandle {

	private static Logger logger = LoggerFactory.getLogger(OracleSQLPageHandleImpl.class);
	
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
			logger.debug("Paging parameter [oldSql="+ oldSql +"; pageNo="+ pageNo +"; pageSize="+ pageSize +"]");
			logger.debug("Before paging sql ["+ oldSql +"]");
		}
		if (pageSize > 0) {
			int startResult = (pageNo-1)*pageSize;
			int endResult = pageNo * pageSize;

			if (pageNo <= 0) {
				endResult = pageSize;
				oldSql = "select * from (" + oldSql + ") where rownum<=" + endResult;
			} else {
				oldSql = " select row_.*,rownum rownum_ from (" + oldSql + ") row_ where rownum<=" + endResult;
				oldSql = "select * from (" + oldSql + ") where rownum_>" + startResult;
			}
		}
		if (isDebug) {
			logger.debug("After paging sql ["+ oldSql +"]");
		}
		return oldSql;
	}

}
