package com.gcit.lms.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import com.gcit.lms.entity.Borrower;

public class BorrowerDAO extends BaseDAO implements ResultSetExtractor<List<Borrower>> {

	public Borrower readBorrowerByID(Integer borrowerId) throws DataAccessException {
		List<Borrower> borrowers = template.query("select * from tbl_borrower where cardNo = ?",
				new Object[] { borrowerId }, this);
		if (borrowers != null && !borrowers.isEmpty()) {
			return borrowers.get(0);
		}
		return null;
	}

	@Override
	public List<Borrower> extractData(ResultSet rs) throws SQLException, DataAccessException {
		List<Borrower> borrowers = new ArrayList<>();
		while (rs.next()) {
			Borrower bo = new Borrower();
			bo.setCardNo(rs.getInt("cardNo"));
			bo.setName(rs.getString("name"));
			bo.setAddress(rs.getString("address"));
			bo.setPhone(rs.getString("phone"));
			borrowers.add(bo);
		}
		return borrowers;
	}

}
