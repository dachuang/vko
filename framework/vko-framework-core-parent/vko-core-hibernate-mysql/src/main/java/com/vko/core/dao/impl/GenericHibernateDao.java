package com.vko.core.dao.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.orm.hibernate3.HibernateTemplate;

/**
 * This base class is prepared for subclass to do CRUD easily.
 * 
 * @author darkwing
 */
public abstract class GenericHibernateDao {

	protected static final Object[] EMPTY_OBJECT_ARRAY = new Object[] {};

	protected final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	protected HibernateTemplate hibernateTemplate;

	@Autowired
	protected JdbcTemplate jdbcTemplate;
	
	/*@Autowired 
	protected SqlSessionTemplate myBatisSqlSession;*/

	/*public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
		this.hibernateTemplate = hibernateTemplate;
	}*/
}
