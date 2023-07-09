package com.BookShop.payload;

import java.util.List;

import com.BookShop.entities.Author;
import com.BookShop.entities.BookImage;
import com.BookShop.entities.BookStatusEnum;

import com.BookShop.entities.Category;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class BookResponse {

	private long id;
	
	private String title;

	private String publisher;

	private String publishYear;
	
	private String language;
	
	private String supplier;

	private double weight;

	private int pageCount;

	private String formality;

	private String decription;

	private int ageRange;

	private double price;

	private String size;

	private int quantity;
	
	private BookStatusEnum status;
	
	private Category category;
//	
	private Author author;
//	
	private List<BookImage> bookImages;
	

}
