package com.gcit.lms.dao;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Component;

import com.gcit.lms.entity.Author;
import com.gcit.lms.entity.Book;

@Component
public class AuthorDAO extends BaseDAO<Author> implements ResultSetExtractor<List<Author>>{
	

	public void addAuthor(Author author) throws SQLException, ClassNotFoundException
	{
		mySqlTemplate.update("insert into tbl_author (authorName) values(?)",new Object[] {author.getAuthorName()});
	}
	
	public Integer addAuthorWithId(Author author) throws SQLException, ClassNotFoundException
	{
		String insertSql="insert into tbl_author (authorName) values(?)";
		GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
		String id_column = "authorId";
		mySqlTemplate.update(con -> {
			PreparedStatement ps = con.prepareStatement(insertSql, new String[] { id_column });
			ps.setString(1, author.getAuthorName());
			return ps;
		}, keyHolder);
		
//		Logger.info("Id is {}", keyHolder.getKey().intValue());
		return keyHolder.getKey().intValue();
	}
	
	public void updateAuthor(Author author) throws SQLException, ClassNotFoundException
	{
		mySqlTemplate.update("update tbl_author set authorName = ? where authorId = ?",new Object[] {author.getAuthorName(),author.getAuthorId()});
		
	}
	
	public void deleteAuthor(Author author) throws ClassNotFoundException, SQLException
	{
		
		mySqlTemplate.update("delete from tbl_author where authorId=?",new Object[] {author.getAuthorId()});	
	}
	
	public List<Author> ReadAllAuthors() throws ClassNotFoundException, SQLException
	{
		
		return mySqlTemplate.query("select * from tbl_author",this);	
	}
	
	public List<Author> readAuthorsByName(String authorName) throws ClassNotFoundException, SQLException
	{
		authorName="%"+authorName+"%";
		
		return mySqlTemplate.query("select * from tbl_author where authorName like ?",new Object[] {authorName},this);	
	}
	
	public List<Author> ReadAuthorsByBookId(Integer bookId) throws ClassNotFoundException, SQLException 
	{
		return mySqlTemplate.query("select * from tbl_author where authorId in(select authorId from tbl_book_authors where bookId=?)",new Object[] {bookId},this);	
	}
	
	
	public Author readAuthorsById(Integer authorId) throws ClassNotFoundException, SQLException
	{
		List<Author> authors= mySqlTemplate.query("select * from tbl_author where authorId = ?",new Object[] {authorId},this);
		if(authors!=null)
		{
			return authors.get(0);
		}
		return null;
	}
	
	public List<Author> extractData(ResultSet rs) throws SQLException
	{
		List<Author> authors=new ArrayList<>();
		while(rs.next()){
			Author author=new Author();
			author.setAuthorId(rs.getInt("authorId"));
			author.setAuthorName(rs.getString("authorName"));
			authors.add(author);
		}
		return authors;
	}
	
	
	
	

}