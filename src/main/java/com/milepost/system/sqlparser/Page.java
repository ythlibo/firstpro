package com.milepost.system.sqlparser;

import java.util.List;

/**
 * 查询列表的结果集封装
 * @author HRF
 *
 * @param <T>
 */
public class Page<T> {

    //一页显示的记录数,<=0的数表示不分页
    private int pageSize;
    //记录总数
    private int totalRows;
    //总页数,
    private int totalPages;
    //当前页码,<=0的数表示第一页
    private int pageNo;
    //起始行数(从0开始，-1表示结果集中没有数据)
    private int startIndex;
    //结束行数(从0开始，-1表示结果集中没有数据)
    private int lastIndex;
    //结果集存放List
    private List<T> rows;
    
    public Page(){

    }
    
    public Page(int pageNo, int pageSize, int totalRows, List<T> rows) {
    	if(pageNo<=0){
    		this.pageNo = 1;
    	}else{
    		this.pageNo = pageNo;
    	}
    	if(pageSize<=0){
    		this.pageSize = rows.size();
    	}else{
    		this.pageSize = pageSize;
    	}
    	this.rows = rows;
		this.totalRows = totalRows;
        setTotalPages();
        setStartIndex();
        setLastIndex();
	}
    /*
    public Page(int pageNo, int pageSize, List<T> rows) {
    	this.pageSize = pageSize;
		this.pageNo = pageNo;
		this.rows = rows;
		this.totalRows = (rows.isEmpty() ? 0 : rows.size());
        setTotalPages();
        setStartIndex();
        setLastIndex();
	}*/

    public List<T> getRows() {
        return rows;
    }

    public void setRows(List<T> rows) {
        this.rows = rows;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getPageNo() {
		return pageNo;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}

    public int getTotalRows() {
		return totalRows;
	}

	public void setTotalRows(int totalRows) {
		this.totalRows = totalRows;
	}

	public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages() {
        if(totalRows % pageSize == 0){
            this.totalPages = totalRows / pageSize;
        }else{
            this.totalPages = (totalRows / pageSize) + 1;
        }
    }

    public int getStartIndex() {
        return startIndex;
    }

    public void setStartIndex() {
    	if(rows.size()>0){
    		this.startIndex = (pageNo - 1) * pageSize;
    	}else{
    		this.startIndex = -1;
    	}
    }

    public int getLastIndex() {
        return lastIndex;
    }

    public void setLastIndex() {
    	if(rows.size()>0){
    		if( totalRows < pageSize){
    			this.lastIndex = totalRows-1;
    		}else if(( (totalRows % pageSize)==0) || (((totalRows % pageSize)!=0) && (pageNo<totalPages))){
    			this.lastIndex = (pageNo * pageSize)-1;
    		}else if( ((totalRows % pageSize)!=0) && (pageNo==totalPages)){
    			this.lastIndex = totalRows-1;
    		}
    	}else{
    		this.lastIndex = -1;
    	}
    }

    public List<T> getResultList() {
        return rows;
    }

    public void setResultList(List<T> rows) {
        this.rows = rows;
    }

	@Override
	public String toString() {
		StringBuffer stringBuffer = new StringBuffer();
		stringBuffer.append("Page [\n\r");
		stringBuffer.append("	pageSize=" + pageSize + ",\n\r");
		stringBuffer.append("	pageNo=" + pageNo + ",\n\r");
		stringBuffer.append("	totalRows=" + totalRows + ",\n\r");
		stringBuffer.append("	totalPages=" + totalPages + ",\n\r");
		stringBuffer.append("	startIndex=" + startIndex + ",\n\r");
		stringBuffer.append("	lastIndex=" + lastIndex + ",\n\r");
		stringBuffer.append("	rows=" + rows + "\n\r");
		stringBuffer.append("]");
		return stringBuffer.toString();
	}
    
}
