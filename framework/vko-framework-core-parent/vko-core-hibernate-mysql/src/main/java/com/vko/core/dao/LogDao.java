package com.vko.core.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.vko.core.entity.Log;
import com.vko.core.entity.Page;




public interface LogDao  extends GenericDao<Log>{
	
	//列出所有日志
	public List<Log> findAll(Page page) ;
	
	//查询某个时间段内的日志
	public List<Log> findLogs(String beginTime , String endTime ,Page page) throws DataAccessException;
	
}
