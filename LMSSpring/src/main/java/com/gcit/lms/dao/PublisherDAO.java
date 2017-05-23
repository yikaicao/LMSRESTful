package com.gcit.lms.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import com.gcit.lms.entity.Publisher;

public class PublisherDAO extends BaseDAO implements ResultSetExtractor<List<Publisher>> {

	public List<Publisher> readAllPublishers() throws DataAccessException {
		return template.query("select * from tbl_publisher", this);
	}

	public Publisher readPublisherByPK(Integer publisherId) {
		List<Publisher> pbs = template.query("select * from tbl_publisher where publisherId = ?",
				new Object[] { publisherId }, this);
		if (pbs != null && !pbs.isEmpty())
			return pbs.get(0);
		return null;
	}

	public void updatePublisher(Publisher publisher) {
		template.update(
				"update tbl_publisher set publisherName = ?, publisherAddress = ?, publisherPhone = ? where publisherId = ?",
				new Object[] { publisher.getPublisherName(), publisher.getPublisherAddress(),
						publisher.getPublisherPhone(), publisher.getPublisherId() });
	}

	public void deltePublisherByPK(Integer primaryKey) {
		template.update("delete from tbl_publisher where publisherId = ?", new Object[] { primaryKey });
	}

	@Override
	public List<Publisher> extractData(ResultSet rs) throws SQLException, DataAccessException {
		List<Publisher> publishers = new ArrayList<>();
		while (rs.next()) {
			Publisher p = new Publisher();
			p.setPublisherId(rs.getInt("publisherId"));
			p.setPublisherName(rs.getString("publisherName"));
			p.setPublisherAddress(rs.getString("publisherAddress"));
			p.setPublisherPhone(rs.getString("publisherPhone"));
			publishers.add(p);
		}
		return publishers;
	}

}
