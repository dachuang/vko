package com.vko.core.dao.impl;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.vko.core.dao.LogDao;
import com.vko.core.entity.Log;
import com.vko.core.entity.Page;

/**
 * Implementation of LogDao.
 * 
 * @author sunyi
 * 
 */
@Repository
public class LogDaoImpl extends TemplateGenericDao<Log> implements LogDao {

	public LogDaoImpl() {
		super(Log.class);
	}
	
	@SuppressWarnings("unchecked")
	public List<Log> findAll(Page page){

		return queryForList("select count(*) from Log l order by l.receiveDate desc",
				"from Log l order by l.receiveDate desc",
				null,
				page);
	}

	@SuppressWarnings("unchecked")
	public List<Log> findLogs(String beginTime, String endTime ,Page page) throws DataAccessException {
		
		return queryForList("select count(*) from Log l where l.receiveDate between to_date(?,'yyyy-mm-dd') and to_date(?,'yyyy-mm-dd') order by l.receiveDate desc",
				"from Log l where l.receiveDate between to_date(?,'yyyy-mm-dd') and to_date(?,'yyyy-mm-dd') order by l.receiveDate desc",
				new Object[]{beginTime,endTime},
				page);
	}

}
