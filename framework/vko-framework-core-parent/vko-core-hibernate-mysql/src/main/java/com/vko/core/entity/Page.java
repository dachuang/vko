package com.vko.core.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Page is not a domain object but is used to store and fetch page information.
 * 
 * @author king.zhu
 */
@SuppressWarnings("rawtypes")
public class Page implements Serializable {
	private static final long serialVersionUID = -4431525375645176613L;
	public static final int DEFAULT_PAGE_SIZE = 10;

	private int pageIndex;
	private int pageSize;
	private int totalCount;
	private int pageCount;

	private List records;

	public Page(int pageIndex, int pageSize) {
		// check:
		if (pageIndex < 1)
			pageIndex = 1;
		if (pageSize < 1)
			pageSize = 1;
		this.pageIndex = pageIndex;
		this.pageSize = pageSize;
	}

	public Page(int pageIndex) {
		this(pageIndex, DEFAULT_PAGE_SIZE);
	}

	public int getPageIndex() {
		return pageIndex;
	}

	public int getPageSize() {
		return pageSize;
	}

	public int getPageCount() {
		return pageCount;
	}

	public int getTotalCount() {
		return totalCount;
	}

	public int getFirstResult() {
		return (pageIndex - 1) * pageSize;
	}

	public boolean getHasPrevious() {
		return pageIndex > 1;
	}

	public boolean getHasNext() {
		return pageIndex < pageCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
		pageCount = totalCount / pageSize + (totalCount % pageSize == 0 ? 0 : 1);
		// adjust pageIndex:
		if (totalCount == 0) {
			if (pageIndex != 1)
				throw new IndexOutOfBoundsException("Page index out of range.");
		} else {
			if (pageIndex > pageCount) {
				//此处默认处理了异常，将当前页码设置为最后一页（未将异常抛出）
				pageIndex = pageCount;
				//throw new IndexOutOfBoundsException("Page index out of range.");
			}
		}
	}

	public boolean isEmpty() {
		return totalCount == 0;
	}

	public List getRecords() {
		return records;
	}

	public void setRecords(List records) {
		this.records = records;
	}

}
