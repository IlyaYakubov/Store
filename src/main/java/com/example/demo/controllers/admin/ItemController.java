package com.example.demo.controllers.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import com.example.demo.model.Item;
import com.example.demo.service.ItemServiceImpl;

@Controller
public class ItemController {

    @GetMapping("/item")
    public String item() {
        return "admin/item";
    }

    @PostMapping("/item")
    public String createItem(@RequestParam("name") String name,
                      @RequestParam("brand") String brand,
                      @RequestParam("size") String size,
                      @RequestParam("color") String color,
                      @RequestParam("price") String price) {
        Item item = new Item();
        item.setId(ItemServiceImpl.INSTANCE.nextId());
        item.setName(name);
        item.setBrand(brand);
        item.setSize(size);
        item.setColor(color);
        item.setPrice(price);

        ItemServiceImpl.INSTANCE.add(item);

        return "redirect:/items";
    }

    @PostMapping("/item/{itemId}")
    public String editItem(@PathVariable("itemId") int itemId,
                       @RequestParam("name") String name) {
        for (Item item : ItemServiceImpl.INSTANCE.getAllItems()) {
            if (item.getId() == itemId) {
                item.setName(name);
            }
        }

        return "redirect:/items";
    }

    @GetMapping("/item/delete/{itemId}")
    public String removeItem(@PathVariable("itemId") int itemId) {
        ItemServiceImpl.INSTANCE.remove(ItemServiceImpl.INSTANCE.getById(itemId));
        return "redirect:/items";
    }
}
