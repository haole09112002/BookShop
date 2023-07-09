package com.BookShop.entities;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data

@Entity
@Table(name = "books")
public class Book {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Column(nullable = false, length = 150)
	private String title;
	
	@Column
	private String publisher;
	
	@Column
	private String publishYear;
	
	@Column
	private String language;
	
	@Column
	private String supplier;
	
	@Column
	private double weight;
	
	@Column
	private int pageCount;
	
	@Column
	private String formality;
	
	@Column
	private String decription;
	
	@Column
	private int ageRange;
	
	@Column
	private double price;
	
	@Column
	private String size;
	
	@Enumerated(EnumType.STRING)
	private BookStatusEnum status;
	
	@ManyToOne
	@JoinColumn(name = "categoryId")
	private Category category;
	
	@ManyToOne
	@JoinColumn(name = "authorId")
	private Author author;
	
	
	@OneToMany(mappedBy = "book", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)	// mac dinh la lazy
	private List<InvoiceDetail> invoiceDetails;
	
	@OneToMany(mappedBy = "book", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<BookImage> bookImages;
	
	
	
}
