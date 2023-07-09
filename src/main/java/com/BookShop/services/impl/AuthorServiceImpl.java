package com.BookShop.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.BookShop.entities.Author;
import com.BookShop.exceptions.NotFoundException;
import com.BookShop.payload.AuthorRequest;
import com.BookShop.repositories.AuthorRepository;
import com.BookShop.services.AuthorService;

@Service
public class AuthorServiceImpl implements AuthorService {

	@Autowired
	private AuthorRepository authorRepository;
	
	@Override
	public List<Author> findAll() {
		// TODO Auto-generated method stub
		return authorRepository.findAll();
	}

	@Override
	public Author findById(long id) {
		return	authorRepository.findById(id).orElseThrow(()->new NotFoundException("Không tìm thấy tác giả có id: " +id));
	}

	@Override
	public Author save(AuthorRequest authorRequest) {
		Author author = new Author();
		author.setFullname(authorRequest.getFullname());
		author.setDescription(authorRequest.getDescription());
		return authorRepository.save(author);
	}
}
