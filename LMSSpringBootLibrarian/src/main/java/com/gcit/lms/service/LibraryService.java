package com.gcit.lms.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gcit.lms.dao.AuthorDAO;
import com.gcit.lms.dao.BookCopiesDAO;
import com.gcit.lms.dao.BookDAO;
import com.gcit.lms.dao.BookLoansDAO;
import com.gcit.lms.dao.GenreDAO;
import com.gcit.lms.dao.LibraryBranchDAO;
import com.gcit.lms.dao.PublisherDAO;
import com.gcit.lms.entity.Book;
import com.gcit.lms.entity.BookCopies;
import com.gcit.lms.entity.LibraryBranch;

@RestController
public class LibraryService {
	

	@Autowired
	AuthorDAO adao;
	
	@Autowired
	GenreDAO gndao;
	
	@Autowired
	BookDAO bdao;
	
	@Autowired
	PublisherDAO pdao;
	
	@Autowired
	BookLoansDAO bldao;
	
	@Autowired
	BookCopiesDAO bcdao;
	
	@Autowired
	LibraryBranchDAO lbdao;
	
	
	// BookCopy operations
	
	@Transactional
	@RequestMapping(value="/bookCopy",method=RequestMethod.POST,consumes="application/json")
	public void saveBookCopy(@RequestBody BookCopies bookCopy) throws SQLException
	{
			try {
				bcdao.addBookCopies(bookCopy);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
	}
	
	
	@Transactional
	@RequestMapping(value="/bookCopies",method=RequestMethod.GET,consumes="application/json",produces="application/json")
	public List<BookCopies> readBookCopiesById(@RequestParam("bookId") Integer bookId,@RequestParam("branchId") Integer branchId) throws SQLException
	{
			try {
				List<BookCopies> bookCopies=bcdao.ReadBookCopiesById(bookId, branchId);
				return bookCopies;
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		return null;
	}
	
	
	@Transactional
	@RequestMapping(value="/bookCopies/{bookId}",method=RequestMethod.PUT,consumes="application/json")
	public void updateBookCopies(@PathVariable("bookId") Integer bookId,@RequestParam("branchId") Integer branchId,@RequestBody BookCopies bookCopy) throws SQLException
	{																				//admin updating noOfCopies
			try {
				bcdao.updateBookCopies(bookCopy);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
	}
	
	@Transactional
	@RequestMapping(value="/bookCopies/return",method=RequestMethod.PUT,consumes="application/json")
	public void updateBookCopies2(@RequestBody BookCopies bookCopy) throws SQLException  //when returning book
	{
			try {
				int noOfCopies=bcdao.ReadBookCopiesById(bookCopy.getBookId(), bookCopy.getBranchId()).get(0).getNoOfCopies();
				noOfCopies+=1;
				bookCopy.setNoOfCopies(noOfCopies);
				
				bcdao.updateBookCopies(bookCopy);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
	}
	
	
	//Library Branch Operations
	
	@Transactional
	@RequestMapping(value="/libraryBranches/{branchId}",method=RequestMethod.PUT,consumes="application/json")
	public void updateLibraryBranch(@PathVariable("branchId") Integer branchId,@RequestBody LibraryBranch libraryBranch) throws SQLException
	{
			try {
				lbdao.updateLibraryBranch(libraryBranch);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
	}
	
	
	@Transactional
	@RequestMapping(value="/libraryBranches/{branchId}",method=RequestMethod.DELETE,consumes="application/json")
	public void deleteLibraryBranch(@PathVariable("branchId") Integer branchId) throws SQLException
	{
			try {
				lbdao.deleteLibraryBranch(branchId);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
	}
	
	
	@Transactional
	@RequestMapping(value="/libraryBranch",method=RequestMethod.POST,consumes="application/json")
	public void saveLibraryBranch(@RequestBody LibraryBranch libraryBranch) throws SQLException
	{
			try {
				lbdao.addLibraryBranch(libraryBranch);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
	}
	
	
	@Transactional
	@RequestMapping(value="/libraryBranches",method=RequestMethod.GET,produces="application/json")
	public List<LibraryBranch> readLibraryBranch() throws SQLException
	{
			try {
				List<LibraryBranch> libraryBranches=lbdao.ReadAllLibraryBranches();
				
				//get the books in the branch
				for(LibraryBranch branch: libraryBranches)
				{
					List<Book> books=bdao.ReadBooksByBranchID(branch.getBranchId());
					
					for(Book book: books)
					{
						List<BookCopies> bookCopies=bcdao.ReadBookCopiesById(book.getBookId(), branch.getBranchId());
						book.setNoOfCopies(bookCopies.get(0).getNoOfCopies());
					}
					
					
					branch.setBooks(books);
				}
				
				
				return libraryBranches;
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		
		return null;
	}
	
	
	@Transactional
	@RequestMapping(value="/libraryBranches/{branchName}",method=RequestMethod.GET,produces="application/json")
	public List<LibraryBranch> readLibraryBranchesByName(@PathVariable("branchName") String branchName) throws SQLException
	{
			try {
				List<LibraryBranch> libraryBranches=new ArrayList<LibraryBranch>();
				
				if(branchName.equals("undefined"))
				{
					libraryBranches=lbdao.ReadAllLibraryBranches();
				}
				else {
					libraryBranches=lbdao.readBranchesByName(branchName);
				}
				
				//get the books in the branch
				for(LibraryBranch branch: libraryBranches)
				{
					List<Book> books=bdao.ReadBooksByBranchID(branch.getBranchId());
					branch.setBooks(books);
				}
				
				
				return libraryBranches;
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		
		return null;
	}

}
