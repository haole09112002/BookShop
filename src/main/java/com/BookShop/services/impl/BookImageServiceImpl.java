package com.BookShop.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.BookShop.entities.Book;
import com.BookShop.entities.BookImage;
import com.BookShop.repositories.BookImageRepository;
import com.BookShop.services.BookImageService;

import jakarta.transaction.Transactional;

@Service
public class BookImageServiceImpl implements BookImageService {

	@Autowired
	private BookImageRepository bookImageRepository;
	
	@Autowired
	private FileStorageService fileStorageService;
	

	
	@Override
	public List<BookImage> findByBookId(long bookId){
		return bookImageRepository.findByBookId(bookId);
	}
	
	@Transactional
	@Override
	public BookImage saveBookImage(MultipartFile imgFile, Book book) {
		String fileName = fileStorageService.storeFile(imgFile);
        String imgUrl = ServletUriComponentsBuilder.fromCurrentContextPath()
        		.path("bookshop")
                .path("/downloadFile/")
                .path(fileName)
                .toUriString();
        BookImage bookImage = new BookImage();
        bookImage.setName(fileName);
        bookImage.setUrl(imgUrl);
        bookImage.setBook(book);
        
        return bookImage;
	}


	@Override
	@Transactional
	public void removeBooksImage(List<Long> imagesId) {
		imagesId.forEach(id -> 	{
			
			bookImageRepository.deleteById(id);
			
		
		});
		
	}
}
