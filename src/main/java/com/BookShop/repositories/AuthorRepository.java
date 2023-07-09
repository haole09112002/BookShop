package com.BookShop.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.BookShop.entities.Author;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long>{

}
