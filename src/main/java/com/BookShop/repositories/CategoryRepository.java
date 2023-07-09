package com.BookShop.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.BookShop.entities.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long>{
	
	List<Category> findByParentCategoryId(long id);
	
	@Query("select c from Category c where c.parentCategory = null")
	List<Category> findRootCategory();
}
