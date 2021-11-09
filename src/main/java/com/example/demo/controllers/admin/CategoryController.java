package com.example.demo.controllers.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import com.example.demo.model.Category;
import com.example.demo.service.CategoryServiceImpl;

@Controller
public class CategoryController {

    @GetMapping("/category")
    public String category() {
        return "admin/category";
    }

    @PostMapping("/category")
    public String createCategory(@RequestParam("name") String name) {
        Category category = new Category();
        category.setId(CategoryServiceImpl.INSTANCE.nextId());
        category.setName(name);
        CategoryServiceImpl.INSTANCE.add(category);

        return "redirect:/categories";
    }

    @PostMapping("/category/{categoryId}")
    public String editCategory(@PathVariable("categoryId") int categoryId,
                      @RequestParam("name") String name) {
        for (Category group : CategoryServiceImpl.INSTANCE.getAllCategories()) {
            if (group.getId() == categoryId) {
                group.setName(name);
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
