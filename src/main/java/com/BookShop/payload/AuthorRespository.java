package com.BookShop.payload;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.BookShop.entities.Author;

@Repository
public interface AuthorRespository extends JpaRepository<Author, Long> {

}
