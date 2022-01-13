package ru.step.store.controllers;

import org.springframework.web.bind.annotation.*;
import ru.step.store.models.*;
import ru.step.store.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Controller
public class OrderController {

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderElementRepository orderElementRepository;

    @Autowired
    private RealizationRepository realizationRepository;

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

    @ResponseBody
    @PostMapping("/order/{id}/{quantity}")
    public String addInOrder(@PathVariable Long id, @PathVariable Long quantity, @AuthenticationPrincipal User user) {
        Item item = itemRepository.findById(id).get();
        OrderElement orderElement = new OrderElement();
        orderElement.setItem(item);
        orderElement.setQuantity(quantity);
        orderElement.setSum(item.getPrice().multiply(new BigInteger(String.valueOf(quantity))));
        orderElement.setUser(user);
        orderElementRepository.save(orderElement);
        return "Товар в корзине";
    }

    @PostMapping("/order/create")
    public String buyItems(@AuthenticationPrincipal User user) {
        Order order = new Order();
        order.setUser(user);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        order.setDate(LocalDateTime.now().format(formatter));
        List<OrderElement> orderElements = orderElementRepository.findOrderElementsByUserAndArranged(user, false);
        order.setOrderElements(orderElements);
        orderRepository.save(order);

        for (OrderElement orderElement : orderElements) {
            orderElement.setArranged(true);
            orderElementRepository.save(orderElement);
        }

        return "redirect:/";
    }

    @PostMapping("/order/delete/{id}")
    public String deleteItem(@PathVariable Long id) {
        orderElementRepository.delete(orderElementRepository.findById(id).get());
        return "redirect:/order";
    }

    @GetMapping("/buy/{id}")
    public String buyItems(Model model, @PathVariable Long id) {
        model.addAttribute("item", itemRepository.findItemById(id));
        return "/user/realization";
    }

    @PostMapping("/buy/{id}")
    public String buy(@PathVariable Long id, @RequestParam (value = "phone") String phone) {
        Item item = itemRepository.findById(id).get();
        OrderElement orderElement = new OrderElement();
        orderElement.setItem(item);
        orderElement.setQuantity(1L);
        orderElement.setSum(item.getPrice());
        orderElement.setUser(null);
        orderElementRepository.save(orderElement);

        Order order = new Order();
        order.setUser(null);
        order.setOrderElements(orderElementRepository.findOrderElementsByUserAndArranged(null, false));
        orderRepository.save(order);

        orderElement.setArranged(true);
        orderElementRepository.save(orderElement);

        Realization realization = new Realization();
        realization.setOrder(order);
        realization.setPhone(phone);
        realizationRepository.save(realization);

        return "redirect:/";
    }
}
