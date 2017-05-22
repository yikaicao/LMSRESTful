package com.gcit.lms.service;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.gcit.lms.dao.BookCopyDAO;
import com.gcit.lms.dao.BookDAO;
import com.gcit.lms.dao.BorrowerDAO;
import com.gcit.lms.entity.Book;
import com.gcit.lms.entity.BookCopy;
import com.gcit.lms.entity.Borrower;

@RestController
public class BorrowerService {

	@Autowired
	BookDAO bdao;

	@Autowired
	BorrowerDAO bodao;

	@Autowired
	BookCopyDAO bcdao;

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
	
	@RequestMapping(value = "/bookloans/{branchId}/{borrowerId}", method = RequestMethod.GET, produces = "applicaiton/json")
	public BookLoan readBookLoan(@PathVariable Integer branchId, @PathVariable Integer borrowerId) {
		return null;
	}

	@Transactional
	@RequestMapping(value = "checkoutbook/{branchId}/{bookId}/{borrowerId}", method = RequestMethod.POST)
	public void  checkoutBook(@PathVariable Integer branchId, @PathVariable Integer bookId, @PathVariable Integer borrowerId) {
		bcdao.decrementBookCopy(branchId, bookId);
		bodao.addBookLoan(branchId, bookId, borrowerId);
	}
	
}
