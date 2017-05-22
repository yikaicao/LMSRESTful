package com.gcit.lms.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import com.gcit.lms.entity.BookCopy;

public class BookCopyDAO extends BaseDAO implements ResultSetExtractor<List<BookCopy>> {

	public List<BookCopy> readBookCopyByBranchID(Integer branchId) throws DataAccessException {
		return template.query("select * from tbl_book_copies where branchId = ?", new Object[] { branchId }, this);
	}

	public void updateBookCopy(BookCopy bc) throws DataAccessException {
		template.update("update tbl_book_copies set noOfCopies = ? where bookId = ? and branchId = ?",
				new Object[] { bc.getNoOfCopies(), bc.getBookId(), bc.getBranchId() });
	}

	@Override
	public List<BookCopy> extractData(ResultSet rs) throws SQLException, DataAccessException {
		List<BookCopy> bookCopies = new ArrayList<>();
		while (rs.next()) {
			BookCopy bc = new BookCopy();
			bc.setBookId(rs.getInt("bookId"));
			bc.setBranchId(rs.getInt("branchId"));
			bc.setNoOfCopies(rs.getInt("noOfCopies"));
			bookCopies.add(bc);
		}
		return bookCopies;
	}

}
