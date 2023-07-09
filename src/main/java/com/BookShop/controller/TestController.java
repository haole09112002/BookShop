package com.BookShop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.BookShop.services.BookService;
import com.BookShop.services.CategoryService;



@Controller
public class TestController {

	@Autowired
	private BookService bookService;
	@Autowired
	private CategoryService categoryService;
	
	@GetMapping("/home")
	public String home(Model model, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "3") int size) {
		
		int size2 = 3;
		model.addAttribute("bookPages", bookService.findAll(page, size2));
		return "home";
	}
	@GetMapping("/test")
	public String test(Model model, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "3") int size) {
		
		int size2 = 3;
//		model.addAttribute("bookPages", bookService.findAll(page, size2));
		model.addAttribute("bookPages", bookService.findAll(page, 10));
		model.addAttribute("categories", categoryService.findRootCategory());
		return "test";
	}
	
}
