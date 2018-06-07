package com.gcit.lms.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Component;

import com.gcit.lms.entity.Author;
import com.gcit.lms.entity.LibraryBranch;

@Component
public class LibraryBranchDAO extends BaseDAO<LibraryBranch> implements ResultSetExtractor<List<LibraryBranch>>{

	public void addLibraryBranch(LibraryBranch libraryBranch) throws SQLException, ClassNotFoundException
	{
		mySqlTemplate.update("insert into tbl_library_branch (branchName,branchAddress) values(?,?)",new Object[] {libraryBranch.getBranchName(),libraryBranch.getBranchAddress()});
	}
	
	public void updateLibraryBranch(LibraryBranch libraryBranch) throws SQLException, ClassNotFoundException
	{
		mySqlTemplate.update("update tbl_library_branch set branchName = ?,branchAddress = ? where branchId = ?",new Object[] {libraryBranch.getBranchName(),libraryBranch.getBranchAddress(),libraryBranch.getBranchId()});
		
	}
	
	public void deleteLibraryBranch(Integer branchId) throws ClassNotFoundException, SQLException
	{
		
		mySqlTemplate.update("delete from tbl_library_branch where branchId=?",new Object[] {branchId});	
	}
	
	public List<LibraryBranch> ReadAllLibraryBranches() throws ClassNotFoundException, SQLException
	{
		
		return mySqlTemplate.query("select * from tbl_library_branch",this);	
	}
	
	public List<LibraryBranch> ReadLibraryBranchesById(int branchId) throws ClassNotFoundException, SQLException
	{
		
		return mySqlTemplate.query("select * from tbl_library_branch where branchId=?",new Object[] {branchId},this);	
	}
	
	public List<LibraryBranch> readBranchesByName(String name) throws ClassNotFoundException, SQLException
	{
		name="%"+name+"%";
		
		return mySqlTemplate.query("select * from tbl_library_branch where branchName like ?",new Object[] {name},this);	
	}
	
	
	public List<LibraryBranch> extractData(ResultSet rs) throws SQLException
	{
		List<LibraryBranch> libraryBranches=new ArrayList<>();
		while(rs.next()){
		
			LibraryBranch libraryBranch = new LibraryBranch();
			libraryBranch.setBranchId(rs.getInt("branchId"));
			libraryBranch.setBranchName(rs.getString("branchName"));
			libraryBranch.setBranchAddress(rs.getString("branchAddress"));
			libraryBranches.add(libraryBranch);
		}
		return libraryBranches;
	}
	
	

}