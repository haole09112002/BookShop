package com.BookShop.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.BookShop.entities.BookImage;

@Repository
public interface BookImageRepository extends JpaRepository<BookImage, Long> {
	List<BookImage> findByBookId(long bookId);
}
