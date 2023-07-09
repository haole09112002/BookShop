package com.BookShop.entities;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrimaryKeyJoinColumn;
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
	
	@OneToOne(fetch = FetchType.LAZY)
	@MapsId
	@JoinColumn(name = "id")
	private Invoice invoice;

}
