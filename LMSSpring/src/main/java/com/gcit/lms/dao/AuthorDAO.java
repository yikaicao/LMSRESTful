package com.gcit.lms.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.jdbc.core.ResultSetExtractor;

import com.gcit.lms.entity.Author;

/**
 * This is a DAO
 * @author ppradhan
 *
 */
public class AuthorDAO extends BaseDAO implements ResultSetExtractor<List<Author>>{
	
	public final String AUTH_COLLECTION = "Authors";
	
	public void addAuthor(Author author) throws ClassNotFoundException, SQLException{
		template.update("insert into tbl_author (authorName) values (?)", new Object[] {author.getAuthorName()});
//		author.setAuthorId((int)seqDao.getNextSequenceId(AUTH_COLLECTION));
//		mongoTemplate.save(author, AUTH_COLLECTION);
	}
	
	public void updateAuthor(Author author) throws ClassNotFoundException, SQLException{
		template.update("update tbl_author set authorName = ? where authorId = ?", new Object[]{author.getAuthorName(), author.getAuthorId()});
		
//		Query query = new Query();
//		query.addCriteria(Criteria.where("authorName").regex("New author"));
////		query.addCriteria(criteriaDefinition)
//		
//		Update update = new Update();
//		update.set("authorName", author.getAuthorName());
//		
//		mongoTemplate.updateMulti(query, update, AUTH_COLLECTION);
		
	}
	
	public void deleteAuthor(Author author) throws ClassNotFoundException, SQLException{
		template.update("delete * from tbl_author where authorId = ?", new Object[] {author.getAuthorId()});
	}
	
	public List<Author> readAllAuthors(Integer pageNo) throws ClassNotFoundException, SQLException{
		setPageNo(pageNo);
		return template.query("select * from tbl_author", this);
//		return mongoTemplate.findAll(Author.class);
	}
	
	public Author readAuthorByID(Integer authorID) throws ClassNotFoundException, SQLException{
		List<Author> authors = template.query("select * from tbl_author where authorId = ?", new Object[]{authorID}, this);
		if(authors!=null && !authors.isEmpty()){
			return authors.get(0);
		}
		return null;
	}
	
	public List<Author> readAuthorsByName(String  authorName) throws ClassNotFoundException, SQLException{
		authorName = "%"+authorName+"%";
		return template.query("select * from tbl_author where authorName like ?", new Object[]{authorName}, this);
	}
	
//	public Integer getAuthorsCount() throws ClassNotFoundException, SQLException{
//		return template.query("select count(*) as COUNT from tbl_author", null);
//	}

	@Override
	public List<Author> extractData(ResultSet rs) throws SQLException {
		List<Author> authors = new ArrayList<>();
		while(rs.next()){
			Author a = new Author();
			a.setAuthorId(rs.getInt("authorId"));
			a.setAuthorName(rs.getString("authorName"));
			authors.add(a);
		}
		return authors;
	}
}
