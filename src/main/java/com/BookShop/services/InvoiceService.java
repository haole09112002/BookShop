package com.BookShop.services;

import java.util.List;

import com.BookShop.entities.Invoice;
import com.BookShop.payload.InvoiceRequest;



public interface InvoiceService {

	boolean userHavePurchasedBook(String username, Long bookId);

	void sendEmail(Invoice invoice);

	boolean saveInvoice(InvoiceRequest invoiceRequest, String username);
	
	List<Invoice> getAll();

	Invoice getById(Long id);
	
	List<Invoice> getByKeyword(String keyword, String searchType);
}
