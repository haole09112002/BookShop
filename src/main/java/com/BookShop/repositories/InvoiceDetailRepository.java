package com.BookShop.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.BookShop.entities.InvoiceDetail;

@Repository
public interface InvoiceDetailRepository extends JpaRepository<InvoiceDetail, Long> {
	Optional<InvoiceDetail> findByBookIdAndInvoiceCreatedBy(Long bookId, String username);
}
