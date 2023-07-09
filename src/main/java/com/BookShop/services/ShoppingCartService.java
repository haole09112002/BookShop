package com.BookShop.services;

import java.util.List;

import com.BookShop.payload.CartItemResponse;

import com.BookShop.payload.Item;

public interface ShoppingCartService {

	CartItemResponse checkCartItem(Item cartItem);

	List<CartItemResponse> getCartItems(List<Item> cartItems);

	boolean checkCartItems(List<Item> cartItems);
	
	int addToCart(Item item, String username);

	List<CartItemResponse> getCartItemResponsesByUsername(String username);

	boolean removeCartItem(long bookId, String username);
}
