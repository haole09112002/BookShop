package com.BookShop.services;

import java.util.List;

import com.BookShop.entities.Author;
import com.BookShop.payload.AuthorRequest;

public interface AuthorService {
	List<Author> findAll();

	Author findById(long id);
	
	Author save(AuthorRequest author);
}
