package com.BookShop.entities;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity

@Table(name = "invoices")

public class Invoice {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@Column
	private double totalAmount;
	@Column
	private String decription;
	@Column
	private LocalDateTime createdDate;
	@Column(nullable = true)
	private String createdBy;
	
	@OneToOne(mappedBy = "invoice", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
	private Contact contact;
	
	@OneToMany(mappedBy = "invoice", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	private List<InvoiceDetail> invoiceDetails;

	public List<InvoiceDetail> getInvoiceDetails(){
		return invoiceDetails == null ? new ArrayList<InvoiceDetail>(this.invoiceDetails) : this.invoiceDetails;
	}
	 public void addInvoiceDetail(InvoiceDetail invoiceDetail) {
		 if(this.invoiceDetails == null){
			 this.invoiceDetails = new ArrayList<InvoiceDetail>();
		 }
	        invoiceDetails.add(invoiceDetail);
	        invoiceDetail.setInvoice(this);
	    }
}
