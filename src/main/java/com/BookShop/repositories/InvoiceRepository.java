package com.BookShop.repositories;


import java.util.List;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.BookShop.entities.Invoice;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, Long>{

	
	@Query("select inv from Invoice inv where LOWER(inv.contact.email) like LOWER(CONCAT('%', :email, '%'))")
	List<Invoice> findByContactEmail(String email);
	
	@Query("select inv from Invoice inv where LOWER(inv.contact.phone) like LOWER(CONCAT('%', :phone, '%'))")
	List<Invoice> findByContactPhone(String phone);
}
