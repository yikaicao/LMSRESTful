package com.gcit.lms.service;

import java.sql.SQLException;
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

import com.gcit.lms.dao.AuthorDAO;
import com.gcit.lms.dao.BookDAO;
import com.gcit.lms.dao.BookLoanDAO;
import com.gcit.lms.dao.GenreDAO;
import com.gcit.lms.dao.PublisherDAO;
import com.gcit.lms.entity.Author;
import com.gcit.lms.entity.Book;
import com.gcit.lms.entity.BookLoan;
import com.gcit.lms.entity.Genre;
import com.gcit.lms.entity.Publisher;

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

	@Autowired
	PublisherDAO pdao;

	@Autowired
	GenreDAO gdao;

	@Autowired
	BookLoanDAO bldao;

	// %%%%%%%%%% author services %%%%%%%%%%

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
			if (searchString == null)
				authors = adao.readAllAuthors();
			else
				authors = adao.readAuthorsByName(searchString);
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

	@Transactional
	@RequestMapping(value = "/authors", method = RequestMethod.PUT)
	public void updateAuthor(@RequestBody Author author) {
		try {
			adao.updateAuthor(author);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
	}

	@Transactional
	@RequestMapping(value = "/authors/{primaryKey}", method = RequestMethod.DELETE)
	public void deleteAuthor(@PathVariable Integer primaryKey) {
		try {
			adao.deleteAuthorByPK(primaryKey);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
	}

	// %%%%%%%%%% book services %%%%%%%%%%

	@RequestMapping(value = "/initBook", method = RequestMethod.GET, produces = "application/json")
	public Book initBook() {
		return new Book();
	}

	@Transactional
	@RequestMapping(value = "/addBook", method = RequestMethod.POST, consumes = "application/json")
	public void createBook(@RequestBody Book book) {
		try {
			bdao.addBook(book);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
	}

	@RequestMapping(value = "/books", method = RequestMethod.GET, produces = "application/json")
	public List<Book> readBooks(@RequestParam(value = "pageNo", required = false) Integer pageNo,
			@RequestParam(value = "searchString", required = false) String searchString) {
		List<Book> books = new ArrayList<>();
		if (searchString == null)
			books = bdao.readAllBooks();
		else
			books = bdao.readAllBooksByName(searchString);
		for (Book b : books) {
			b.setAuthors(adao.readAllAuthorsByBookID(b.getBookId()));
			b.setGenres(gdao.readAllGenresByBookID(b.getBookId()));
		}
		return books;
	}

	@RequestMapping(value = "/books/{primaryKey}", method = RequestMethod.GET, produces = "application/json")
	public Book readBook(@PathVariable Integer primaryKey) {
		try {
			Book toReturn = bdao.readBookByID(primaryKey);
			toReturn.setGenres(gdao.readAllGenresByBookID(toReturn.getBookId()));
			toReturn.setAuthors(adao.readAllAuthorsByBookID(toReturn.getBookId()));
			return toReturn;
		} catch (DataAccessException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Transactional
	@RequestMapping(value = "/books", method = RequestMethod.PUT)
	public void updateBook(@RequestBody Book book) {
		try {
			bdao.updateBook(book);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
	}

	@Transactional
	@RequestMapping(value = "/books/{primaryKey}", method = RequestMethod.DELETE)
	public void deleteBook(@PathVariable Integer primaryKey) {
		try {
			bdao.delteBookByPK(primaryKey);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
	}

	// %%%%%%%%%% publisher services %%%%%%%%%%

	@RequestMapping(value = "/publishers", method = RequestMethod.GET, produces = "application/json")
	public List<Publisher> readPublishers(@RequestParam(value = "pageNo", required = false) Integer pageNo,
			@RequestParam(value = "searchString", required = false) String searchString) {
		return pdao.readAllPublishers();
	}

	@RequestMapping(value = "/publishers/{publisherId}", method = RequestMethod.GET, produces = "application/json")
	public Publisher readPublisher(@PathVariable Integer publisherId) {
		return pdao.readPublisherByPK(publisherId);
	}

	@Transactional
	@RequestMapping(value = "/publishers", method = RequestMethod.PUT)
	public void updatePublisher(@RequestBody Publisher publisher) {
		pdao.updatePublisher(publisher);
	}
	
	@Transactional
	@RequestMapping(value = "/publishers/{primaryKey}", method = RequestMethod.DELETE)
	public void deletePublisher(@PathVariable Integer primaryKey) {
		pdao.deltePublisherByPK(primaryKey);
	}

	// %%%%%%%%%% genre services %%%%%%%%%%
	@RequestMapping(value = "/initGenre", method = RequestMethod.GET, produces = "application/json")
	public Genre initGenre() {
		return new Genre();
	}

	@RequestMapping(value = "/genres", method = RequestMethod.GET, produces = "application/json")
	public List<Genre> readGenres() {
		return gdao.readAllGenres();
	}

	// %%%%%%%%%% book loan services %%%%%%%%%%

	@RequestMapping(value = "/bookloans/{branchId}", method = RequestMethod.GET, produces = "application/json")
	public List<BookLoan> readBookLoans(@PathVariable Integer branchId) {
		return bldao.readBookLoansAtBranch(branchId);
	}

	@Transactional
	@RequestMapping(value = "/bookloans", method = RequestMethod.PUT)
	public void updateBookLoan(@RequestBody BookLoan bl) {
		bldao.updateBookLoanDueDate(bl);
	}

	@Transactional
	@RequestMapping(value = "/extendduedate/{branchId}/{bookId}/{cardNo}", method = RequestMethod.PUT)
	public void extendduedate(@PathVariable Integer branchId, @PathVariable Integer bookId,
			@PathVariable Integer cardNo) {
		bldao.extendDueDate(branchId, bookId, cardNo);
	}

}
