package com.BookShop.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.BookShop.entities.Category;
import com.BookShop.exceptions.NotFoundException;
import com.BookShop.payload.CategoryRequest;
import com.BookShop.repositories.CategoryRepository;
import com.BookShop.services.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService{

	@Autowired
	private CategoryRepository categoryRepo;
	
	@Override
	public Category findById(long id) {
		return categoryRepo.findById(id).orElseThrow(()-> new NotFoundException("Không tìm thấy danh mục có id: " + id));
	}

	@Override
	public List<Category> findByParentCategoryId(long parentId) {
		return categoryRepo.findByParentCategoryId(parentId);
	}

	@Override
	public List<Category> findAll() {
		return categoryRepo.findAll();
	}

	@Override
	public List<Category> findRootCategory(){
		return categoryRepo.findRootCategory();
	}

	@Override
	public List<Category> findTop5() {
		return categoryRepo.findTop5();
	}
	
	@Override
	public Category save(CategoryRequest categoryRequest) {
		Category newCategory = new Category();
		newCategory.setName(categoryRequest.getName());
		newCategory.setDecription(categoryRequest.getDescription());
		return categoryRepo.save(newCategory);
	}
}
