package com.milepost.system.sqlparser;

/**
 * 分页处理接口
 * 
 * @author HRF
 */
public interface SQLPageHandle {

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
	public String handlerPagingSQL(String oldSql, int pageNo, int pageSize);

}
