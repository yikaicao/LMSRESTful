package com.gcit.lms.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.jdbc.core.JdbcTemplate;

public abstract class BaseDAO {
	
	@Autowired
	public JdbcTemplate template;
	
	@Autowired
	public MongoTemplate mongoTemplate;
	
	@Autowired
	public SequenceDAO seqDao;
	
}
