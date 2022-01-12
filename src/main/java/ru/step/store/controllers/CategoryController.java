package ru.step.store.controllers;

import org.hibernate.collection.internal.PersistentSet;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import ru.step.store.models.Category;
import ru.step.store.models.Role;
import ru.step.store.models.User;
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
    public String getCategory(@AuthenticationPrincipal User user) {
        if (!((Role) user.getRoles().toArray()[0]).name().equals("ADMIN")) {
            return "redirect:/";
        }
        return "admin/category";
    }

    @GetMapping("/categories")
    public String getCategories(Model model, @AuthenticationPrincipal User user) {
        if (!((Role) user.getRoles().toArray()[0]).name().equals("ADMIN")) {
            return "redirect:/";
        }
        model.addAttribute("categories", categoryRepository.findAll());
        return "admin/categories";
    }

    @PostMapping("/category/create")
    public String createCategory(@RequestParam("name") String name, @RequestParam("categoryId") String categoryId, @AuthenticationPrincipal User user) {
        if (!((Role) user.getRoles().toArray()[0]).name().equals("ADMIN")) {
            return "redirect:/";
        }
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
    public String editCategory(@PathVariable("id") Long id, Model model, @AuthenticationPrincipal User user) {
        if (!((Role) user.getRoles().toArray()[0]).name().equals("ADMIN")) {
            return "redirect:/";
        }
        model.addAttribute("category", categoryRepository.findById(id).get());
        return "admin/edit-category";
    }

    @PostMapping("/category/{id}")
    public String updateCategory(@PathVariable("id") Long id,
                                 @RequestParam("categoryId") String categoryId,
                                 @RequestParam("name") String name, @AuthenticationPrincipal User user) {
        if (!((Role) user.getRoles().toArray()[0]).name().equals("ADMIN")) {
            return "redirect:/";
        }
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
    public String deleteCategory(@PathVariable("id") Long id, @AuthenticationPrincipal User user) {
        if (!((Role) user.getRoles().toArray()[0]).name().equals("ADMIN")) {
            return "redirect:/";
        }
        categoryRepository.delete(categoryRepository.findById(id).get());
        return "redirect:/categories";
    }
}
