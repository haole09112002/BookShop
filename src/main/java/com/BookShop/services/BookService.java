package com.BookShop.services;

import com.BookShop.entities.Book;
import com.BookShop.payload.PagedResponse;

public interface BookService {

	PagedResponse<Book> findByCategoryId(int page, int size, Long id);
	
	PagedResponse<Book> findByAuthorId(int page, int size, Long id);
	
	PagedResponse<Book> findAll(int page, int size);
	
	PagedResponse<Book> findByCategoryIdAndSearch(int page, int size, Long categoryId, 
			String keywork,
			String sortType, String authorName,
			double minPrice, double maxPrice);
	
//	PagedResponse<Book> findByTitleAndAuthorNameAndSupplier(int page, int size, String keywork);

//	PagedResponse<Book> findByTitleAndAuthorNameAndSupplierAndSort(int page, int size, String keywork, String sort);
	
	PagedResponse<Book> findByKeywordAndSortAndPrice(int page, int size, String keywork, String sort, double minPrice, double maxPrice);

	PagedResponse<Book> findByKeywordAndPrice(int page, int size, String keywork, double minPrice, double maxPrice);
	
	
	PagedResponse<Book> findByKeyword(int page, int size, String keyword);
	
	PagedResponse<Book> findByKeywordAndSort(int page, int size, String keyword, String sort);

	PagedResponse<Book> findBySortAndPrice(int page, int size, String sort, double minPrice, double maxPrice);
	
	PagedResponse<Book> findByPrice(int page, int size, double minPrice, double maxPrice);
	
	PagedResponse<Book> sort(int page, int size, String sort);

	Book findById(long id);
}
