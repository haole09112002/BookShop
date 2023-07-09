package com.BookShop.entities;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.CreatedBy;

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
import jakarta.persistence.OneToOne;
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
	
	@Column(columnDefinition = "TEXT")
	private String decription;
	
	@Column
	private int ageRange;
	
	@Column
	private double price;
	
	@Column
	private String size;
	
	@Column
	private int quantity;
	
	@Column 
	private LocalDateTime createdDate;
	
	@Column
	private long createdBy;
	
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
	
	@OneToMany(mappedBy = "book",  cascade = CascadeType.ALL)
	private List<BookImage> bookImages;
	
	@OneToMany(mappedBy = "book", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)	// mac dinh la lazy
	private List<CartItem> cartItems;
	
	@OneToMany(mappedBy = "book", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)	// mac dinh la lazy
	private List<Comment> comments;
	
	
	public List<BookImage> getBookImages(){
		return this.bookImages == null ? this.bookImages = new ArrayList<BookImage>() :this.bookImages;
	}
}
