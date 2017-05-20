package com.gcit.lms.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.springframework.jdbc.core.ResultSetExtractor;
import com.gcit.lms.entity.Book;

public class BookDAO extends BaseDAO implements ResultSetExtractor<List<Book>> {

	public void addBook(Book book) throws ClassNotFoundException, SQLException {
		template.update("insert into tbl_book (title) values (?)", new Object[] { book.getTitle() });
	}

	public Integer addBookWithID(Book book) throws ClassNotFoundException, SQLException {
		return template.update("insert into tbl_book (title) values (?)", new Object[] { book.getTitle() });
	}

	public void addBookAuthors(Integer bookId, Integer authorId) throws ClassNotFoundException, SQLException {
		template.update("insert into tbl_book_authors values (?, ?)", new Object[] { bookId, authorId });
	}

	public void updateBook(Book book) throws ClassNotFoundException, SQLException {
		template.update("update tbl_book set title = ? where bookId = ?",
				new Object[] { book.getTitle(), book.getBookId() });
	}

	public void deleteBook(Book book) throws ClassNotFoundException, SQLException {
		template.update("delete * from tbl_book where bookId = ?", new Object[] { book.getBookId() });
	}

	public List<Book> readAllBooksByAuthorID(Integer authorId) {
		return template.query(
				"select * from tbl_book where bookId IN (Select bookId from tbl_book_authors where authorId = ?)",
				new Object[] { authorId }, this);
	}

	@Override
	public List<Book> extractData(ResultSet rs) throws SQLException {
		List<Book> books = new ArrayList<>();
		while (rs.next()) {
			Book b = new Book();
			b.setTitle(rs.getString("title"));
			b.setBookId(rs.getInt("bookId"));
			books.add(b);
		}
		return books;
	}

	public List<Book> readAllBook() throws ClassNotFoundException, SQLException {
		return template.query("select * from tbl_book", this);
	}
}
