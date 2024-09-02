package com.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.entities.Category;
import com.exceptions.ResourceNotFoundException;
import com.payloads.CategoryDto;
import com.repo.CategoryRepo;
@Service
public class CategoryServiceImpl implements CategoryService {
	
	@Autowired
    private CategoryRepo categoryRepo;
	
	@Autowired
	private ModelMapper modelMapper;
	
	
	@Override
	public CategoryDto createCategory(CategoryDto categoryDto) {
		Category category=modelMapper.map(categoryDto, Category.class);
		Category save=categoryRepo.save(category);
		return  modelMapper.map(save, CategoryDto.class);
	}

	@Override
	public CategoryDto updateCategory(CategoryDto categoryDto, int categoryId) {
	      Category category=categoryRepo.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("Category", "CategoryId", categoryId));
		category.setCategoryTitle(categoryDto.getCategoryTitle());
		category.setCategoryDescripation(categoryDto.getCategoryDescripation());
		Category save=categoryRepo.save(category);
		return  modelMapper.map(save, CategoryDto.class);
	}

	@Override
	public void deleteCategory(int categoryId) {
		 Category category=categoryRepo.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("Category", "CategoryId", categoryId));
		 categoryRepo.delete(category);
	}

	@Override
	public CategoryDto getCategoryById(int categoryId) {
		 Category category=categoryRepo.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("Category", "CategoryId", categoryId));
		 return  modelMapper.map(category, CategoryDto.class);
	}

	@Override
	public List<CategoryDto> getAllCategories() {
	List<Category>categories=categoryRepo.findAll();
	List<CategoryDto>categoryDto=categories.stream().map(category->modelMapper.map(category, CategoryDto.class)).collect(Collectors.toList());
		return categoryDto;
	}

}
