package com.gcit.lms.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

public abstract class BaseDAO {
	
	@Autowired
	public JdbcTemplate template;
	
	@Autowired
	public SequenceDAO seqDao;
	
}
