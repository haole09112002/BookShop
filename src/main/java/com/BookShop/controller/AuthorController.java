package com.BookShop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.BookShop.entities.Author;
import com.BookShop.payload.AuthorRequest;
import com.BookShop.services.AuthorService;

@Controller
@RequestMapping("/bookshop")
public class AuthorController {

	@Autowired
	private AuthorService authorService;
	
	@PostMapping("/admin/authors/new")
	public ResponseEntity<Author> save(@RequestBody AuthorRequest authorRequest, Authentication authentication){
		if(authentication != null) {
			return ResponseEntity.ok(authorService.save(authorRequest));
		}else {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
	}
}
