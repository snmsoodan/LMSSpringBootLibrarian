package com.gcit.lms.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Component;

import com.gcit.lms.entity.BookCopies;

@Component
public class BookCopiesDAO extends BaseDAO<BookCopies> implements ResultSetExtractor<List<BookCopies>>{


	public void addBookCopies(BookCopies bookCopy) throws ClassNotFoundException, SQLException
	{
		mySqlTemplate.update("insert into tbl_book_copies  values(?,?,?)",new Object[] {bookCopy.getBookId(),bookCopy.getBranchId(),bookCopy.getNoOfCopies()});
	}

	
	public List<BookCopies> ReadBookCopiesById(Integer bookId,Integer branchId) throws ClassNotFoundException, SQLException
	{
		
		return mySqlTemplate.query("select * from tbl_book_copies where bookId=? and branchId=?",new Object[] {bookId,branchId},this);	
	}
	
	public void updateBookCopies(BookCopies bookCopy) throws SQLException, ClassNotFoundException
	{
		mySqlTemplate.update("update tbl_book_copies set noOfCopies = ? where bookId = ? and branchId=?",new Object[] {bookCopy.getNoOfCopies(),bookCopy.getBookId(),bookCopy.getBranchId()});
		
	} 
	
	
	public void LoanBookCopies(BookCopies bookCopy) throws SQLException, ClassNotFoundException // both to Loan And Return
	{
		mySqlTemplate.update("update tbl_book_copies set noOfCopies = ? where bookId = ? and branchId=?",new Object[] {bookCopy.getNoOfCopies(),bookCopy.getBookId(),bookCopy.getBranchId()});
		
	}  
	

	@Override
	public List<BookCopies> extractData(ResultSet rs) throws SQLException {
		List<BookCopies> bookCopies=new ArrayList<>();
		while(rs.next()){
		
			BookCopies bookCopy = new BookCopies();
			bookCopy.setBookId(rs.getInt("bookId"));
			bookCopy.setBranchId(rs.getInt("branchId"));
			bookCopy.setNoOfCopies(rs.getInt("noOfCopies"));
			
			bookCopies.add(bookCopy);
		}
		return bookCopies;
	}
	
	

}
