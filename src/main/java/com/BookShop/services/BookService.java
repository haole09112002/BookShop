package com.BookShop.services;

import java.util.List;
import java.util.Map;

import com.BookShop.entities.Book;
import com.BookShop.payload.PagedResponse;

public interface BookService {

	PagedResponse<Book> findByCategoryId(int page, int size, Long id);
	
	PagedResponse<Book> findByAuthorId(int page, int size, Long id);
	
	PagedResponse<Book> findAll(int page, int size);
	
	List<Book> findByBookStatusAndKeyword(String status, String keyword);

	Book findById(long id);
	
	List<String>  getformality();
	
	PagedResponse<Book> filterBooks(Map<String, Object> filters);
		
	List<Book> findTop5ByCategoryId(long id);
	
	Book saveBook(Book newBook);

	Book updateBookStatus(long bookId, Boolean isBlock);
	
	List<Book> top5Sale();
}
