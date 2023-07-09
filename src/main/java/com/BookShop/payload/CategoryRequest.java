package com.BookShop.payload;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CategoryRequest implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String name;
	private String description;
}
