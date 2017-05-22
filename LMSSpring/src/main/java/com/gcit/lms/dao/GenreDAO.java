package com.gcit.lms.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import com.gcit.lms.entity.Genre;

public class GenreDAO extends BaseDAO implements ResultSetExtractor<List<Genre>> {

	public List<Genre> readAllGenres() {
		return template.query("select * from tbl_genre", this);
	}

	public List<Genre> readAllGenresByBookID(Integer bookId) {
		return template.query(
				"select * from tbl_genre where genre_id IN (Select genre_id from tbl_book_genres where bookId = ?)",
				new Object[] { bookId }, this);
	}

	@Override
	public List<Genre> extractData(ResultSet rs) throws SQLException, DataAccessException {
		List<Genre> genres = new ArrayList<>();
		while (rs.next()) {
			Genre g = new Genre();
			g.setGenreId(rs.getInt("genre_id"));
			g.setGenreName(rs.getString("genre_name"));
			genres.add(g);
		}
		return genres;
	}

}
