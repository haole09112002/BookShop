package com.BookShop.services.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.BookShop.controller.BookController;
import com.BookShop.entities.Book;
import com.BookShop.payload.PagedResponse;
import com.BookShop.repositories.BookRepository;
import com.BookShop.services.BookService;

@Service
public class BookServiceImpl implements BookService {
	 public static final Logger LOGGER = LoggerFactory.getLogger(BookServiceImpl.class);
	@Autowired
	private BookRepository bookRepository;

	@Override
	public PagedResponse<Book> findAll(int page, int size) {
		PageRequest pageRequest = PageRequest.of(page, size);
		Page<Book> bookPages = bookRepository.findAll(pageRequest);
		return new PagedResponse<Book>(bookPages.getContent(), bookPages.getNumber(), bookPages.getSize(),
				bookPages.getTotalElements(), bookPages.getTotalPages(), bookPages.isLast());
	}

	@Override
	public PagedResponse<Book> findByCategoryIdAndSearch(int page, int size, Long categoryId, 
			String keywork,String sortType, String authorName,
			double minPrice, double maxPrice) {
		// validate page and size

		PageRequest pageRequest = PageRequest.of(page, size);
		Page<Book> bookPages = null;
		if (categoryId == (long)0) // all category
		{	
			LOGGER.info(categoryId + " search");
			bookPages = bookRepository.search(keywork, authorName, minPrice, maxPrice, pageRequest);
			LOGGER.info("Books "+bookPages.getContent().size());
		} else {
			LOGGER.info(categoryId + "hahah");
			bookPages = bookRepository.searchByCategoryId(categoryId, keywork, authorName, minPrice, maxPrice, sortType, pageRequest);
		}
		
		return new PagedResponse<Book>(bookPages.getContent(), bookPages.getNumber(), bookPages.getSize(),
				bookPages.getTotalElements(), bookPages.getTotalPages(), bookPages.isLast());
	}

//	@Override
//	public PagedResponse<Book> findByTitleAndAuthorNameAndSupplier(int page, int size, String keywork) {
//		PageRequest pageRequest = PageRequest.of(page, size);
//		Page<Book> bookPages = bookRepository.findByTitleAndAuthorNameAndSupplier(keywork,pageRequest);
//		return new PagedResponse<Book>(bookPages.getContent(), bookPages.getNumber(), bookPages.getSize(),
//				bookPages.getTotalElements(), bookPages.getTotalPages(), bookPages.isLast());
//	}
//
//	@Override
//	public PagedResponse<Book> findByTitleAndAuthorNameAndSupplierAndSort(int page, int size, String keywork, String sort) {
//		PageRequest pageRequest = PageRequest.of(page, size);
//		Page<Book> bookPages = bookRepository.findByTitleAndAuthorNameAndSupplierAndSort(keywork,sort, pageRequest);
//		return new PagedResponse<Book>(bookPages.getContent(), bookPages.getNumber(), bookPages.getSize(),
//				bookPages.getTotalElements(), bookPages.getTotalPages(), bookPages.isLast());
//	}

	@Override
	public PagedResponse<Book> findByKeywordAndSortAndPrice(int page, int size, String keywork, String sort,
			double minPrice, double maxPrice) {
		PageRequest pageRequest = PageRequest.of(page, size);
		Page<Book> bookPages = bookRepository.findByKeywordAndPriceAndSort(keywork,sort,minPrice, maxPrice, pageRequest);
		return new PagedResponse<Book>(bookPages.getContent(), bookPages.getNumber(), bookPages.getSize(),
				bookPages.getTotalElements(), bookPages.getTotalPages(), bookPages.isLast());
	}
	
