package com.gcit.lms.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import com.gcit.lms.entity.BookLoan;

public class BookLoanDAO extends BaseDAO implements ResultSetExtractor<List<BookLoan>> {

	public List<BookLoan> readBookLoans(Integer branchId, Integer borrowerId) throws Exception {
		return template.query("select * from tbl_book_loans where branchId = ? and cardNo = ?",
				new Object[] { branchId, borrowerId }, this);
	}

	public BookLoan readBookLoan(Integer branchId, Integer borrowerId, Integer bookId) {
		List<BookLoan> bls = template.query(
				"select * from tbl_book_loans where branchId = ? and cardNo = ? and bookId = ?",
				new Object[] { branchId, borrowerId, bookId }, this);
		if (bls != null && !bls.isEmpty()) {
			return bls.get(0);
		}
		return null;
	}

	@Override
	public List<BookLoan> extractData(ResultSet rs) throws SQLException, DataAccessException {
		List<BookLoan> bookloans = new ArrayList<>();
		while (rs.next()) {
			BookLoan bl = new BookLoan();
			bl.setBookId(rs.getInt("bookId"));
			bl.setBranchId(rs.getInt("branchId"));
			bl.setCardNo(rs.getInt("cardNo"));
			bl.setDateOut(rs.getDate("dateOut"));
			bl.setDueDate(rs.getDate("dueDate"));
			if (rs.getDate("dateIn") != null)
				bl.setDateIn(rs.getDate("dateIn"));
			bookloans.add(bl);
		}
		return bookloans;
	}

}
