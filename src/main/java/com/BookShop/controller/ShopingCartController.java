package com.BookShop.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.BookShop.entities.Book;
import com.BookShop.entities.CartItem;
import com.BookShop.entities.User;

import com.BookShop.payload.CartItemRequest;
import com.BookShop.payload.CartItemResponse;
import com.BookShop.payload.Item;
import com.BookShop.payload.UserPrincipal;
import com.BookShop.repositories.CartItemRepository;
import com.BookShop.services.BookService;
import com.BookShop.services.CategoryService;
import com.BookShop.services.ShoppingCartService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
public class ShopingCartController {
	public static final Logger LOGGER = LoggerFactory.getLogger(ShopingCartController.class);
	@Autowired
	private BookService bookService;

	@Autowired
	private ShoppingCartService shoppingCartService;
	
	@Autowired
	private CategoryService categoryService;
	
	
	@GetMapping("/bookshop/shoping-cart")
	public String showShopingCart(Authentication authentication, Model model) {
		if(authentication != null) {
			UserPrincipal userPrincipal = (UserPrincipal) (authentication.getPrincipal());
			List<CartItemResponse> cartItems = new ArrayList<CartItemResponse>();
			cartItems = shoppingCartService.getCartItemResponsesByUsername(userPrincipal.getUsername());
			model.addAttribute("cartItems", cartItems);
		}
			
		return "shoping-cart";
	}
	
	
	@GetMapping("/bookshop/getcart")
	@ResponseBody
	public List<CartItemResponse> getCart(Authentication authentication){
		 List<CartItemResponse> cartItems = new ArrayList<CartItemResponse>();
		if(authentication != null) {
			UserPrincipal userPrincipal = (UserPrincipal)  (authentication.getPrincipal());
			cartItems = shoppingCartService.getCartItemResponsesByUsername(userPrincipal.getUsername());
			return cartItems;
		}
		else {
			return null;
		}
		
	}
	
	
	@GetMapping("check")
	public String checkCartItem(@RequestParam(name = "cartItems") String cartItemsJson, Model model, RedirectAttributes redirectAttributes, Principal principal) {
		List<Item> cartItems = new ArrayList<Item>();
		ObjectMapper objectMapper = new ObjectMapper();
		try {
		    cartItems = Arrays.asList(objectMapper.readValue(cartItemsJson, Item[].class));
		    cartItems.forEach(item->{LOGGER.info(item.toString());});
		} catch (JsonProcessingException e) {
		        e.printStackTrace();
		}
		
		
		List<CartItemResponse> cartItemResponses = shoppingCartService.getCartItems(cartItems);
		if(!shoppingCartService.checkCartItems(cartItems)) {	// sai
			model.addAttribute("cartItems", cartItemResponses);
			return "redirect:/shoping-cart";
		}else {
			
			double totalItemFee = 0;
			double shippingFee = 30000;
			for (CartItemResponse item : cartItemResponses) {
				totalItemFee += item.getTotal();
			}
//			if(principal != null) {
//				redirectAttributes.addFlashAttribute("cartItems", cartItemResponses);
//			}else {
				redirectAttributes.addFlashAttribute("cartItems", cartItemResponses);
//			}
			
			redirectAttributes.addFlashAttribute("totalItemFee", totalItemFee);
			redirectAttributes.addFlashAttribute("shippingFee", shippingFee);
			redirectAttributes.addFlashAttribute("total", totalItemFee + shippingFee);
//			return "checkout";
			return "redirect:/bookshop/invoice";
		}
	}
	@PostMapping("/bookshop/add-to-cart")
	@ResponseBody
	public int addToCart(@RequestBody Item item, Authentication authentication) {
		
		int quantity = getBookQuantity(item.getId());
		if	(item.getQuantity() <= quantity) {
			if(authentication != null) {
				UserPrincipal userPrincipal =(UserPrincipal) authentication.getPrincipal();
				System.out.println(userPrincipal.getFullname());
				shoppingCartService.addToCart(item, userPrincipal.getUsername());
			}
			
		}else {
			return quantity;
		}
		return -1;
	}
		
	@GetMapping("/bookshop/shopping-cart")
	public String changeQuantity(@RequestParam(name = "id") Long bookId, @RequestParam(name = "quantity") int quantity, @RequestParam(name = "isDel", required = false) Boolean isDel , Authentication authentication) {
		if(authentication != null) {
			UserPrincipal userPrincipal = (UserPrincipal)authentication;
			if(isDel!= null && isDel == true)
				System.out.println(shoppingCartService.removeCartItem(bookId, userPrincipal.getUsername()));
			else {
				System.out.println(bookId + "  " + quantity );
				shoppingCartService.addToCart(new Item(bookId, quantity), userPrincipal.getUsername());
			}
		}
	
		return "redirect:/shoping-cart";
	}
	
	@GetMapping("/bookshop/check-quantity")
	@ResponseBody
	public int getBookQuantity(@RequestParam Long id) {
		Book book  = bookService.findById(id);
		return book.getQuantity();
	}
}
