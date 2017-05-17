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
	
	private Integer pageNo;
	private Integer pageSize = 10;
	
	public Integer getPageNo() {
		return pageNo;
	}

	public void setPageNo(Integer pageNo) {
		this.pageNo = pageNo;
	}

}
