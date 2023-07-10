package com.BookShop.controller;

import java.security.Principal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.BookShop.services.CategoryService;

@Controller
@RequestMapping("/bookshop")
public class HomeController {
	public static final Logger LOGGER = LoggerFactory.getLogger(HomeController.class);
	@Autowired
	CategoryService categoryService;
	@GetMapping
	public String homePage(Model model, Principal principal) {
		model.addAttribute("categories", categoryService.findTop5());

		LOGGER.info(categoryService.findTop5().size() + "");
		if(principal != null) {
				model.addAttribute("name", principal.getName());
		}
	
		return "/users/home";
	}
	
	@RequestMapping(value = "/401", method = RequestMethod.GET)
	public String error401() {
		return "401";
	}
	
	@GetMapping("/admin")
	public String adminHomePage(){
		return "/admin/home";
	}
}
