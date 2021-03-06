package com.gcit.lms.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gcit.lms.dao.BookCopyDAO;
import com.gcit.lms.dao.BookDAO;
import com.gcit.lms.dao.BranchDAO;
import com.gcit.lms.entity.Book;
import com.gcit.lms.entity.BookCopy;
import com.gcit.lms.entity.Branch;

@RestController
public class LibrarianService {

	@Autowired
	BookDAO bdao;

	@Autowired
	BranchDAO brdao;

	@Autowired
	BookCopyDAO bcdao;

	// %%%%%%%%%% branch services %%%%%%%%%%

	@RequestMapping(value = "/branches", method = RequestMethod.GET, produces = "application/json")
	public List<Branch> readBranches(@RequestParam(value = "pageNo", required = false) Integer pageNo,
			@RequestParam(value = "searchString", required = false) String searchString) {
		List<Branch> branches = new ArrayList<>();
		try {
			branches = brdao.readAllBranches();
			return branches;
		} catch (DataAccessException e) {
			e.printStackTrace();
		}
		return null;
	}

	@RequestMapping(value = "/branches/{primaryKey}", method = RequestMethod.GET, produces = "application/json")
	public Branch readBranch(@PathVariable Integer primaryKey) {
		try {
			return brdao.readBranchByPK(primaryKey);
		} catch (DataAccessException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Transactional
	@RequestMapping(value = "/branches", method = RequestMethod.PUT)
	public void updateAuthor(@RequestBody Branch branch) {
		brdao.updateBranch(branch);
	}

	// %%%%%%%%%% book copy services %%%%%%%%%%

	@Transactional
	@RequestMapping(value = "/bookcopies", method = RequestMethod.POST, consumes = "application/json")
	public void createAuthor(@RequestBody BookCopy bc) {
		bcdao.addBookCopy(bc);
	}

	@RequestMapping(value = "/bookcopies/{branchId}", method = RequestMethod.GET, produces = "application/json")
	public List<BookCopy> readBookCopy(@PathVariable Integer branchId) {
		return bcdao.readBookCopyByBranchID(branchId);
	}

	@Transactional
	@RequestMapping(value = "/bookcopies", method = RequestMethod.PUT)
	public void updateBook(@RequestBody BookCopy bc) {
		bcdao.updateBookCopy(bc);
	}

	// %%%%%%%%%% book services %%%%%%%%%%

	@RequestMapping(value = "/missedbooks/{branchId}", method = RequestMethod.GET, produces = "application/json")
	public List<Book> readBook(@PathVariable Integer branchId) {
		return bdao.readAllBooksNotAtBranch(branchId);
	}
}
