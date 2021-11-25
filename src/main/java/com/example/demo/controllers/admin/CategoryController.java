package com.example.demo.controllers.admin;

import com.example.demo.models.Category;
import com.example.demo.services.CategoryServiceImpl;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class CategoryController {

    @GetMapping("/category/add")
    public String category() {
        return "admin/category";
    }

    @PostMapping("/category/create")
    public String createCategory(@RequestParam("name") String name, @RequestParam("categoryId") String categoryId) {
        Category newCategory = new Category();
        newCategory.setId(CategoryServiceImpl.INSTANCE.nextId());
        newCategory.setName(name);

        for (Category category : CategoryServiceImpl.INSTANCE.getAllCategories()) {
            try {
                if (category.getId() == Integer.parseInt(categoryId)) {
                    newCategory.setCategoryId(Integer.parseInt(categoryId));
                }
            } catch (NumberFormatException e) {
            }
        }
        CategoryServiceImpl.INSTANCE.add(newCategory);

        return "redirect:/categories";
    }

    @PostMapping("/category/{id}")
    public String editCategory(@RequestParam("categoryId") String categoryId,
                               @PathVariable("id") int id,
                               @RequestParam("name") String name) {
        for (Category category : CategoryServiceImpl.INSTANCE.getAllCategories()) {
            if (category.getId() == id) {
                try {
                    category.setCategoryId(Integer.parseInt(categoryId));
                } catch (NumberFormatException e) {}
                category.setName(name);
            }
        }

        return "redirect:/categories";
    }

    @GetMapping("/category/delete/{categoryId}")
    public String removeCategory(@PathVariable("categoryId") int categoryId) {
        CategoryServiceImpl.INSTANCE.remove(CategoryServiceImpl.INSTANCE.getById(categoryId));
        return "redirect:/categories";
    }
}
