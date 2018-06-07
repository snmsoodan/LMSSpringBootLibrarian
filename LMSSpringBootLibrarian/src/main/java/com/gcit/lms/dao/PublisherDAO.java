package com.gcit.lms.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Component;

import com.gcit.lms.entity.Genre;
import com.gcit.lms.entity.Publisher;

@Component
public class PublisherDAO extends BaseDAO<Publisher> implements ResultSetExtractor<List<Publisher>>{
	
	public void addPublisher(Publisher publisher) throws SQLException, ClassNotFoundException
	{
		mySqlTemplate.update("insert into tbl_publisher (publisherName,publisherAddress,publisherPhone) values(?,?,?)",new Object[] {publisher.getPublisherName(),publisher.getPublisherAddress(),publisher.getPublisherPhone()});
	}
	
	public void updatePublisher(Publisher publisher) throws SQLException, ClassNotFoundException
	{
		mySqlTemplate.update("update tbl_publisher set publisherName = ?,publisherAddress=?,publisherPhone=? where publisherId = ?",new Object[] {publisher.getPublisherName(),publisher.getPublisherAddress(),publisher.getPublisherPhone(),publisher.getPublisherId()});
		
	}
	
	public void deletePublisher(Publisher publisher) throws ClassNotFoundException, SQLException
	{
		
		mySqlTemplate.update("delete from tbl_publisher where publisherId=?",new Object[] {publisher.getPublisherId()});	
	}
	
	public List<Publisher> ReadAllPublishers() throws ClassNotFoundException, SQLException
	{
		
		return mySqlTemplate.query("select * from tbl_publisher",this);	
	}
	
	public List<Publisher> ReadAllPublishersByName(String name) throws ClassNotFoundException, SQLException
	{
		name="%"+name+"%";
		return mySqlTemplate.query("select * from tbl_publisher where publisherName like ?",new Object[] {name},this);	
	}
	
	
	public List<Publisher> ReadPublisherByBookId(Integer bookId) throws ClassNotFoundException, SQLException 
	{
		return mySqlTemplate.query("select * from tbl_publisher where publisherId in(select pubId from tbl_book where bookId=?)",new Object[] {bookId},this);	
	}
	
	public List<Publisher> extractData(ResultSet rs) throws SQLException
	{
		List<Publisher> publishers=new ArrayList<>();
		while(rs.next()){
			Publisher publisher=new Publisher();
			publisher.setPublisherId(rs.getInt("publisherId"));
			publisher.setPublisherName(rs.getString("publisherName"));
			publisher.setPublisherAddress(rs.getString("publisherAddress"));
			publisher.setPublisherPhone(rs.getString("publisherPhone"));
			publishers.add(publisher);
		}
		return publishers;
	}
	

}