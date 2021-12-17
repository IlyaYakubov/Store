package ru.step.store.controllers;

import ru.step.store.models.*;
import ru.step.store.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.math.BigInteger;
import java.util.List;

@Controller
public class OrderController {

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderElementRepository orderElementRepository;

    @GetMapping("/order")
    public String getOrder(Model model, @AuthenticationPrincipal User user) {
        model.addAttribute("cart", orderElementRepository.findOrderElementsByUserAndArranged(user, false));

        BigInteger sum = new BigInteger("0");
        for (OrderElement orderElement : orderElementRepository.findOrderElementsByUserAndArranged(user, false)) {
            sum = sum.add(orderElement.getSum());
        }
        model.addAttribute("sum", sum);
        return "/user/order";
    }

    @PostMapping("/order/{id}")
    public String addInOrder(@PathVariable Long id, @AuthenticationPrincipal User user) {
        Item item = itemRepository.findById(id).get();
        OrderElement orderElement = new OrderElement();
        orderElement.setItem(item);
        orderElement.setSum(item.getPrice());
        orderElement.setUser(user);
        orderElementRepository.save(orderElement);
        return "redirect:/";
    }

    @GetMapping("/order/create")
    public String buyItems(@AuthenticationPrincipal User user) {
        Order order = new Order();
        order.setUser(user);
        List<OrderElement> orderElements = orderElementRepository.findOrderElementsByUserAndArranged(user, false);
        order.setOrderElements(orderElements);
        orderRepository.save(order);

        for (OrderElement orderElement : orderElements) {
            orderElement.setArranged(true);
            orderElementRepository.save(orderElement);
        }

        return "redirect:/";
    }

    @PostMapping("/buy/{id}")
    public String buyItems(@PathVariable Long id) {
        Item item = itemRepository.findById(id).get();
        OrderElement orderElement = new OrderElement();
        orderElement.setItem(item);
        orderElement.setSum(item.getPrice());
        orderElement.setUser(null);
        orderElementRepository.save(orderElement);

        Order order = new Order();
        order.setUser(null);
        order.setOrderElements(orderElementRepository.findOrderElementsByUserAndArranged(null, false));
        orderRepository.save(order);

        orderElement.setArranged(true);
        orderElementRepository.save(orderElement);
        return "redirect:/";
    }
}
