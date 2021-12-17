package ru.step.store.controllers;

import ru.step.store.models.Brand;
import ru.step.store.models.Color;
import ru.step.store.models.Item;
import ru.step.store.repositories.BrandRepository;
import ru.step.store.repositories.CategoryRepository;
import ru.step.store.repositories.ColorRepository;
import ru.step.store.repositories.ItemRepository;
import ru.step.store.storage.StorageService;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigInteger;

@Controller
public class ItemController {

    private final StorageService storageService;

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private BrandRepository brandRepository;

    @Autowired
    private ColorRepository colorRepository;

    @Autowired
    public ItemController(StorageService storageService) {
        this.storageService = storageService;
    }

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
                             @RequestParam("brand") String brandName,
                             @RequestParam("size") String size,
                             @RequestParam("color") String colorName,
                             @RequestParam("price") String price,
                             @RequestParam("filename") MultipartFile filename) {
        if (itemRepository.findItemByName(name) != null) {
            return "redirect:/items";
        }

        Item item = new Item();
        fillItem(categoryName, name, brandName, size, colorName, price, filename, item);

        itemRepository.save(item);

        return "redirect:/items";
    }

    @GetMapping("/item/{id}")
    public String editItem(@PathVariable("id") Long id, Model model) {
        Item item = itemRepository.findById(id).get();
        model.addAttribute("item", item);
        model.addAttribute("filename", item.getFilename());
        return "admin/edit-item";
    }

    @PostMapping("/item/{id}")
    public String updateItem(@PathVariable("id") Long id,
                             @RequestParam("categoryName") String categoryName,
                             @RequestParam("name") String name,
                             @RequestParam("brand") String brandName,
                             @RequestParam("size") String size,
                             @RequestParam("color") String colorName,
                             @RequestParam("price") String price,
                             @RequestParam("filename") MultipartFile filename) {
        Item item = itemRepository.findById(id).get();
        fillItem(categoryName, name, brandName, size, colorName, price, filename, item);

        itemRepository.save(item);

        return "redirect:/items";
    }

    private void fillItem(@RequestParam("categoryName") String categoryName,
                          @RequestParam("name") String name,
                          @RequestParam("brand") String brandName,
                          @RequestParam("size") String size,
                          @RequestParam("color") String colorName,
                          @RequestParam("price") String price,
                          @RequestParam("filename") MultipartFile filename,
                          Item item) {
        item.setCategory(categoryRepository.findCategoryByName(categoryName));
        item.setName(name);
        item.setSize(Long.parseLong(size));
        item.setPrice(BigInteger.valueOf(Long.parseLong(price)));
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
        storageService.store(filename);
        item.setFilename(filename.getOriginalFilename());
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

    @GetMapping("/item/delete/{id}")
    public String deleteItem(@PathVariable("id") Long id) {
        itemRepository.delete(itemRepository.findById(id).get());
        return "redirect:/items";
    }

    @ExceptionHandler(StorageFileNotFoundException.class)
    public ResponseEntity<?> handleStorageFileNotFound(StorageFileNotFoundException exc) {
        return ResponseEntity.notFound().build();
    }

    static class StorageFileNotFoundException extends Exception {

    }
}