package com.gcit.lms.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gcit.lms.dao.AuthorDAO;
import com.gcit.lms.dao.BookDAO;
import com.gcit.lms.entity.Author;

/**
 * Using spring to handle http requests and specify what to return.
 * 
 * Support the following http requests,
 * 
 * GET: /authors?{pageNo}{searchString} GET: /authors/{primaryKey}
 *
 * POST: /authors?
 * 
 * @author yikaicao
 *
 */

@RestController
public class AdminService {

	@Autowired
	BookDAO bdao;

	@Autowired
	AuthorDAO adao;

	@RequestMapping(value = "/initAuthor", method = RequestMethod.GET, produces = "application/json")
	public Author initAuthor() {
		return new Author();
	}

	@Transactional
	@RequestMapping(value = "/addAuthor", method = RequestMethod.POST, consumes = "application/json")
	public void createAuthor(@RequestBody Author author) {
		try {
			adao.addAuthor(author);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
	}

	@RequestMapping(value = "/authors", method = RequestMethod.GET, produces = "application/json")
	public List<Author> readAuthors(@RequestParam(value = "pageNo", required = false) Integer pageNo,
			@RequestParam(value = "searchString", required = false) String searchString) {
		List<Author> authors = new ArrayList<>();
		try {
			authors = adao.readAllAuthors();
			for (Author a : authors) {
				a.setBooks(bdao.readAllBooksByAuthorID(a.getAuthorId()));
			}
			return authors;
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@RequestMapping(value = "/authors/{primaryKey}", method = RequestMethod.GET, produces = "application/json")
	public Author readAuthor(@PathVariable Integer primaryKey) {
		try {
			return adao.readAuthorByID(primaryKey);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@RequestMapping(value = "/authors", method = RequestMethod.PUT)
	public void updateAuthor(@RequestBody Author author) {
		try {
			adao.updateAuthor(author);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
	}

	@RequestMapping(value = "/authors", method = RequestMethod.DELETE, consumes = "application/json")
	public void deleteAuthor(@RequestBody Author author) {
		System.out.println("got the request!");
		try {
			adao.deleteAuthor(author);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
	}
}
