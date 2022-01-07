package ru.step.store.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.step.store.models.Category;
import ru.step.store.models.Item;
import ru.step.store.models.Role;
import ru.step.store.models.User;
import ru.step.store.repositories.CategoryRepository;
import ru.step.store.repositories.ItemRepository;
import ru.step.store.repositories.UserRepository;

import java.util.Collections;
import java.util.stream.IntStream;

@Controller
@RequestMapping("/")
public class MainController {

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping
    public String getIndexPage(@AuthenticationPrincipal User user,
                               Model model,
                               @RequestParam(value = "page", required = false, defaultValue = "0") Integer page) {
        Page<Item> items = itemRepository.findAll(PageRequest.of(page, 3));
        model.addAttribute("items", items.getContent());
        model.addAttribute("pages", IntStream.range(0, items.getTotalPages()).toArray());

        if (user != null && user.getRoles().stream().findFirst().get().name().equals("ADMIN")) {
            return "redirect:/admin";
        }

        final int[] counter = {0};
        userRepository.findAll().forEach(role -> counter[0]++);
        if (counter[0] == 0) {
            User admin = new User();
            admin.setUsername("admin");
            admin.setPassword(passwordEncoder.encode("123"));
            admin.setRoles(Collections.singleton(Role.ADMIN));
            userRepository.save(admin);
        }

        model.addAttribute("user", "anonymous");
        if (user != null) {
            model.addAttribute("user", user.getUsername());
        }
        return "index";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/catalog/items/{id}")
    public String getItem(@AuthenticationPrincipal User user, @PathVariable("id") Long id, Model model) {
        Item item = itemRepository.findById(id).get();
        model.addAttribute("item", item);
        model.addAttribute("filename", item.getFilename());

        model.addAttribute("user", "anonymous");
        if (user != null) {
            model.addAttribute("user", user.getUsername());
        }
        return "user/item";
    }

    @ResponseBody
    @PostMapping("/categories/all")
    public Iterable<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    @GetMapping("/catalog/")
    public String getItemFromPage(@AuthenticationPrincipal User user,
                               Model model,
                               @RequestParam(value = "page", required = false, defaultValue = "0") Integer page) {
        Page<Item> items = itemRepository.findAll(PageRequest.of(page, 3));
        model.addAttribute("items", items.getContent());
        model.addAttribute("pages", IntStream.range(0, items.getTotalPages()).toArray());

        if (user != null && user.getRoles().stream().findFirst().get().name().equals("ADMIN")) {
            return "redirect:/admin";
        }

        final int[] counter = {0};
        userRepository.findAll().forEach(role -> counter[0]++);
        if (counter[0] == 0) {
            User admin = new User();
            admin.setUsername("admin");
            admin.setPassword(passwordEncoder.encode("123"));
            admin.setRoles(Collections.singleton(Role.ADMIN));
            userRepository.save(admin);
        }

        model.addAttribute("user", "anonymous");
        if (user != null) {
            model.addAttribute("user", user.getUsername());
        }
        return "index";
    }
}
