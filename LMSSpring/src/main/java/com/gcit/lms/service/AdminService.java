package com.gcit.lms.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.gcit.lms.dao.AuthorDAO;
import com.gcit.lms.dao.BookDAO;
import com.gcit.lms.entity.Author;

/**
 * RESTful API services to provide administrative queries requested by web
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

	@RequestMapping(value = "/viewAuthors/{pageNo}/{searchString}", method = RequestMethod.GET, produces = "application/json")
	public List<Author> viewAuthors(@PathVariable Integer pageNo, @PathVariable String searchString) {

		List<Author> authors = new ArrayList<>();

		try {
			if (searchString != null && searchString.length() > 0) {
				authors = adao.readAuthorsByName(searchString);
			} else {
				authors = adao.readAllAuthors(pageNo);
			}

			for (Author a : authors) {
				a.setBooks(bdao.readAllBooksByAuthorID(a.getAuthorId()));
			}

			return authors;
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}

		return null;
	}

	@Transactional
	@RequestMapping(value = "/addAuthor", method = RequestMethod.POST, consumes = "application/json")
	public void addAuthor(Author author) {
		try {
			adao.addAuthor(author);
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Transactional
	public void editAuthor(Author author) {

		try {
			adao.updateAuthor(author);
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public List<Author> getAllAuthors(Integer pageNo) {

		List<Author> authors = new ArrayList<>();
		try {
			authors = adao.readAllAuthors(pageNo);
			for (Author a : authors) {
				a.setBooks(bdao.readAllBooksByAuthorID(a.getAuthorId()));
			}
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return authors;
	}

	public List<Author> getAuthorsByName(Integer pageNo, String authorName) {

		try {
			return adao.readAuthorsByName(authorName);
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public Author getAuthorByPk(Integer authorId) {

		try {
			return adao.readAuthorByID(authorId);
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

}
