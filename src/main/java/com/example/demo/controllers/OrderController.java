package com.example.demo.controllers;

import com.example.demo.models.Cart;
import com.example.demo.models.Item;
import com.example.demo.models.Order;
import com.example.demo.repositories.ItemRepository;
import com.example.demo.repositories.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class OrderController {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private ItemRepository itemRepository;

    @GetMapping("/order")
    public String getOrder(Model model) {
        model.addAttribute("cart", cartRepository.findAll());
        return "/user/order";
    }

    @PostMapping("/order/{id}")
    public String addInOrderItems(@PathVariable Long id) {
        Item item = itemRepository.findById(id).get();
        Cart cart = new Cart();
        cart.setItem(item);
        cartRepository.save(cart);
        return "redirect:/";
    }

    @GetMapping("/order/create")
    public String buyItems() {
        Order order = new Order();
        //order.setUser();
        cartRepository.deleteAll();
        return "redirect:/";
    }
}
