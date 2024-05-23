package application;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import application.Dto.CategoryDto;
import application.model.Category;
import application.service.CategoryService;

@Controller
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/category")
    public String getCategoryPage(Model model) {
        List<Category> categoryDataList = categoryService.getAllCategories();
        model.addAttribute("categoryList", categoryDataList);
        model.addAttribute("categoryDto", new CategoryDto()); 
        return "category";
    }

    @PostMapping("/categoryAdd")
    public String addCategory(@ModelAttribute("categoryDto") CategoryDto categoryDto) {
        categoryService.saveCategory(categoryDto);
        return "redirect:/category";
    }

    @GetMapping("/categoryEdit/{categoryId}")
    public String editCategory(@PathVariable Long categoryId, Model model) {
        Optional<Category> optionalCategory = categoryService.findById(categoryId);
        if (optionalCategory.isPresent()) {
            Category category = optionalCategory.get();
            model.addAttribute("category", category);
            return "categoryEdit";
        } else {
            return "categoryNotFound";
        }
    }

    @PostMapping("/categoryUpdate")
    public String updateCategory(@ModelAttribute("categoryDto") CategoryDto categoryDto) {
        Category category = categoryService.convertToEntity(categoryDto);
        categoryService.saveCategory(category);
        return "redirect:/category";
    }
    
    @DeleteMapping("/categoryDelete/{categoryId}")
    public ResponseEntity<String> deleteCategory(@PathVariable Long categoryId) {
        categoryService.deleteCategoryById(categoryId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/api/categories")
    @ResponseBody
    public List<Category> getAllCategories() {
        return categoryService.getAllCategories();
    }


}
