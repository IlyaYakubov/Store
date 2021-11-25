package com.example.demo.controllers.user;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.example.demo.services.ItemServiceImpl;
import com.example.demo.services.OrderServiceImpl;

@Controller
public class OrderController {

    @PostMapping("/order/{itemId}")
    public String editOrder(@PathVariable("itemId") int itemId) {
        OrderServiceImpl.INSTANCE.add(ItemServiceImpl.INSTANCE.getById(itemId));
        return "redirect:/";
    }

    @GetMapping("/order")
    public String order(Model model) {
        model.addAttribute("items", OrderServiceImpl.INSTANCE.getAllItems());
        return "/user/order";
    }

    @GetMapping("/order/create")
    public String createOrder() {
        OrderServiceImpl.INSTANCE.clear();
        return "redirect:/";
    }
}
