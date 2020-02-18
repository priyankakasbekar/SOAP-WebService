package com.learningspring.soapwebservices.LibraryManagement.soap.service;



import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.stereotype.Component;

import com.learningspring.soapwebservices.LibraryManagement.soap.bean.Book;

@Component
public class BookDetailsService {
	
	public enum Status{SUCESS,FAILURE};
	
	private static List<Book> booklst = new ArrayList<Book>();
	
	static {
		booklst.add(new Book(123,"Book1","Author1"));
		booklst.add(new Book(456,"Book2","Author2"));
		booklst.add(new Book(789,"Book3","Author3"));
	}
	
	public Book findByID(int id) {
		
		for(Book book : booklst) {
			if(book.getId() == id) {
				return book;
			}
		}
		return null;
	}
	
	public List<Book> findAll(){
		return booklst;
	}
	
	public Status deleteBook(int id) {
		
		Iterator<Book> iterator = booklst.iterator();
		while(iterator.hasNext()) {
			Book book = iterator.next();
			if(book.getId() == id){
				iterator.remove();
				return Status.SUCESS;
			}
		}
		return Status.FAILURE;
	}

}
