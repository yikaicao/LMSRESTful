package com.gcit.lms.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.gcit.lms.dao.AuthorDAO;
import com.gcit.lms.dao.BookDAO;
import com.gcit.lms.entity.Author;

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

	@RequestMapping(value = "/viewAuthors/{pageNo}", method = RequestMethod.GET, produces = "application/json")
	public List<Author> viewAuthors(@PathVariable Integer pageNo) {
		List<Author> authors = new ArrayList<>();
		try {
			authors = adao.readAllAuthors(pageNo);
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
	public void addAuthor(@RequestBody Author author) {
		try {
			adao.addAuthor(author);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
	}

	@Transactional
	@RequestMapping(value = "/updateAuthor", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public List<Author> updateAuthor(@RequestBody Author author) {
		try {
			adao.updateAuthor(author);
			return adao.readAllAuthors(1);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@RequestMapping(value = {"/searchAuthors/", "/searchAuthors/{authorName}"}, method = RequestMethod.GET, produces = "application/json")
	public List<Author> getAuthorsByName(@PathVariable Optional<String> authorName) {

		try {
			if (authorName.isPresent())
				return adao.readAuthorsByName(authorName.get());
			return adao.readAllAuthors(1);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@RequestMapping(value = "/getAuthorByPK/{authorId}", method = RequestMethod.GET, produces = "application/json")
	public Author getAuthorByPk(@PathVariable Integer authorId) {

		try {
			return adao.readAuthorByID(authorId);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

}
