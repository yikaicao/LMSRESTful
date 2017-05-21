package com.gcit.lms.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.springframework.jdbc.core.ResultSetExtractor;
import com.gcit.lms.entity.Author;

/**
 * Receives calls from service layer and access database.
 * 
 * @note pagination is handled at front end by javascript
 * @author yikaicao
 *
 */

public class AuthorDAO extends BaseDAO implements ResultSetExtractor<List<Author>> {

	public void addAuthor(Author author) throws ClassNotFoundException, SQLException {
		template.update("insert into tbl_author (authorName) values (?)", new Object[] { author.getAuthorName() });
	}

	public List<Author> readAllAuthors() throws ClassNotFoundException, SQLException {
		return template.query("select * from tbl_author", this);
	}

	public Author readAuthorByID(Integer authorID) throws ClassNotFoundException, SQLException {
		List<Author> authors = template.query("select * from tbl_author where authorId = ?", new Object[] { authorID },
				this);
		if (authors != null && !authors.isEmpty()) {
			return authors.get(0);
		}
		return null;
	}

	public List<Author> readAuthorsByName(String authorName) throws ClassNotFoundException, SQLException {
		authorName = "%" + authorName + "%";
		return template.query("select * from tbl_author where authorName like ?", new Object[] { authorName }, this);
	}

	public List<Author> readAllAuthorsByBookID(Integer bookId) {
		return template.query(
				"select * from tbl_author where authorId IN (Select authorId from tbl_book_authors where bookId = ?)",
				new Object[] { bookId }, this);
	}

	public void updateAuthor(Author author) throws ClassNotFoundException, SQLException {
		template.update("update tbl_author set authorName = ? where authorId = ?",
				new Object[] { author.getAuthorName(), author.getAuthorId() });
	}

	public void deleteAuthor(Author author) throws ClassNotFoundException, SQLException {
		template.update("delete from tbl_author where authorId = ?", new Object[] { author.getAuthorId() });
	}

	public void deleteAuthorByPK(Integer primaryKey) throws ClassNotFoundException, SQLException {
		template.update("delete from tbl_author where authorId = ?", new Object[] { primaryKey });
	}

	/**
	 * extractData: only extract first layer of the data.
	 */
	@Override
	public List<Author> extractData(ResultSet rs) throws SQLException {
		List<Author> authors = new ArrayList<>();
		while (rs.next()) {
			Author a = new Author();
			a.setAuthorId(rs.getInt("authorId"));
			a.setAuthorName(rs.getString("authorName"));
			authors.add(a);
		}
		return authors;
	}

}
