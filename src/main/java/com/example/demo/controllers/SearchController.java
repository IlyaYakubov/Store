package com.example.demo.controllers;

import com.example.demo.repositories.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class SearchController {

    @Autowired
    private ItemRepository itemRepository;

    @GetMapping("/search")
    public String findItems(@RequestParam("search") String search, Model model) {
        model.addAttribute("items", itemRepository.findItemByName(search));
        return "index";
    }
}
