package com.BookShop.controller;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import com.BookShop.entities.Author;
import com.BookShop.entities.Book;
import com.BookShop.entities.BookStatusEnum;
import com.BookShop.entities.Category;
import com.BookShop.payload.BookResponse;
import com.BookShop.payload.UserPrincipal;
import com.BookShop.services.AuthorService;
import com.BookShop.services.BookImageService;
import com.BookShop.services.BookService;
import com.BookShop.services.CategoryService;
import com.BookShop.services.CommentService;
import com.BookShop.services.InvoiceService;



@Controller
@RequestMapping("/bookshop")
public class BookController {
	public static final Logger LOGGER = LoggerFactory.getLogger(BookController.class);
	@Autowired
	private BookService bookService;

	@Autowired
	private CommentService commentService;
	
	@Autowired
	private CategoryService categoryService;
	
	@Autowired
	private InvoiceService invoiceService;

	@Autowired
	private AuthorService authorService;
	
	@Autowired
	private BookImageService bookImageService;


	@GetMapping("/books/categories/{id}")
	public String getBookByCategoryId(Model model,@PathVariable long id, @RequestParam(required = false, defaultValue = "0") int page,  @RequestParam(required = false, defaultValue = "10") int size) {
		model.addAttribute("bookPages", bookService.findByCategoryId(page, size, id));
		model.addAttribute("categories", categoryService.findRootCategory());
		return "book-result";
	}
	
	@GetMapping("/books/{id}")
	public String getBookDetails(@PathVariable(name = "id") Long id, @RequestParam(defaultValue = "0", required = true) int page,
			@RequestParam(defaultValue = "5") int size,Principal principal, Model model) {
		Book book = bookService.findById(id);
			model.addAttribute("book", book);
			boolean isPurchased = false;
			if(principal != null) {
				isPurchased = invoiceService.userHavePurchasedBook(principal.getName(), id);
			}
			model.addAttribute("isPurchased", isPurchased);
			model.addAttribute("comments", commentService.findCommentsByBookId(id));
			model.addAttribute("booksByAuthor", bookService.findByAuthorId(page, size, book.getAuthor().getId()));
			model.addAttribute("booksByCategory", bookService.findByCategoryId(page, size, book.getCategory().getId()));
		return "book-details";
	}
	
	@GetMapping("/books")
	@ResponseBody
	public BookResponse getBook(@RequestParam(name = "id") Long id, Model model) {
		Book book = bookService.findById(id);
		BookResponse bookResponse = new BookResponse(book.getId(), book.getTitle(), book.getPublisher(), book.getPublishYear(), book.getLanguage(), book.getSupplier(), book.getWeight(), book.getPageCount(), book.getFormality(), book.getDecription(), book.getAgeRange(), book.getPrice(), book.getSize(), book.getQuantity(), book.getStatus(), book.getCategory(), book.getAuthor(), book.getBookImages());
		return bookResponse;
	}
	
	
	@GetMapping(value = "/books/booksByAuthor/{id}")
    public String getBooksByAuthor(@PathVariable(name = "id") Long id, @RequestParam(defaultValue = "0", required = true) int page,
			@RequestParam(defaultValue = "5") int size, Model model) {
		Book book = bookService.findById(id);
		model.addAttribute("book", book);
        model.addAttribute("books",  bookService.findByAuthorId(page, size, book.getCategory().getId()));
        model.addAttribute("type", "author");
        return "list-book-fragment :: list-book-fragment";
    }
	
	@GetMapping(value = "/books/booksByCategory/{id}")
    public String getBooksByCategory(@PathVariable(name = "id") Long id, @RequestParam(defaultValue = "0", required = true) int page,
			@RequestParam(defaultValue = "5") int size, Model model) {
		Book book = bookService.findById(id);
		model.addAttribute("book", book);
        model.addAttribute("books",  bookService.findByCategoryId(page, size, book.getCategory().getId()));
        model.addAttribute("type", "category");
        return "list-book-fragment :: list-book-fragment";
    }
	

	
	@GetMapping(value = "/books/topsale")
    public String getBooksTopSale( @RequestParam(defaultValue = "0", required = true) int page,
			@RequestParam(defaultValue = "5") int size, Model model) {
        model.addAttribute("books",  bookService.findByCategoryId(page, size,(long) 1));
        model.addAttribute("type", "sale");
        return "list-book-top-sale :: list-book-top-sale";
    }
	
	@GetMapping(value = "/books/top-categories/{id}")
	public String getBooksTop5Category( @PathVariable(name = "id") Long id,  Model model) {
	        model.addAttribute("books",  bookService.findTop5ByCategoryId(id));
	        model.addAttribute("type", "category");
	        model.addAttribute("id", id);
	        return "list-book-home-fragment :: list-book-home-fragment";
	}
	
	@GetMapping(value = "/books/top-sale")
    public String getTopSale( Model model) {
	
        model.addAttribute("books",  bookService.top5Sale());
        model.addAttribute("type", "sale");
        return "list-book-home-fragment :: list-book-home-fragment";
    }
	