	@Override
	public PagedResponse<Book> findByKeywordAndPrice(int page, int size, String keywork, 
			double minPrice, double maxPrice) {
		PageRequest pageRequest = PageRequest.of(page, size);
		Page<Book> bookPages = bookRepository.findByKeywordAndPrice(keywork,minPrice, maxPrice, pageRequest);
		return new PagedResponse<Book>(bookPages.getContent(), bookPages.getNumber(), bookPages.getSize(),
				bookPages.getTotalElements(), bookPages.getTotalPages(), bookPages.isLast());
	}

	@Override
	public PagedResponse<Book> findByKeyword(int page, int size, String keyword) {
		PageRequest pageRequest = PageRequest.of(page, size);
		Page<Book> bookPages = bookRepository.findByKeyword(keyword, pageRequest);
		return new PagedResponse<Book>(bookPages.getContent(), bookPages.getNumber(), bookPages.getSize(),
				bookPages.getTotalElements(), bookPages.getTotalPages(), bookPages.isLast());
	}

	@Override
	public PagedResponse<Book> findByKeywordAndSort(int page, int size, String keyword, String sort) {
		PageRequest pageRequest = PageRequest.of(page, size);
		Page<Book> bookPages = bookRepository.findByKeywordAndSort(keyword, sort, pageRequest);
		return new PagedResponse<Book>(bookPages.getContent(), bookPages.getNumber(), bookPages.getSize(),
				bookPages.getTotalElements(), bookPages.getTotalPages(), bookPages.isLast());
	}

	@Override
	public PagedResponse<Book> findBySortAndPrice(int page, int size, String sort, double minPrice, double maxPrice) {
		PageRequest pageRequest = PageRequest.of(page, size);
		Page<Book> bookPages = bookRepository.findByPriceAndSort(sort,minPrice, maxPrice, pageRequest);
		return new PagedResponse<Book>(bookPages.getContent(), bookPages.getNumber(), bookPages.getSize(),
				bookPages.getTotalElements(), bookPages.getTotalPages(), bookPages.isLast());
	}

	@Override
	public PagedResponse<Book> findByPrice(int page, int size, double minPrice, double maxPrice) {
		PageRequest pageRequest = PageRequest.of(page, size);
		Page<Book> bookPages = bookRepository.findByprice(minPrice, maxPrice, pageRequest);
		return new PagedResponse<Book>(bookPages.getContent(), bookPages.getNumber(), bookPages.getSize(),
				bookPages.getTotalElements(), bookPages.getTotalPages(), bookPages.isLast());
	}

	@Override
	public PagedResponse<Book> sort(int page, int size, String sort) {
		PageRequest pageRequest = PageRequest.of(page, size);
		Page<Book> bookPages = bookRepository.sort(sort, pageRequest);
		return new PagedResponse<Book>(bookPages.getContent(), bookPages.getNumber(), bookPages.getSize(),
				bookPages.getTotalElements(), bookPages.getTotalPages(), bookPages.isLast());
	}

	@Override
	public Book findById(long id) {
		return bookRepository.findById(id).orElseThrow(()-> new RuntimeException("Không tìm thấy sách có id: " +id));
		
	}

	@Override
	public PagedResponse<Book> findByCategoryId(int page, int size, Long id) {
		PageRequest pageRequest = PageRequest.of(page, size);
		Page<Book> bookPages = bookRepository.findByCategoryId(id, pageRequest);
		return new PagedResponse<Book>(bookPages.getContent(), bookPages.getNumber(), bookPages.getSize(),
				bookPages.getTotalElements(), bookPages.getTotalPages(), bookPages.isLast());
	}

	@Override
	public PagedResponse<Book> findByAuthorId(int page, int size, Long id) {
		PageRequest pageRequest = PageRequest.of(page, size);
		Page<Book> bookPages = bookRepository.findByAuthorId(id, pageRequest);
		return new PagedResponse<Book>(bookPages.getContent(), bookPages.getNumber(), bookPages.getSize(),
				bookPages.getTotalElements(), bookPages.getTotalPages(), bookPages.isLast());
	}
	
}
