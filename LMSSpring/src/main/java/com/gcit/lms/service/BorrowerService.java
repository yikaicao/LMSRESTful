package com.gcit.lms.service;

import java.util.Calendar;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.gcit.lms.dao.BookCopyDAO;
import com.gcit.lms.dao.BookDAO;
import com.gcit.lms.dao.BookLoanDAO;
import com.gcit.lms.dao.BorrowerDAO;
import com.gcit.lms.entity.Book;
import com.gcit.lms.entity.BookCopy;
import com.gcit.lms.entity.BookLoan;
import com.gcit.lms.entity.Borrower;

@RestController
public class BorrowerService {

	@Autowired
	BookDAO bdao;

	@Autowired
	BorrowerDAO bodao;

	@Autowired
	BookCopyDAO bcdao;

	@Autowired
	BookLoanDAO bldao;

	@RequestMapping(value = "/borrowers/{primaryKey}", method = RequestMethod.GET, produces = "application/json")
	public Borrower readBorrower(@PathVariable Integer primaryKey, HttpServletResponse response) {

		if (bodao.readBorrowerByID(primaryKey) != null)
			return bodao.readBorrowerByID(primaryKey);
		else
			response.setStatus(HttpServletResponse.SC_NO_CONTENT);

		return null;

	}

	@RequestMapping(value = "/availablebooks/{borrowerId}/{branchId}", method = RequestMethod.GET, produces = "application/json")
	public List<Book> readAvailableBooks(@PathVariable Integer borrowerId, @PathVariable Integer branchId) {
		return bdao.readAvailableBooks(borrowerId, branchId);
	}

	@RequestMapping(value = "/bookcopy/{branchId}/{bookId}", method = RequestMethod.GET, produces = "application/json")
	public BookCopy readBookCopy(@PathVariable Integer branchId, @PathVariable Integer bookId) {
		return bcdao.readBookCopyByBranchIdAndBookId(branchId, bookId);
	}

	@RequestMapping(value = "/bookloans/{branchId}/{borrowerId}", method = RequestMethod.GET, produces = "application/json")
	public List<BookLoan> readBookLoans(@PathVariable Integer branchId, @PathVariable Integer borrowerId) {
		try {
			return bldao.readBookLoans(branchId, borrowerId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@RequestMapping(value = "/bookloans/{branchId}/{borrowerId}/{bookId}", method = RequestMethod.GET, produces = "application/json")
	public BookLoan readBookLoan(@PathVariable Integer branchId, @PathVariable Integer borrowerId, @PathVariable Integer bookId) {
		try {
			return bldao.readBookLoan(branchId, borrowerId, bookId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Transactional
	@RequestMapping(value = "checkoutbook/{branchId}/{bookId}/{borrowerId}", method = RequestMethod.POST)
	public void checkoutBook(@PathVariable Integer branchId, @PathVariable Integer bookId,
			@PathVariable Integer borrowerId) {
		bcdao.decrementBookCopy(branchId, bookId);
		bodao.addBookLoan(branchId, bookId, borrowerId);
	}

	@Transactional
	@RequestMapping(value = "bookloans", method = RequestMethod.POST)
	public void returnBook(@RequestBody BookLoan bl) {
		bcdao.incrementBookCopy(bl.getBranchId(), bl.getBookId());
		Calendar calendar = Calendar.getInstance();
		bl.setDateIn(calendar.getTime());
		bodao.returnBookLoan(bl);
	}

}
