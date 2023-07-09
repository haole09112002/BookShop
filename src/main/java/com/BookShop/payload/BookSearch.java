package com.BookShop.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class BookSearch {
	private String keyword;
	private Long  categoryId;
	private String authorName;
	private int currentPage;
	private String sort; 
	private String minPrice;
	private String maxPrice;
	private String BookStatus;
}
