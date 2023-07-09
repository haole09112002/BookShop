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
public class AuthorRequest implements Serializable {/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private long id;
	
	private String fullname;
	
	private String description;

}
