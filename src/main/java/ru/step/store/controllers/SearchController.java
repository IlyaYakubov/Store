package ru.step.store.controllers;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import ru.step.store.models.User;
import ru.step.store.repositories.CategoryRepository;
import ru.step.store.repositories.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class SearchController {

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @GetMapping("/search")
    public String findItems(@RequestParam("search") String search, Model model, @AuthenticationPrincipal User user) {
        model.addAttribute("items", itemRepository.findItemByName(search));
        model.addAttribute("user", "anonymous");

        if (user != null) {
            model.addAttribute("user", user.getUsername());
        }
        return "index";
    }

    @GetMapping("/search/{categoryId}")
    public String filter(@PathVariable Long categoryId, Model model, @AuthenticationPrincipal User user) {
        model.addAttribute("items", itemRepository.findItemByCategory_Id(categoryId));
        model.addAttribute("categories", categoryRepository.findAll());

        model.addAttribute("user", "anonymous");
        if (user != null) {
            model.addAttribute("user", user.getUsername());
        }
        return "index";
    }
}
