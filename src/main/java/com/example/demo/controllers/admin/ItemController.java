package com.example.demo.controllers.admin;

import com.example.demo.model.Category;
import com.example.demo.model.Image;
import com.example.demo.model.Item;
import com.example.demo.repository.ImageDAO;
import com.example.demo.service.CategoryServiceImpl;
import com.example.demo.service.ItemServiceImpl;
import lombok.SneakyThrows;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.Base64;

@Controller
public class ItemController {

    @GetMapping("/item/add")
    public String item() {
        return "admin/item";
    }

    @SneakyThrows
    @PostMapping("/item/create")
    public String createItem(@RequestParam("categoryId") String categoryId,
                             @RequestParam("name") String name,
                             @RequestParam("brand") String brand,
                             @RequestParam("size") String size,
                             @RequestParam("color") String color,
                             @RequestParam("price") String price,
                             @RequestParam("image") MultipartFile image) {
        Item item = new Item();

        for (Category category : CategoryServiceImpl.INSTANCE.getAllCategories()) {
            try {
                if (category.getId() == Integer.parseInt(categoryId)) {
                    item.setCategoryId(Integer.parseInt(categoryId));
                }
            } catch (NumberFormatException e) {}
        }

        item.setId(ItemServiceImpl.INSTANCE.nextId());
        item.setName(name);
        item.setBrand(brand);
        item.setSize(size);
        item.setColor(color);
        item.setPrice(price);

        InputStream inputStream = image.getInputStream();
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

        ItemServiceImpl.INSTANCE.add(item);

        item.setImage(newImage);

        return "redirect:/items";
    }

    @PostMapping("/item/{itemId}")
    public String editItem(@RequestParam("categoryId") String categoryId,
                           @PathVariable("itemId") int itemId,
                           @RequestParam("name") String name) {
        for (Item item : ItemServiceImpl.INSTANCE.getAllItems()) {
            if (item.getId() == itemId) {
                try {
                    item.setCategoryId(Integer.parseInt(categoryId));
                } catch (NumberFormatException e) {}
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
