package com.BookShop.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Item {
	private Long id;
	private int quantity;
	@Override
	public String toString() {
		return "Item [id=" + id + ", quantity=" + quantity + "]";
	}
}
