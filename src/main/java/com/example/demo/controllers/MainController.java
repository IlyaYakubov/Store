package com.example.demo.controllers;

import com.example.demo.model.ImageUtil;
import com.example.demo.repository.ItemDAO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("items", ItemDAO.INSTANCE.getItems());
        model.addAttribute("imgUtil", new ImageUtil());
        return "index";
    }
}
