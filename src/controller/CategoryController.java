package controller;

import model.Category;
import service.CategoryService;

import java.util.List;

public class CategoryController {
    private final CategoryService categoryService;

    public CategoryController() {
        this.categoryService = new CategoryService();
    }

    public List<Category> getAllCategories() {
        return categoryService.getAllCategories();
    }

    public boolean addCategory(Category category) {
        return categoryService.addCategory(category);
    }

    public boolean deleteCategory(int id) {
        return categoryService.deleteCategory(id);
    }
}
