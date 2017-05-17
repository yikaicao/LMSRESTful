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
import org.springframework.web.bind.annotation.RestController;

import com.gcit.lms.dao.AuthorDAO;
import com.gcit.lms.dao.BookDAO;
import com.gcit.lms.entity.Author;
import com.gcit.lms.entity.Book;

@RestController
public class AdminService {

	@Autowired
	BookDAO bdao;

	@Autowired
	AuthorDAO adao;
	
	@RequestMapping(value = "/initAuthor", method = RequestMethod.GET, produces="application/json")
	public Author initAuthor() {
		return new Author();
	}

	@Transactional
	@RequestMapping(value = "/addAuthor", method = RequestMethod.POST, consumes="application/json")
	public void addAuthor(@RequestBody Author author) {
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

	@Transactional
	public void addBook(Book book) {

		try {
			Integer bookId = bdao.addBookWithID(book);
			if (book.getAuthors() != null && !book.getAuthors().isEmpty()) {
				for (Author a : book.getAuthors()) {
					bdao.addBookAuthors(book.getBookId(), a.getAuthorId());
				}
			}
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
	
	@RequestMapping(value = "/searchAuthors/{authorName}", method = RequestMethod.GET, produces="application/json")
	public List<Author> getAuthorsByName(@PathVariable String authorName) {

		try {
			return adao.readAuthorsByName(authorName);
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	@RequestMapping(value = "/getAuthorByPK/{authorId}", method = RequestMethod.GET, produces="application/json")
	public Author getAuthorByPk(@PathVariable Integer authorId) {

		try {
			return adao.readAuthorByID(authorId);
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	// public Integer getAuthorsCount() {
	// Connection conn = null;
	// try {
	// conn = ConnectionUtil.getConnection();
	// return adao.getAuthorsCount();
	// } catch (ClassNotFoundException | SQLException e) {
	// e.printStackTrace();
	// } finally{
	// if(conn!=null){
	// conn.close();
	// }
	// }
	// return null;
	// }

}
