package com.BookShop.payload;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data

public class UserRequest implements Serializable{
	
	private static final long serialVersionUID = 1L;

	private String fullName;

	private String username;

	private String phone;

	private String email;
	
	private String password;
}
