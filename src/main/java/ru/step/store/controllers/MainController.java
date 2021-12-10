package ru.step.store.controllers;

import ru.step.store.models.Item;
import ru.step.store.repositories.CategoryRepository;
import ru.step.store.repositories.ItemRepository;
import ru.step.store.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/")
public class MainController {

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @GetMapping
    public String index(@AuthenticationPrincipal User user, Model model) {
        model.addAttribute("items", itemRepository.findAll());
        model.addAttribute("categories", categoryRepository.findAll());

        model.addAttribute("user", "anonymous");
        if (user != null) {
            model.addAttribute("user", user.getUsername());
        }
        return "index";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/catalog/items/{id}")
    public String getItem(@AuthenticationPrincipal User user, @PathVariable("id") Long id, Model model) {
        Item item = itemRepository.findById(id).get();
        model.addAttribute("item", item);
        model.addAttribute("filename", item.getFilename());

        model.addAttribute("user", "anonymous");
        if (user != null) {
            model.addAttribute("user", user.getUsername());
        }
        return "user/item";
    }
}
