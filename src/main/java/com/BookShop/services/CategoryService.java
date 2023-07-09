package com.BookShop.services;

import java.util.List;

import com.BookShop.entities.Category;

public interface CategoryService {
	Category findById(long id);
	
	List<Category> findByParentCategoryId(long parentId);
	
	List<Category> findAll();
	
	List<Category> findRootCategory();
}
