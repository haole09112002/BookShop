package com.BookShop.entities;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data

@Entity
@Table(name = "contacts")
public class Contact {

	@Id
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Column
	private String fullname;
	
	@Column
	private String phone;
	
	@Column
	private String email;
	
	@Column
	private String message;
	
	@OneToOne(mappedBy = "contact", cascade = CascadeType.ALL, orphanRemoval = true)
	private Address address;
	
	@OneToOne
	@MapsId
	@JoinColumn(name = "id")
	private Invoice invoice;

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Contact [id=");
		builder.append(id);
		builder.append(", fullname=");
		builder.append(fullname);
		builder.append(", phone=");
		builder.append(phone);
		builder.append(", email=");
		builder.append(email);
		builder.append(", message=");
		builder.append(message);
		builder.append(", address=");
		builder.append(address);
		builder.append(", invoice=");
		builder.append(invoice);
		builder.append("]");
		return builder.toString();
	}

}
