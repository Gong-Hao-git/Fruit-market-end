package com.wch.utils;

import com.github.pagehelper.Page;

import java.util.List;


/**
 * 分页对象
 * @param <T>
 */
public class Pager<T> {
	/**
	 * 分页的大小（每页的数据条数）
	 */
	private int pageSize;
	/**
	 * 页码（第几页）
	 */
	private int pageNum;
	/**
	 * 总记录数
	 */
	private long total;
	/**
	 * 分页的数据
	 */
	private List<T> datas;

	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public int getPageNum() {
		return pageNum;
	}
	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}
	public long getTotal() {
		return total;
	}
	public void setTotal(long total) {
		this.total = total;
	}
	public List<T> getDatas() {
		return datas;
	}
	public void setDatas(List<T> datas) {
		this.datas = datas;
	}

	/**
	 * 构造方法里面求数据
	 * @param datas
	 */
	public Pager(List<T> datas) {
		if(datas instanceof Page){
			Page<T> page = (Page<T>)datas;
			setPageNum(page.getPageNum());
			setPageSize(page.getPageSize());
			setTotal(page.getTotal());
			setDatas(datas);
		}
		
	}
	public Pager() {
	}
	
	
}
