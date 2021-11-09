package com.example.demo.controllers.admin;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import com.example.demo.service.CategoryServiceImpl;

@Controller
public class CategoriesController {

    @GetMapping("/categories")
    public String categories(Model model) {
        model.addAttribute("categories", CategoryServiceImpl.INSTANCE.getAllCategories());
        return "admin/categories";
    }

    @GetMapping("/categories/{categoryId}")
    public String createCategory(@PathVariable("categoryId") int categoryId, Model model) {
        model.addAttribute("category", CategoryServiceImpl.INSTANCE.getById(categoryId));
        return "admin/edit-category";
    }
}
