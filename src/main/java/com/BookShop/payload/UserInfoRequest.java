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

public class UserInfoRequest implements Serializable{
	
	private static final long serialVersionUID = 1L;

	private String fullname;
	
	private String email;

	private String phone;

	
	private String createPassword;
	
	private String repeatPassword;
}
