package com.gcit.lms.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Component;

import com.gcit.lms.entity.Borrower;

@Component
public class BorrowerDAO extends BaseDAO<Borrower> implements ResultSetExtractor<List<Borrower>>{
	
	public void addBorrower(Borrower borrower) throws SQLException, ClassNotFoundException
	{
		mySqlTemplate.update("insert into tbl_borrower (name,address,phone) values(?,?,?)",new Object[] {borrower.getName(),borrower.getAddress(),borrower.getPhone()});
	}
	
	public void updateBorrower(Borrower borrower) throws SQLException, ClassNotFoundException
	{
		mySqlTemplate.update("update tbl_borrower set name = ?, address = ?, phone = ? where cardNo = ?",new Object[] {borrower.getName(),borrower.getAddress(),borrower.getPhone(),borrower.getCardNo()});
		
	}
	
	public void deleteBorrower(Borrower borrower) throws ClassNotFoundException, SQLException
	{
		
		mySqlTemplate.update("delete from tbl_borrower where cardNo=?",new Object[] {borrower.getCardNo()});	
	}
	
	public List<Borrower> ReadAllBorrowers() throws ClassNotFoundException, SQLException
	{
		
		return mySqlTemplate.query("select * from tbl_borrower",this);	
	}
	
	public List<Borrower> ReadAllBorrowerById(int cardNo) throws ClassNotFoundException, SQLException
	{
		
		return mySqlTemplate.query("select * from tbl_borrower where cardNo=?",new Object[] {cardNo},this);	
	}
	
	public List<Borrower> ReadBorrowersByName(String name) throws ClassNotFoundException, SQLException
	{
		name="%"+name+"%";
		return mySqlTemplate.query("select * from tbl_borrower where name like ?",new Object[] {name},this);	
	}
	
	
	public List<Borrower> extractData(ResultSet rs) throws SQLException
	{
		List<Borrower> borrowers=new ArrayList<>();
		while(rs.next()){
			Borrower borrower=new Borrower();
			
			borrower.setCardNo(rs.getInt("cardNo"));
			borrower.setName(rs.getString("name"));
			borrower.setPhone(rs.getString("phone"));
			borrower.setAddress(rs.getString("address"));
			
			borrowers.add(borrower);
		}
		return borrowers;
	}
	

	

}