	@GetMapping("/books/search-books")
	public String searchBooks2(Model model,@RequestParam Map<String, Object> params)
			
	{
		System.out.println(params.toString()); 
		if (params.containsKey("categoryId")) {
	        Long categoryId = Long.parseLong((String) params.get("categoryId"));
	        System.out.println(categoryId+"");
	        model.addAttribute("categoryId", categoryId);
	    }
		else {
			 model.addAttribute("categoryId", 0);
		}
		if (params.containsKey("keyword")) {
		       String keyword = (String) params.get("keyword");
		        model.addAttribute("keyword", keyword);
		}
		if (params.containsKey("sort")) {
		       String sortVal = (String) params.get("sort");
		        model.addAttribute("sort", sortVal);
		}   
		if (params.containsKey("minPrice")) {
	        double minPrice = Double.parseDouble  ((String) params.get("minPrice"));
	        model.addAttribute("minPrice", minPrice);
	    }
		if (params.containsKey("maxPrice")) {
	        double maxPrice = Double.parseDouble  ((String) params.get("minPrice"));
	        model.addAttribute("maxPrice", maxPrice);
	    }
		model.addAttribute("bookPages", bookService.filterBooks(params));
		model.addAttribute("categories", categoryService.findRootCategory());
		return "book-result";
	}
	
	@GetMapping("/admin/books")
	public String addBook(@RequestParam(required = false) String status, @RequestParam(required = false) String keyword,Model model, Authentication authentication) {
		if(authentication != null) {
			model.addAttribute("bookPages", bookService.findByBookStatusAndKeyword(status, keyword)); 
		model.addAttribute("authors", authorService.findAll());
		model.addAttribute("categories", categoryService.findAll());
		
		return "/admin/list-book";
		}else {
			return "redirect:/bookshop/401";
		}
		
	}
	@GetMapping( value = { "/admin/books/new/{id}","/admin/books/new" })
	public String listBookAdmin(@PathVariable(required = false) Long id, Model model) {
		if(id != null) {
			model.addAttribute("book", bookService.findById(id));
		}
		model.addAttribute("authors", authorService.findAll());
		model.addAttribute("categories", categoryService.findAll());
		return "/admin/new-book";
	}
	
	@PostMapping("/admin/books/add")
    public ResponseEntity<?> addOrUpdateBook(
    		@RequestParam(name = "id", required = false) Long bookId,
            @RequestParam("title") String title,
            @RequestParam("price") double price,
            @RequestParam("quantity") int quantity,
            @RequestParam("publisher") String publisher,
            @RequestParam("publishYear") String publishYear,
            @RequestParam("language") String language,
            @RequestParam("supplier") String supplier,
            @RequestParam("weight") Double weight,
            @RequestParam("pageCount") int pageCount,
            @RequestParam("formality") String formality,
            @RequestParam("ageRange") int ageRange,
            @RequestParam("size") String size,
            @RequestParam("categoryId") long categoryId,
            @RequestParam("authorId") long authorId,
            @RequestParam("description") String description,
            @RequestParam(name = "images", required = false) MultipartFile[] images,
            @RequestParam(name = "removeImage", required = false) List<Long> imagesId,
            Authentication authentication) {
        if(authentication != null) {
        	UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();

        	if(imagesId != null) {
    			bookImageService.removeBooksImage(imagesId);
    		}
        	Book book = new Book();
    		if(bookId != null) {
    			book = bookService.findById(bookId);
    		}
    		
    		book.setTitle(title);
    		book.setPrice(price);
    		book.setQuantity(quantity);
    		book.setPublisher(publisher);
    		book.setPublishYear(publishYear);
    		book.setLanguage(language);
    		book.setSupplier(supplier);
    		book.setWeight(weight);
    		book.setPageCount(pageCount);
    		book.setFormality(formality);
    		book.setAgeRange(ageRange);
    		book.setSize(size);
    		book.setDecription(description);	
    		book.setStatus(BookStatusEnum.ACTIVE);
    		book.setCreatedBy(authorId);
    	    Category category =	categoryService.findById(categoryId);
    		Author author = authorService.findById(authorId);
    		book.setCategory(category);
    		book.setAuthor(author);
    		book.setCreatedBy(userPrincipal.getId());
    		book.setCreatedDate(LocalDateTime.now());

    	
    		if(images != null) {
    			for(MultipartFile img : images )
    			book.getBookImages().add(bookImageService.saveBookImage(img, book));
    		}
    		 bookService.saveBook(book);
    		if(bookId != null)
    			return ResponseEntity.ok(book);
    		else {
    			return ResponseEntity.ok(book);
			}
        }
        else {
        	return  ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
		
        
    }
	@PostMapping("/admin/books/update-book-status")	
	public ResponseEntity<?> updateBookStatus(@RequestParam(name = "bookId") long bookId, @RequestParam(name = "isBlock") Boolean isBlock, Authentication authentication){
		if(authentication != null) {
			if(bookService.updateBookStatus(bookId, isBlock)!= null)
			return ResponseEntity.ok("Done");
			else {
				return ResponseEntity.badRequest().build();
			}
		}
		return  ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();	
	}
	

	
}
