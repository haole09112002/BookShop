package com.BookShop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.BookShop.entities.Category;
import com.BookShop.payload.CategoryRequest;
import com.BookShop.services.CategoryService;

@Controller
@RequestMapping("/bookshop")
public class CategoryController {
	
	@Autowired
	private CategoryService categoryService;
	
	@PostMapping("/admin/categories/new")
	private ResponseEntity<Category> saveCategory(@RequestBody CategoryRequest categoryRequest, Authentication authentication) {
		if(authentication != null) {
			System.out.println(categoryRequest.getName());
			return ResponseEntity.ok(categoryService.save(categoryRequest));
		}
		else {
			System.out.println(categoryRequest.getName());
			return  ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
		
		
	}
}
