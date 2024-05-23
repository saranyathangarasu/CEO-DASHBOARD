package application.service;

import java.util.List;
import java.util.Optional;

import application.Dto.CategoryDto;
import application.model.Category;

public interface CategoryService {
	 List<Category> getAllCategories();
	 void saveCategory(CategoryDto categoryDto);
	 Optional<Category> findById(Long id);
	 void saveCategory(Category category);
	 Category convertToEntity(CategoryDto categoryDto);
	 void deleteCategoryById(Long id);
}
