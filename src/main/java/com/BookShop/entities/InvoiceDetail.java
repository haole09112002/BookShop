package com.BookShop.entities;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "invoice_details")

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class InvoiceDetail {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@ManyToOne
	@JoinColumn(name = "bookId")
	private Book book;

	@ManyToOne
	@JoinColumn(name = "invoiceId")
	private Invoice invoice;
	
	@Column
	private int quantity;
	
	@Column
	private double price;

}
