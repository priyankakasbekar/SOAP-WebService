package com.learningspring.soapwebservices.LibraryManagement.soap;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import com.learningspring.soapwebservices.LibraryManagement.soap.bean.Book;
import com.learningspring.soapwebservices.LibraryManagement.soap.exception.BookNotFoundException;
import com.learningspring.soapwebservices.LibraryManagement.soap.service.BookDetailsService;
import com.learningspring.soapwebservices.LibraryManagement.soap.service.BookDetailsService.Status;

import localhost.books.BookDetails;
import localhost.books.DeleteBookDetailsRequest;
import localhost.books.DeleteBookDetailsResponse;
import localhost.books.GetAllBookDetailsRequest;
import localhost.books.GetAllBookDetailsResponse;
import localhost.books.GetBookDetailsRequest;
import localhost.books.GetBookDetailsResponse;
import localhost.books.RequestStatus;

@Endpoint
public class BookDetailsEndpoint {
	
	@Autowired
	BookDetailsService service;
	
	@PayloadRoot(namespace = "http://localhost/books", localPart = "GetBookDetailsRequest")
	@ResponsePayload
	public GetBookDetailsResponse processBookRequest(@RequestPayload GetBookDetailsRequest request) {
		
		Book fndBook = service.findByID(request.getId());
		
		if(fndBook == null) {
			throw new BookNotFoundException("Book with id:" + request.getId() + " not found");
		}
		
		return mapBook(fndBook);	
	}
	
	@PayloadRoot(namespace = "http://localhost/books", localPart = "GetAllBookDetailsRequest")
	@ResponsePayload
	public GetAllBookDetailsResponse processAllBookRequest(@RequestPayload GetAllBookDetailsRequest request) {
		
		List<Book> bookLst = service.findAll();
				
		return mapAllBooks(bookLst);		
	}
	
	@PayloadRoot(namespace = "http://localhost/books", localPart = "DeleteBookDetailsRequest")
	@ResponsePayload
	public DeleteBookDetailsResponse processBookDeleteRequest(@RequestPayload DeleteBookDetailsRequest request) {
		
		Status status = service.deleteBook(request.getId());		
		DeleteBookDetailsResponse response = new DeleteBookDetailsResponse();
		response.setStatus(mapStatus(status));
		return response;
	}
	
	public GetBookDetailsResponse mapBook(Book book) {
		
		GetBookDetailsResponse response = new GetBookDetailsResponse();
		BookDetails bookdetails = mapBookDetails(book);
		response.setBookDetails(bookdetails);
		return response;
	}

	private BookDetails mapBookDetails(Book book) {
		BookDetails bookdetails = new BookDetails();
		bookdetails.setId(book.getId());
		bookdetails.setName(book.getName());
		bookdetails.setAuthor(book.getAuthor());
		return bookdetails;
	}
	
	private GetAllBookDetailsResponse mapAllBooks(List<Book> books) {
		GetAllBookDetailsResponse response = new GetAllBookDetailsResponse();
		
		for(Book book : books) {
			BookDetails bookdetail = mapBookDetails(book);
			response.getBookDetails().add(bookdetail);
		}
		return response;
	}
	
	private RequestStatus mapStatus(Status stat) {
		if(stat == Status.SUCESS)
			return RequestStatus.SUCCESS;
		else
			return RequestStatus.FAILURE;
	}

}
