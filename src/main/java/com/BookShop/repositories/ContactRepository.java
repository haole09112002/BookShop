package com.BookShop.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.BookShop.entities.Contact;

@Repository
public interface ContactRepository extends JpaRepository<Contact, Long> {

}
