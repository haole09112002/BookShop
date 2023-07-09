package com.BookShop.payload;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Data
public class CartItemResponse implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long id;
	private String title;
	private String img;
	private int quantity;
	private double price;
	private double total;
	public CartItemResponse(Long id, String title, String img, int quantity, double price) {
		super();
		this.id = id;
		this.title = title;
		this.img = img;
		this.quantity = quantity;
		this.price = price;
		this.total = this.price * this.quantity;
	}
	
}
