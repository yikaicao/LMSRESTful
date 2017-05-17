package com.gcit.lms.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import com.gcit.lms.entity.SequenceId;

public class SequenceDAO {

	@Autowired
	MongoOperations mongoOps;

	public long getNextSequenceId(String key) {
		Query query = new Query(Criteria.where("_id").is(key));

		Update update = new Update();
		update.inc("seq", 1);

		FindAndModifyOptions options = new FindAndModifyOptions();
		options.returnNew(true);

		SequenceId seqId = mongoOps.findAndModify(query, update, options, SequenceId.class);

		if (seqId == null) {
			return new Long(0);
		}

		return seqId.getSeq();

	}
}
