package ru.step.store.controllers;

import ru.step.store.models.Category;
import ru.step.store.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class CategoryController {

    @Autowired
    private CategoryRepository categoryRepository;

    @GetMapping("/category/add")
    public String getCategory() {
        return "admin/category";
    }

    @GetMapping("/categories")
    public String getCategories(Model model) {
        model.addAttribute("categories", categoryRepository.findAll());
        return "admin/categories";
    }

    @PostMapping("/category/create")
    public String createCategory(@RequestParam("name") String name, @RequestParam("categoryId") String categoryId) {
        Category category = new Category();
        category.setName(name);
        if (categoryId.isEmpty()) {
            categoryId = "1";
        }
        category.setCategory(categoryRepository.findCategoryById(Long.parseLong(categoryId)));
        categoryRepository.save(category);

        return "redirect:/categories";
    }

    @GetMapping("/categories/{id}")
    public String editCategory(@PathVariable("id") Long id, Model model) {
        model.addAttribute("category", categoryRepository.findById(id).get());
        return "admin/edit-category";
    }

    @PostMapping("/category/{id}")
    public String updateCategory(@PathVariable("id") Long id,
                                 @RequestParam("categoryId") String categoryId,
                                 @RequestParam("name") String name) {
        if (!categoryId.isEmpty()) {
            if (Long.parseLong(categoryId) == id) {
                return "redirect:/categories";
            }
        }
        Category category = categoryRepository.findById(id).get();
        category.setName(name);
        if (categoryId.isEmpty()) {
            categoryId = "1";
        }
        category.setCategory(categoryRepository.findCategoryById(Long.parseLong(categoryId)));
        categoryRepository.save(category);

        return "redirect:/categories";
    }

    @GetMapping("/category/delete/{id}")
    public String deleteCategory(@PathVariable("id") Long id) {
        categoryRepository.delete(categoryRepository.findById(id).get());
        return "redirect:/categories";
    }
}
