package com.BookShop.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.BookShop.entities.Book;
import com.BookShop.payload.PagedResponse;
import com.BookShop.services.BookService;
import com.BookShop.services.CategoryService;

import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/books")
public class BookController {
	public static final Logger LOGGER = LoggerFactory.getLogger(BookController.class);
	@Autowired
	private BookService bookService;

	@Autowired
	private CategoryService categoryService;

	@GetMapping("topnav")
	public String home2() {
		return "layout";
	}

	@GetMapping()
	public String listBook(Model model, @RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size,
			@RequestParam(defaultValue = "0", required = false) long parentId) {
		model.addAttribute("bookPages", bookService.findAll(page, size));
		model.addAttribute("categories", categoryService.findRootCategory());
		return "book-result";
	}

	@GetMapping("search")
	public String Search(@RequestParam(defaultValue = "0", required = true) int page,
			@RequestParam(defaultValue = "10") int size, @RequestParam(defaultValue = "0") Long categoryId,
			@RequestParam(defaultValue = "") String keyword, @RequestParam(defaultValue = "ASC") String sortType,
			@RequestParam(defaultValue = "") String authorName, @RequestParam(defaultValue = "0.0") double minPrice,
			@RequestParam(defaultValue = "1000000.0") double maxPrice, Model model) {
//		LOGGER.info(keyword +" " + categoryId);
		PagedResponse<Book> bookPages = bookService.findByCategoryIdAndSearch(page, size, categoryId, keyword, sortType,
				authorName, minPrice, maxPrice);
		model.addAttribute("bookPages", bookPages);
		model.addAttribute("categories", categoryService.findRootCategory());

//		LOGGER.info(String.valueOf(bookPages.getTotalElements())+" " + String.valueOf(bookPages.getTotalPages()) + "");
		return "book-result";
	}

	@GetMapping("search-books")
	public String searchBooks(Model model, @RequestParam(defaultValue = "0", required = true) int page,
			@RequestParam(defaultValue = "10") int size, @RequestParam(required = false) String keyword,
			@RequestParam(required = false) String sort,
			@RequestParam(required = false) Double minPrice,
			@RequestParam(required = false) Double maxPrice, HttpServletRequest httpServletRequest
			) {
		String location = String.format("%s?%s", httpServletRequest.getServletPath(), httpServletRequest.getQueryString());
		LOGGER.info(location);
		model.addAttribute("location", location);
		LOGGER.info(keyword);

		PagedResponse<Book> bookPages = null;

		
		if(keyword != null && !keyword.isEmpty()) {
			if(sort != null) {
				if(maxPrice!= null && minPrice != null) {
					bookPages = bookService.findByKeywordAndSortAndPrice(page, size, keyword, sort, minPrice , maxPrice);
					model.addAttribute("maxPrice", maxPrice);
					model.addAttribute("minPrice", minPrice);
				}
				else {
					//findByKeywordAndSort()
					bookPages = bookService.findByKeywordAndSort(page, size, keyword, sort);
				}	
				model.addAttribute("sort", sort);
			}else {
				if(maxPrice!= null && minPrice != null) {
					bookPages = bookService.findByKeywordAndPrice(page, size, keyword, minPrice, maxPrice);
					model.addAttribute("maxPrice", maxPrice);
					model.addAttribute("minPrice", minPrice);
				}
				else {
					//findByKeyword
					bookPages = bookService.findByKeyword(page, size, keyword);
				}	
			}
			model.addAttribute("keyword", keyword);
		}
		else {
			if(sort != null) {
				if(maxPrice!= null && minPrice != null) {
					bookPages = bookService.findBySortAndPrice(page, size, sort, minPrice, maxPrice);
					model.addAttribute("maxPrice", maxPrice);
					model.addAttribute("minPrice", minPrice);
				}
				else {
					//Sort
					bookPages = bookService.sort(page, size, sort);
				}	
				model.addAttribute("sort", sort);
			}else {
				if(maxPrice!= null && minPrice != null) {
					bookPages = bookService.findByPrice(page, size, minPrice, maxPrice);
					model.addAttribute("maxPrice", maxPrice);
					model.addAttribute("minPrice", minPrice);
				}
				else {
					bookPages = bookService.findAll(page, size);
				}	
			}
		}
		model.addAttribute("bookPages", bookPages);
		model.addAttribute("categories", categoryService.findRootCategory());
		return "book-result";
	}
	@GetMapping("/categories/{id}")
	public String getBookByCategoryId(Model model,@PathVariable long id, @RequestParam(required = false, defaultValue = "0") int page,  @RequestParam(required = false, defaultValue = "10") int size) {
		
		model.addAttribute("bookPages", bookService.findAll(page, size));
		model.addAttribute("categories", categoryService.findRootCategory());
		return "book-result";
	}
	@GetMapping("/{id}")
	public String getBookDetails(@PathVariable(name = "id") Long id, @RequestParam(defaultValue = "0", required = true) int page,
			@RequestParam(defaultValue = "5") int size, Model model) {
		Book book = bookService.findById(id);
//		if(book != null)
//		{
			model.addAttribute("book", book);
			model.addAttribute("booksByAuthor", bookService.findByAuthorId(page, size, book.getAuthor().getId()));
			model.addAttribute("booksByCategory", bookService.findByCategoryId(page, size, book.getCategory().getId()));
//		}
		
		
		
		return "book-details";
	}
	@GetMapping(value = "/booksByAuthor/{id}")
    public String moreMessage(@PathVariable(name = "id") Long id, @RequestParam(defaultValue = "0", required = true) int page,
			@RequestParam(defaultValue = "5") int size, Model model) {
		Book book = bookService.findById(id);
		model.addAttribute("book", book);
        model.addAttribute("booksByCategory",  bookService.findByCategoryId(page, size, book.getCategory().getId()));
        return "list-book-fragment :: list-book-fragment";
    }
}
