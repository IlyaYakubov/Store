package ru.step.store.controllers;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.step.store.models.Brand;
import ru.step.store.models.Color;
import ru.step.store.models.Item;
import ru.step.store.models.User;
import ru.step.store.repositories.BrandRepository;
import ru.step.store.repositories.CategoryRepository;
import ru.step.store.repositories.ColorRepository;
import ru.step.store.repositories.ItemRepository;
import ru.step.store.storage.StorageService;

import java.math.BigInteger;
import java.util.stream.IntStream;

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
    public String createItem(@RequestParam("categoryId") String categoryId,
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
        fillItem(categoryId, name, brandName, size, colorName, price, filename, item);

        itemRepository.save(item);

        return "redirect:/items";
    }

    @GetMapping("/items/edit/{id}")
    public String editItem(@PathVariable("id") Long id, Model model) {
        Item item = itemRepository.findById(id).get();
        model.addAttribute("item", item);
        return "admin/edit-item";
    }

    @PostMapping("/item/{id}")
    public String updateItem(@PathVariable("id") Long id,
                             @RequestParam("categoryId") String categoryId,
                             @RequestParam("name") String name,
                             @RequestParam("brand") String brandName,
                             @RequestParam("size") String size,
                             @RequestParam("color") String colorName,
                             @RequestParam("price") String price,
                             @RequestParam("filenameImage") String filenameImage,
                             @RequestParam("filename") MultipartFile filename) {
        Item item = itemRepository.findById(id).get();
        fillItem(categoryId, name, brandName, size, colorName, price, filename, item);
        if (filename.getOriginalFilename().isEmpty()) {
            item.setFilename(filenameImage);
        }
        itemRepository.save(item);

        return "redirect:/items";
    }

    private void fillItem(@RequestParam("categoryId") String categoryId,
                          @RequestParam("name") String name,
                          @RequestParam("brand") String brandName,
                          @RequestParam("size") String size,
                          @RequestParam("color") String colorName,
                          @RequestParam("price") String price,
                          @RequestParam("filename") MultipartFile filename,
                          Item item) {
        if (categoryId.isEmpty()) {
            categoryId = "1";
        }
        item.setCategory(categoryRepository.findCategoryById(Long.parseLong(categoryId)));
        item.setName(name);
        item.setSize(size);
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
        if (!filename.getOriginalFilename().isEmpty()) {
            storageService.store(filename);
        }
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

    @GetMapping("/items/{category_id}")
    public String getItemsByCategory(@AuthenticationPrincipal User user,
                                     @PathVariable Long category_id,
                                     Model model,
                                     @RequestParam(value = "page", required = false, defaultValue = "0") Integer page) {
        Page<Item> items = itemRepository.findItemsByCategory_Id(category_id, PageRequest.of(page, 3));
        model.addAttribute("items", items.getContent());
        model.addAttribute("pages", IntStream.range(0, items.getTotalPages()).toArray());
        model.addAttribute("category_id", category_id);

        model.addAttribute("user", "anonymous");
        if (user != null) {
            model.addAttribute("user", user.getUsername());
        }
        return "index";
    }

    @ResponseBody
    @PostMapping("/items/{category_id}")
    public Page<Item> getItemsByCategoryId(@PathVariable Long category_id,
                                           @RequestParam(value = "page", required = false, defaultValue = "0") Integer page) {
        return itemRepository.findItemsByCategory_Id(category_id, PageRequest.of(page, 3));
    }

    @ResponseBody
    @PostMapping("/items/")
    public Page<Item> getAllItems(@RequestParam(value = "page", required = false, defaultValue = "0") Integer page) {
        return itemRepository.findAll(PageRequest.of(page, 3));
    }

    @ResponseBody
    @PostMapping("/brands")
    public Iterable<Brand> getAllBrands() {
        return brandRepository.findAll();
    }

    @ResponseBody
    @PostMapping("/colors")
    public Iterable<Color> getAllColors() {
        return colorRepository.findAll();
    }
}
