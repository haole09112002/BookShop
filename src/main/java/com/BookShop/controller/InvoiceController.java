package com.BookShop.controller;

import java.security.Principal;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.BookShop.entities.Invoice;
import com.BookShop.payload.InvoiceRequest;
import com.BookShop.services.InvoiceService;

@Controller
@RequestMapping("/bookshop")
public class InvoiceController {
	public static final Logger LOGGER = LoggerFactory.getLogger(InvoiceController.class);
	
	@Autowired
	private InvoiceService invoiceService;
	
	@GetMapping("/invoice")
	public String checkoutPage() {
		return "checkout";
	}
	
	@GetMapping("/invoice/complete")
	public String checkoutCompletePage() {
		return "checkout-complete";
	}
	
	@PostMapping("/invoice")
	@ResponseBody
	public boolean createInvoice(@RequestBody InvoiceRequest invoiceRequest, Model model, Principal principal) {
		LOGGER.info(invoiceRequest.toString());
		boolean isComplete = false;
		if(principal != null) {
			if(invoiceService.saveInvoice(invoiceRequest, principal.getName())) {
				isComplete = true;
			}
		}else {
			if(invoiceService.saveInvoice(invoiceRequest, null)) {
				isComplete = true;
			}
		}
		return isComplete;
	}
	
	@GetMapping("/admin/invoices")
	public String getListInvoices(Model model ,Authentication authentication) {
		if(authentication != null) {
			model.addAttribute("invoices", invoiceService.getAll()) ;
			return "/admin/order";
		}
		else {
			return "redirect:/bookshop/401";
		}
	}
	
	@GetMapping("/admin/invoices/{id}")
	public String invoiceDetail(@PathVariable long id, Model model, Authentication authentication) {
		if(authentication != null) {
			Invoice invoice = invoiceService.getById(id);
			model.addAttribute("invoice",invoice) ;
			model.addAttribute("invoiceDetails", invoice.getInvoiceDetails());
			return "/admin/invoice-details";
		}
		else {
			return "redirect:/bookshop/401";
		}
	}
	
	@GetMapping("/admin/invoices/search")
	public String findBykeyword(@RequestParam(required = true) String keyword, @RequestParam(required = true) String searchType , Model model, Authentication authentication) {
		if(authentication != null) {
			List<Invoice> invoices = invoiceService.getByKeyword(keyword, searchType);
			model.addAttribute("invoices",invoices) ;
		
		 return "/admin/order";
		}
		else {
			return "redirect:/bookshop/401";
		}
	}
}


