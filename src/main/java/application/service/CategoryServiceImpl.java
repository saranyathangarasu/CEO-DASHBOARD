package application.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import application.Dto.CategoryDto;
import application.model.Category;
import application.repo.CategoryRepository;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public void saveCategory(CategoryDto categoryDto) {
        Category category = new Category();
        category.setCategory(categoryDto.getCategory());
        categoryRepository.save(category);
    }

    @Override
    public Optional<Category> findById(Long id) {
        return categoryRepository.findById(id);
    }
    @Override
    public void saveCategory(Category category) { 
        categoryRepository.save(category);
    }
    
    public Category convertToEntity(CategoryDto categoryDto) {
        Category category = new Category();
        category.setId(categoryDto.getId());
        category.setCategory(categoryDto.getCategory());
        return category;
    }
    
    @Override
    public void deleteCategoryById(Long id) {
        categoryRepository.deleteById(id);
    }
}
