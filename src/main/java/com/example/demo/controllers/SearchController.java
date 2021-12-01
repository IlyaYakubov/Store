package com.example.demo.controllers;

import com.example.demo.models.Item;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.LinkedList;

@Controller
public class SearchController {

    @GetMapping("/search")
    public String editOrder(@RequestParam("search") String search, Model model) {
        LinkedList<Item> items = new LinkedList<>();
        /*for (Item item : ItemDAO.INSTANCE.getItems()) {
            if (search.equals(item.getName())) {
                items.add(item);
            }
        }*/
        model.addAttribute("items", items);
        //model.addAttribute("imgUtil", new ImageUtil());
        return "index";
    }
}
