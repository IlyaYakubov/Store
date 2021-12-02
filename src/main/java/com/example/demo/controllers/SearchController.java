package com.example.demo.controllers;

import com.example.demo.repositories.CategoryRepository;
import com.example.demo.repositories.ItemRepository;
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
    public String findItems(@RequestParam("search") String search, Model model) {
        model.addAttribute("items", itemRepository.findItemByName(search));
        return "index";
    }

    @GetMapping("/search/{categoryId}")
    public String filter(@PathVariable Long categoryId, Model model) {
        model.addAttribute("items", itemRepository.findItemByCategory_Id(categoryId));
        model.addAttribute("categories", categoryRepository.findAll());
        return "index";
    }
}
