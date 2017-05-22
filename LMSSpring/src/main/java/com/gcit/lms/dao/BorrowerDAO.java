package com.gcit.lms.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import com.gcit.lms.entity.BookLoan;
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

	public void addBookLoan(Integer branchId, Integer bookId, Integer borrowerId) {
		template.update("delete from tbl_book_loans where bookId = ? and branchId = ? and cardNo = ?",
				new Object[] { bookId, branchId, borrowerId });
		template.update(
				"insert into tbl_book_loans (bookId, branchId, cardNo, dateOut, dueDate) values(?, ?, ?, CURDATE(), DATE_ADD(CURDATE(),INTERVAL 7 DAY))",
				new Object[] { bookId, branchId, borrowerId });
	}

	public void returnBookLoan(BookLoan bl) {
		template.update("update tbl_book_loans set dateIn = ? where bookId = ? and branchId = ? and cardNo = ?",
				new Object[] { bl.getDateIn(), bl.getBookId(), bl.getBranchId(), bl.getCardNo() });
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
