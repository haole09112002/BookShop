package com.BookShop.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.BookShop.services.BookService;
import com.BookShop.services.CategoryService;

@Controller
public class ShopingCartController {
	public static final Logger LOGGER = LoggerFactory.getLogger(ShopingCartController.class);
	@Autowired
	private BookService bookService;

	@Autowired
	private CategoryService categoryService;
	
	@GetMapping("shoping-card")
	public String showShopingCard() {
		return "shoping-card";
	}
}
