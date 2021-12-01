package com.example.demo.controllers;

import com.example.demo.models.Brand;
import com.example.demo.models.Color;
import com.example.demo.models.Item;
import com.example.demo.repositories.BrandRepository;
import com.example.demo.repositories.CategoryRepository;
import com.example.demo.repositories.ColorRepository;
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

    @Autowired
    private BrandRepository brandRepository;

    @Autowired
    private ColorRepository colorRepository;

    @GetMapping("/item/add")
    public String getItem() {
        return "admin/item";
    }

    @GetMapping("/items")
    public String getItems(Model model) {
        model.addAttribute("items", itemRepository.findAll());
        return "admin/items";
    }

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
    
    @SneakyThrows
    @PostMapping("/item/create")
    public String createItem(@RequestParam("categoryName") String categoryName,
                             @RequestParam("name") String name,
                             @RequestParam("brand") String brandName,
                             @RequestParam("size") String size,
                             @RequestParam("color") String colorName,
                             @RequestParam("price") String price,
                             @RequestParam("image") MultipartFile image) {
        if (itemRepository.findItemByName(name) != null) {
            return "redirect:/items";
        }

        Item item = new Item();
        fillItem(categoryName, name, brandName, size, colorName, price, item);

        itemRepository.save(item);

        return "redirect:/items";
    }

    @PostMapping("/item/{id}")
    public String updateItem(@PathVariable("id") Long id,
                             @RequestParam("categoryName") String categoryName,
                             @RequestParam("name") String name,
                             @RequestParam("brand") String brandName,
                             @RequestParam("size") String size,
                             @RequestParam("color") String colorName,
                             @RequestParam("price") String price) {
        Item item = itemRepository.findById(id).get();
        fillItem(categoryName, name, brandName, size, colorName, price, item);

        itemRepository.save(item);

        return "redirect:/items";
    }

    private void fillItem(@RequestParam("categoryName") String categoryName,
                          @RequestParam("name") String name,
                          @RequestParam("brand") String brandName,
                          @RequestParam("size") String size,
                          @RequestParam("color") String colorName,
                          @RequestParam("price") String price,
                          Item item) {
        item.setCategory(categoryRepository.findCategoryByName(categoryName));
        item.setName(name);
        item.setSize(size);
        item.setPrice(price);
        Brand brand = brandRepository.findBrandByName(brandName);
        if (brand == null) {
            brand = createBrand(brandName);
        }
        item.setBrand(brand);
        Color color = colorRepository.findColorByName(colorName);
        if (color == null) {
            color = createColor(colorName);
        }
        item.setColor(color);
    }

    private Brand createBrand(String brandName) {
        Brand brand = new Brand();
        brand.setName(brandName);
        brandRepository.save(brand);
        return brand;
    }

    private Color createColor(String colorName) {
        Color color = new Color();
        color.setName(colorName);
        colorRepository.save(color);
        return color;
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

    @GetMapping("/item/delete/{id}")
    public String deleteItem(@PathVariable("id") Long id) {
        itemRepository.delete(itemRepository.findById(id).get());
        return "redirect:/items";
    }
}
