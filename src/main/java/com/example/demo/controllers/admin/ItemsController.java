package com.example.demo.controllers.admin;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import com.example.demo.service.ItemServiceImpl;

@Controller
public class ItemsController {

    @GetMapping("/items")
    public String items(Model model) {
        model.addAttribute("items", ItemServiceImpl.INSTANCE.getAllItems());
        return "admin/items";
    }

    @GetMapping("/items/{itemId}")
    public String editItem(@PathVariable("itemId") int itemId, Model model) {
        model.addAttribute("item", ItemServiceImpl.INSTANCE.getById(itemId));
        return "admin/edit-item";
    }

    @GetMapping("/catalog/items/{itemId}")
    public String item(@PathVariable("itemId") int itemId, Model model) {
        model.addAttribute("item", ItemServiceImpl.INSTANCE.getById(itemId));
        return "user/item";
    }
}
