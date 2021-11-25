package com.example.demo.controllers.admin;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import com.example.demo.services.CategoryServiceImpl;

@Controller
public class CategoriesController {

    @GetMapping("/categories")
    public String categories(Model model) {
        model.addAttribute("categories", CategoryServiceImpl.INSTANCE.getAllCategories());
        return "admin/categories";
    }

    @GetMapping("/categories/{id}")
    public String updateCategory(@PathVariable("id") int id, Model model) {
        model.addAttribute("category", CategoryServiceImpl.INSTANCE.getById(id));
        return "admin/edit-category";
    }
}
