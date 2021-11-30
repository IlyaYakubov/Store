package com.example.demo.controllers;

import com.example.demo.models.Item;
import com.example.demo.repositories.CategoryRepository;
import com.example.demo.repositories.ItemRepository;
import com.example.demo.storage.ImageUtil;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class ItemController {

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @GetMapping("/item/add")
    public String getItem() {
        return "admin/item";
    }

    @GetMapping("/items")
    public String getItems(Model model) {
        model.addAttribute("items", itemRepository.findAll());
        return "admin/items";
    }

    @SneakyThrows
    @PostMapping("/item/create")
    public String createItem(@RequestParam("categoryName") String categoryName,
                             @RequestParam("name") String name,
                             @RequestParam("brand") String brand,
                             @RequestParam("size") String size,
                             @RequestParam("color") String color,
                             @RequestParam("price") String price,
                             @RequestParam("image") MultipartFile image) {
        Item item = new Item();
        item.setCategory(categoryRepository.findCategoryByName(categoryName));
        item.setName(name);
        item.setSize(size);
        item.setPrice(price);

        /*InputStream inputStream = image.getInputStream();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        int read;
        final byte[] bytes = new byte[1024];
        while ((read = inputStream.read(bytes)) != -1) {
            outputStream.write(bytes, 0, read);
        }
        Image newImage = new Image();
        newImage.setName(image.getName());
        newImage.setContents(outputStream.toByteArray());
        ImageDAO.INSTANCE.add(newImage);
        item.setImage(newImage);*/

        itemRepository.save(item);

        return "redirect:/items";
    }

    @GetMapping("/item/{id}")
    public String editItem(@PathVariable("id") Long id, Model model) {
        model.addAttribute("item", itemRepository.findById(id).get());
        model.addAttribute("imgUtil", new ImageUtil());
        return "admin/edit-item";
    }

    @GetMapping("/catalog/items/{id}")
    public String getItem(@PathVariable("id") Long id, Model model) {
        model.addAttribute("item", itemRepository.findById(id).get());
        model.addAttribute("imgUtil", new ImageUtil());
        return "user/item";
    }

    @PostMapping("/item/{id}")
    public String updateItem(@PathVariable("id") Long id,
                             @RequestParam("categoryName") String categoryName,
                             @RequestParam("name") String name,
                             @RequestParam("brand") String brand,
                             @RequestParam("size") String size,
                             @RequestParam("color") String color,
                             @RequestParam("price") String price) {
        Item item = itemRepository.findById(id).get();
        item.setCategory(categoryRepository.findCategoryByName(categoryName));
        item.setName(name);
        item.setSize(size);
        item.setPrice(price);
        itemRepository.save(item);

        return "redirect:/items";
    }

    @GetMapping("/item/delete/{id}")
    public String deleteItem(@PathVariable("id") Long id) {
        itemRepository.delete(itemRepository.findById(id).get());
        return "redirect:/items";
    }
}
