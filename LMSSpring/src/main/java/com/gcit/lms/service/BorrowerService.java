package com.gcit.lms.service;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.gcit.lms.dao.BookDAO;
import com.gcit.lms.dao.BorrowerDAO;
import com.gcit.lms.entity.Book;
import com.gcit.lms.entity.Borrower;

@RestController
public class BorrowerService {
	
	@Autowired
	BookDAO bdao;
	
	@Autowired
	BorrowerDAO bodao;

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

}
