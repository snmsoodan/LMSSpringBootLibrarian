package com.gcit.lms.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Component;

import com.gcit.lms.entity.BookLoans;

@Component
public class BookLoansDAO extends BaseDAO<BookLoans> implements ResultSetExtractor<List<BookLoans>>{

	
	public void addBookLoans(BookLoans bookLoans) throws ClassNotFoundException, SQLException
	{
		mySqlTemplate.update("insert into tbl_book_loans (bookId,branchId,cardNo,dateOut,dueDate)  values(?,?,?,?,?)",new Object[] {bookLoans.getBookId(),bookLoans.getBranchId(),bookLoans.getCardNo(),bookLoans.getDateOut(),bookLoans.getDueDate()});
	}
	
	
	public void returnBookLoans(BookLoans bookLoans) throws ClassNotFoundException, SQLException
	{
		mySqlTemplate.update("update tbl_book_loans set dateIn=? where bookId=? and branchId=? and cardNo=? and dateOut=?",new Object[] {bookLoans.getDateIn(),bookLoans.getBookId(),bookLoans.getBranchId(),bookLoans.getCardNo(),bookLoans.getDateOut()});
	}
	
	
	public List<BookLoans> ReadBookLoansById(Integer bookId,Integer branchId,Integer cardNo) throws ClassNotFoundException, SQLException
	{
		
		return mySqlTemplate.query("select * from tbl_book_loans where bookId= ? and branchId= ? and cardNo= ?",new Object[] {bookId,branchId,cardNo},this);	
	}
	
	public List<BookLoans> ReadBookLoansByUserId(Integer cardNo) throws ClassNotFoundException, SQLException
	{
		
		return mySqlTemplate.query("select * from tbl_book_loans where cardNo= ?",new Object[] {cardNo},this);	
	}
	
	
	public List<BookLoans> ReadBookLoansByUser(Integer cardNo) throws ClassNotFoundException, SQLException
	{
		
		return mySqlTemplate.query("select * from tbl_book_loans where cardNo= ? and dateIn is NULL",new Object[] {cardNo},this);	
	}
	
	public List<BookLoans> ReadAllBookLoans() throws ClassNotFoundException, SQLException
	{
		return mySqlTemplate.query("select * from tbl_book_loans where dateIn is null;",this);	
	}
	
	public void changeDueDate(BookLoans bookLoan) throws ClassNotFoundException, SQLException
	{
//		System.out.println(bookLoan.getDueDate());
		mySqlTemplate.update("update tbl_book_loans set dueDate=? where bookId=? and branchId=? and cardNo=?",new Object[] {bookLoan.getDueDate(),bookLoan.getBookId(),bookLoan.getBranchId(),bookLoan.getCardNo()});
	}

	@Override
	public List<BookLoans> extractData(ResultSet rs) throws SQLException {
		List<BookLoans> bookLoans=new ArrayList<>();
		while(rs.next()){
		
			BookLoans bookLoan = new BookLoans();
			bookLoan.setBookId(rs.getInt("bookId"));
			bookLoan.setBranchId(rs.getInt("branchId"));
			bookLoan.setCardNo(rs.getInt("cardNo"));
			bookLoan.setDateOut(rs.getString("dateOut"));
			bookLoan.setDueDate(rs.getString("dueDate"));
			bookLoan.setDateIn(rs.getString("dateIn"));
			
			
//			bookLoan.setDateOut(rs.getDate("dateOut"));
//			bookLoan.setDueDate(rs.getDate("dueDate"));
//			bookLoan.setDateIn(rs.getDate("dateIn"));
			
			bookLoans.add(bookLoan);
		}
		return bookLoans;
	}


}
