package com.BookShop.services.impl;

import java.io.Console;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.BookShop.entities.Book;
import com.BookShop.entities.Cart;
import com.BookShop.entities.CartItem;
import com.BookShop.entities.User;
import com.BookShop.payload.CartItemResponse;
import com.BookShop.payload.Item;
import com.BookShop.repositories.BookRepository;
import com.BookShop.repositories.CartItemRepository;
import com.BookShop.repositories.CartRepository;
import com.BookShop.repositories.UserRepository;
import com.BookShop.services.ShoppingCartService;

import jakarta.transaction.Transactional;

@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {
	
	@Autowired
	private BookRepository bookRepo;
	
	@Autowired
	private CartItemRepository cartItemRepository;
	
	@Autowired
	private CartRepository cartRepository;

	@Autowired
	private UserRepository userRepository;


	@Override
	public CartItemResponse checkCartItem(Item cartItem) {
		Book book = bookRepo.findById(cartItem.getId()).orElseThrow(()->new RuntimeException("ll"));
		if(book.getQuantity() < cartItem.getQuantity()) {
			return new CartItemResponse(book.getId(), book.getTitle(), book.getBookImages().get(0).getUrl(), book.getQuantity(), book.getPrice());
		}
		return null;
	}

	@Override
	public List<CartItemResponse> getCartItems(List<Item> cartItems) {
		List<CartItemResponse> responseCartItems = new ArrayList<CartItemResponse>();
		for (Item item : cartItems) {
			Book book = bookRepo.findById(item.getId()).orElseThrow(()->new RuntimeException("ll"));
			responseCartItems.add(new CartItemResponse(book.getId(), book.getTitle(), book.getBookImages().get(0).getUrl(), item.getQuantity(), book.getPrice()));
		}
		return responseCartItems;
	}

	@Override
	public boolean checkCartItems(List<Item> cartItems) {
		for (Item cartItem : cartItems) {
			if(checkCartItem(cartItem) != null) {
				return false;
			}
		}
		return true;
	}
	@Transactional
	public int addToCart(Item item, String username) {
		Cart cart = cartRepository.findByUserUsername(username).orElse(null);
		Book book = bookRepo.findById(item.getId()).orElse(null);
		if(cart == null) {
			System.out.println(username);
			User user = userRepository.findByUsername(username).orElseThrow(()->new RuntimeException("ccc"));
			cart= new Cart();
			cart.setUser(user);
			cart = cartRepository.save(cart);
			if(item.getQuantity() <= book.getQuantity() ) {
				cartItemRepository.save(new com.BookShop.entities.CartItem(cart, book, item.getQuantity()));
			}else {
				return book.getQuantity();
			}
		}
		else {
			if(cart.getCartItems() != null) {
				int index = -1;
				for(int i = 0 ; i< cart.getCartItems().size() ; i++) {
					if(cart.getCartItems().get(i).getBook().getId() == item.getId()) {
						index = i;
						break;
					}
				}
				if(index >= 0)// da co san trong gio hang
				{
					int newQuantity = cart.getCartItems().get(index).getQuantity() + item.getQuantity();
					if( newQuantity <= book.getQuantity()) {
						cart.getCartItems().get(index).setQuantity(newQuantity);
					}
					else {
						return book.getQuantity();
					}
				}
				else {
					if( item.getQuantity() <= book.getQuantity()) {
						cart.getCartItems().add(new CartItem(cart, book, item.getQuantity()));
					}else {
						return book.getQuantity();
					}
				}
				cartRepository.save(cart);		
			}
		}
		return -1;
	}
	
	public List<CartItemResponse> getCartItemResponsesByUsername(String username){
		List<CartItemResponse> cartItems = new ArrayList<CartItemResponse>();
	 	Cart cart = cartRepository.findByUserUsername(username).orElse(null);
	 	if(cart != null) {
	 	cartItems = cart.getCartItems().stream().map(item -> {
			return new CartItemResponse(item.getBook().getId(), item.getBook().getTitle(), item.getBook().getBookImages().get(0).getUrl(), item.getQuantity(), item.getBook().getPrice());
		}).toList();
	 	return cartItems;
	 	}
	 	else {
			return null;
		}
	}
	@Override
	@Transactional
	public boolean removeCartItem(long bookId, String  username) {
		try {
			CartItem cartItem = cartItemRepository.findByUsernameAndBookId(username, bookId);
		 System.out.println("Id :"+cartItem.getId());	
		cartItemRepository.deleteById(cartItem.getId());
		return true;
		} catch (Exception e) {
			return false;
		}
	}


}
