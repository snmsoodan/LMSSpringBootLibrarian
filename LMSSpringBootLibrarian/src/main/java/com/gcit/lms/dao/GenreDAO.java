package com.gcit.lms.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Component;

import com.gcit.lms.entity.Book;
import com.gcit.lms.entity.Genre;

@Component
public class GenreDAO extends BaseDAO<Genre> implements ResultSetExtractor<List<Genre>>{
	
	
	public Integer addGenreWithId(Genre genre) throws SQLException, ClassNotFoundException
	{
		String insertSql="insert into tbl_genre (genre_name) values(?)";
		GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
		String id_column = "genreId";
		mySqlTemplate.update(con -> {
			PreparedStatement ps = con.prepareStatement(insertSql, new String[] { id_column });
			ps.setString(1, genre.getGenreName());
			return ps;
		}, keyHolder);
		
		return keyHolder.getKey().intValue();
	}
	

	public void addGenre(Genre genre) throws SQLException, ClassNotFoundException
	{
		mySqlTemplate.update("insert into tbl_genre (genre_name) values(?)",new Object[] {genre.getGenreName()});
	}
	
	public void updateGenre(Genre genre) throws SQLException, ClassNotFoundException
	{
		mySqlTemplate.update("update tbl_genre set genre_name = ? where genre_id = ?",new Object[] {genre.getGenreName(),genre.getGenreId()});
		
	}
	
	public void deleteGenre(Genre genre) throws ClassNotFoundException, SQLException
	{
		
		mySqlTemplate.update("delete from tbl_genre where genre_id=?",new Object[] {genre.getGenreId()});	
	}
	
	public List<Genre> ReadAllGenres() throws ClassNotFoundException, SQLException
	{
		
		return mySqlTemplate.query("select * from tbl_genre",this);	
	}
	
	public List<Genre> ReadGenresByBookId(Integer bookId) throws ClassNotFoundException, SQLException 
	{
		return mySqlTemplate.query("select * from tbl_genre where genre_id in(select genre_id from tbl_book_genres where bookId=?)",new Object[] {bookId},this);	
	}
	
	public List<Genre> readGenresByName(String name) throws ClassNotFoundException, SQLException
	{
		name="%"+name+"%";
		
		return mySqlTemplate.query("select * from tbl_genre where genre_name like ?",new Object[] {name},this);	
	}
	

	public List<Genre> extractData(ResultSet rs) throws SQLException
	{
		
		List<Genre> genres=new ArrayList<>();
		while(rs.next()){
			Genre genre=new Genre();
			genre.setGenreId(rs.getInt("genre_id"));
			genre.setGenreName(rs.getString("genre_name"));
			genres.add(genre);
		}
		return genres;
	}
	

	
	

}