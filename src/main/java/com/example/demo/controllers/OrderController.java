package com.example.demo.controllers;

import com.example.demo.models.Item;
import com.example.demo.models.Order;
import com.example.demo.models.OrderElement;
import com.example.demo.models.User;
import com.example.demo.repositories.ItemRepository;
import com.example.demo.repositories.OrderElementRepository;
import com.example.demo.repositories.OrderRepository;
import com.example.demo.repositories.UserRepository;
import com.example.demo.services.OrderElementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class OrderController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OrderElementRepository orderElementRepository;

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderElementService orderElementService;

    @GetMapping("/order")
    public String getOrder(Model model) {
        model.addAttribute("cart", orderElementRepository.findAll());
        model.addAttribute("sum", orderElementService.getSum());
        return "/user/order";
    }

    @PostMapping("/order/{id}")
    public String addInOrder(@PathVariable Long id) {
        Item item = itemRepository.findById(id).get();
        OrderElement orderElement = new OrderElement();
        orderElement.setItem(item);
        orderElement.setSum(item.getPrice());
        orderElementRepository.save(orderElement);
        return "redirect:/";
    }

    @GetMapping("/order/create")
    public String buyItems(@AuthenticationPrincipal User user) {
        Order order = new Order();
        order.setUser(user);
        order.setOrderElements((List<OrderElement>) orderElementRepository.findAll());
        orderRepository.save(order);
        orderElementRepository.deleteAll();
        return "redirect:/";
    }

    @PostMapping("/buy/{id}")
    public String buyItems(@PathVariable Long id) {
        Item item = itemRepository.findById(id).get();
        OrderElement orderElement = new OrderElement();
        orderElement.setItem(item);
        orderElement.setSum(item.getPrice());
        orderElementRepository.save(orderElement);

        Order order = new Order();
        order.setUser(null);
        order.setOrderElements((List<OrderElement>) orderElementRepository.findAll());
        orderRepository.save(order);
        return "redirect:/";
    }
}
