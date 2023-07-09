package com.BookShop.services.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.BookShop.entities.Address;
import com.BookShop.entities.Book;
import com.BookShop.entities.Contact;
import com.BookShop.entities.Invoice;
import com.BookShop.entities.InvoiceDetail;
import com.BookShop.payload.InvoiceRequest;
import com.BookShop.payload.Item;
import com.BookShop.repositories.AddressRepository;
import com.BookShop.repositories.BookRepository;
import com.BookShop.repositories.CartItemRepository;
import com.BookShop.repositories.ContactRepository;
import com.BookShop.repositories.InvoiceDetailRepository;
import com.BookShop.repositories.InvoiceRepository;
import com.BookShop.services.InvoiceService;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.transaction.Transactional;

@Service
public class InvoiceServiceImpl implements InvoiceService {

	@Autowired
	private BookRepository bookRepository;
	
	@Autowired
	private JavaMailSender mailSender;
	
	@Autowired
	private AddressRepository addressRepository;
	
	@Autowired
	private ContactRepository contactRepository;
	
	@Autowired
	private InvoiceDetailRepository invoiceDetailRepository;
	
	@Autowired
	private InvoiceRepository invoiceRepository;
	
	@Autowired
	private CartItemRepository cartItemRepository;
	
	@Override
	@Transactional(rollbackOn = Exception.class)
	public boolean saveInvoice(InvoiceRequest invoiceRequest, String username) {
		Invoice invoice = new Invoice();
		invoice.setDecription(invoiceRequest.getMessage());
		invoice.setCreatedDate(LocalDateTime.now());
		invoice = invoiceRepository.save(invoice);
		
		Contact contact = new Contact();
		contact.setFullname(invoiceRequest.getFullname());
		contact.setEmail(invoiceRequest.getEmail());
		contact.setMessage(invoiceRequest.getMessage());
		contact.setPhone(invoiceRequest.getPhone());
		contact.setInvoice(invoice);
		
		contact = contactRepository.save(contact);
		
		invoice.setContact(contact);
		
		
		Address address = new Address();
		address.setProvince(invoiceRequest.getProvince());
		address.setStreet(invoiceRequest.getStreet());
		address.setWard(invoiceRequest.getWard());
		address.setDistrict(invoiceRequest.getDistrict());
		address.setContact(contact);
		address = addressRepository.save(address);
		contact.setAddress(address);
		
		double totalAmount = 0;
		for (Item item : invoiceRequest.getCartItems()) {
			Book book = bookRepository.findById(item.getId()).orElseThrow(()-> new RuntimeException("LL"));
			if(item.getQuantity() > book.getQuantity()) {
				return false;
			}
			else {
				InvoiceDetail invoiceDetail = new InvoiceDetail();
				invoiceDetail.setPrice(book.getPrice());
				invoiceDetail.setQuantity(item.getQuantity());
				invoiceDetail.setBook(book);
				invoiceDetail.setInvoice(invoice);
//				invoiceDetailRepository.save(invoiceDetail)	;
				invoice.addInvoiceDetail(invoiceDetail);
				if(username != null) {
					
					cartItemRepository.delete(cartItemRepository.findByUsernameAndBookId(username, book.getId()));
					invoice.setCreatedBy(username);
				}
				
				
				book.setQuantity(book.getQuantity() - item.getQuantity());
				bookRepository.save(book);
				
				totalAmount += item.getQuantity() * book.getPrice();
			}
		}	
		
		invoice.setTotalAmount(totalAmount + 30000);
		// send mail
		invoiceRepository.save(invoice);
		
		
		
		
		invoice = invoiceRepository.findById(invoice.getId()).orElseThrow(()-> new RuntimeException("loi"));
		sendEmail(invoice);
		return true;
	}
	
	
	@Override
	public void sendEmail(Invoice invoice) {
		
		if(invoice.getInvoiceDetails().size() <= 0) {
			System.out.print("Sai toe loe");
		}
	
		
		String to = invoice.getContact().getEmail();
		String subject =  "Thông báo xác nhận đơn hàng";
	
		String senderName = "Shop ABC";
		String mailContent = "<p> Dear " + invoice.getContact().getFullname() + "</p>";
		mailContent += "<h2> Thông tin đơn hàng " + invoice.getId() + "</h2>";

		for (InvoiceDetail cartItem : invoice.getInvoiceDetails()) {
			mailContent += "<p> " + cartItem.getBook().getTitle() + " X " + cartItem.getQuantity()
					+ "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" + cartItem.getPrice()
					+ "</p>";
		}
		mailContent += "<h4> Tổng Cộng:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"
				+ invoice.getTotalAmount() + "</h4>";
		mailContent += "<p>-------------------------------------------------------------</p>";
		mailContent += "<h2> Thông tin khách hàng </h2>";
		mailContent += "<p> Tên Khách Hàng: " + invoice.getContact().getFullname() + "</p>";
		mailContent += "<p> Số điện thoại liên lạc: " + invoice.getContact().getPhone() + "</p>";
		mailContent += "<p> Địa chỉ: " + invoice.getContact().getAddress().getStreet() + "</p>";
		mailContent += "<p>Cám ơn bạn đã mua hàng!</p>";
		mailContent += "<p>Shop ABC</p>";

			MimeMessage message = mailSender.createMimeMessage();
		     
			MimeMessageHelper helper;
			try {
				helper = new MimeMessageHelper(message, true, "UTF-8");
				helper.setFrom("lhai09555@gmail.com");
				helper.setTo(to);
				
				helper.setSubject(subject);
			    helper.setText(mailContent, true);
				mailSender.send(message);	
			} 
			catch (MessagingException e) {
				e.printStackTrace();
			}
		}	
	
	
	
	@Override
	public boolean userHavePurchasedBook(String username, Long bookId) {
	 return	invoiceDetailRepository.findByBookIdAndInvoiceCreatedBy(bookId, username).isPresent() ? true : false;
	}


	@Override
	public List<Invoice> getAll() {
		
		return invoiceRepository.findAll();
	}


	@Override
	public Invoice getById(Long id) {
		Invoice invoice = invoiceRepository.findById(id).orElseThrow(()-> new RuntimeException("Không tìm thấy hóa đơn"));
		return invoice;
	}


	@Override
	public List<Invoice> getByKeyword(String keyword,String searchType) {
		List<Invoice> invoices = new ArrayList<Invoice>();
		if(searchType.equals("id")) {
			Long id;
			try {
				id = Long.parseLong(keyword);
				
			} catch (Exception e) {
				return null;
			}
			Invoice invoice =	invoiceRepository.findById(id).orElse(null);
			if(invoice == null)
				return null;
			invoices.add(invoice);
			
		}
		else if(searchType.equals("phone")) {
		
			
			invoices.addAll(invoiceRepository.findByContactPhone(keyword));
		}
		else if(searchType.equals("email")) {
	
			invoices.addAll(invoiceRepository.findByContactEmail(keyword));
		}
		else {
			return null;
		}
		return invoices;
	}
	
	
	
	
}
