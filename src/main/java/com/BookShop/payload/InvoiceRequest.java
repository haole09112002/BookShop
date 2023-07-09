package com.BookShop.payload;

import java.io.Serializable;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class InvoiceRequest implements Serializable {

	@Override
	public String toString() {
		return "InvoiceRequest [province=" + province + ", district=" + district + ", ward=" + ward + ", street="
				+ street + ", fullname=" + fullname + ", phone=" + phone + ", email=" + email + ", message=" + message
				+ ", cartItems=" + cartItems + "]";
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String province;
	
	private String district;
	
	private String ward;
	
	private String street;
	
	private String fullname;
	
	
	private String phone;
	

	private String email;
	

	private String message;
	
	private List<Item> cartItems;
	
}
