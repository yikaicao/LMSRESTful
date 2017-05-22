package com.gcit.lms.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementCreatorFactory;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import com.gcit.lms.entity.Book;
import com.gcit.lms.entity.Genre;

public class BookDAO extends BaseDAO implements ResultSetExtractor<List<Book>> {

	public void addBook(Book book) throws ClassNotFoundException, SQLException {
		String sql = "insert into tbl_book (`title`) values (?)";
		Object[] params = new Object[] { book.getTitle() };
		PreparedStatementCreatorFactory factory = new PreparedStatementCreatorFactory(sql, new int[] { Types.VARCHAR });
		PreparedStatementCreator psc = factory.newPreparedStatementCreator(params);
		factory.setReturnGeneratedKeys(true);

		KeyHolder keyHolder = new GeneratedKeyHolder();
		template.update(psc, keyHolder);

		if (keyHolder.getKey() != null)
			for (Genre g : book.getGenres()) {
				template.update("insert into tbl_book_genres (genre_id, bookId) values (?, ?)",
						new Object[] { g.getGenreId(), keyHolder.getKey() });
			}
		else
			throw new SQLException("BookDAO(): no generated key returned.");
	}

	public Integer addBookWithID(Book book) throws ClassNotFoundException, SQLException {
		return template.update("insert into tbl_book (title) values (?)", new Object[] { book.getTitle() });
	}

	public void addBookAuthors(Integer bookId, Integer authorId) throws ClassNotFoundException, SQLException {
		template.update("insert into tbl_book_authors values (?, ?)", new Object[] { bookId, authorId });
	}

	public List<Book> readAllBooks() throws DataAccessException {
		return template.query("select * from tbl_book", this);
	}

	public Book readBookByID(Integer primaryKey) throws DataAccessException {
		List<Book> books = template.query("select * from tbl_book where bookId = ?", new Object[] { primaryKey }, this);
		if (books != null && !books.isEmpty()) {
			return books.get(0);
		}
		return null;
	}

	public List<Book> readAllBooksByAuthorID(Integer authorId) {
		return template.query(
				"select * from tbl_book where bookId IN (Select bookId from tbl_book_authors where authorId = ?)",
				new Object[] { authorId }, this);
	}

	public void updateBook(Book book) throws ClassNotFoundException, SQLException {
		template.update("update tbl_book set title = ? where bookId = ?",
				new Object[] { book.getTitle(), book.getBookId() });

		if (book.getGenres() != null && !book.getGenres().isEmpty()) {
			template.update("delete from tbl_book_genres where bookId = ?", new Object[] { book.getBookId() });
			for (Genre g : book.getGenres()) {
				template.update("insert into tbl_book_genres (`genre_id`, `bookId`) values (?, ?)",
						new Object[] { g.getGenreId(), book.getBookId() });
			}
		}
	}

	public void deleteBook(Book book) throws ClassNotFoundException, SQLException {
		template.update("delete * from tbl_book where bookId = ?", new Object[] { book.getBookId() });
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

}
