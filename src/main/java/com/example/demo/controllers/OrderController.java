package com.example.demo.controllers;

import com.example.demo.models.Cart;
import com.example.demo.models.Item;
import com.example.demo.models.Order;
import com.example.demo.repositories.CartRepository;
import com.example.demo.repositories.ItemRepository;
import com.example.demo.services.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class OrderController {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private CartService cartService;

    @GetMapping("/order")
    public String getOrder(Model model) {
        model.addAttribute("cart", cartRepository.findAll());
        model.addAttribute("sum", cartService.getSum());
        return "/user/order";
    }

    @PostMapping("/order/{id}")
    public String addInCart(@PathVariable Long id) {
        Item item = itemRepository.findById(id).get();
        Cart cart = new Cart();
        cart.setItem(item);
        cart.setSum(item.getPrice());
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
