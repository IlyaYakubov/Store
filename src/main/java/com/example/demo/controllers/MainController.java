package com.example.demo.controllers;

import com.example.demo.repositories.ItemRepository;
import com.example.demo.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/")
public class MainController {

    @Autowired
    private ItemRepository itemRepository;

    @GetMapping
    public String index(@AuthenticationPrincipal User user, Model model) {
        model.addAttribute("items", itemRepository.findAll());
        if (user != null) {
            model.addAttribute("user", user.getUsername());
            return "index";
        }
        model.addAttribute("user", "anonymous");
        return "index";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }
}
