package com.BookShop.services;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.BookShop.entities.Book;
import com.BookShop.entities.BookImage;

public interface BookImageService {

	BookImage saveBookImage(MultipartFile imgFile, Book book);

	List<BookImage> findByBookId(long bookId);
	
	 void removeBooksImage(List<Long> imagesId);

}
