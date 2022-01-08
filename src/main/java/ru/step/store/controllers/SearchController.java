package ru.step.store.controllers;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import ru.step.store.models.Brand;
import ru.step.store.models.Item;
import ru.step.store.models.Role;
import ru.step.store.models.User;
import ru.step.store.repositories.BrandRepository;
import ru.step.store.repositories.ColorRepository;
import ru.step.store.repositories.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Collections;
import java.util.stream.IntStream;

@Controller
public class SearchController {

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private BrandRepository brandRepository;

    @Autowired
    private ColorRepository colorRepository;

    @GetMapping("/search")
    public String findItems(@RequestParam(value = "search") String search,
                            Model model,
                            @AuthenticationPrincipal User user,
                            @RequestParam(value = "page", required = false, defaultValue = "0") Integer page) {
        Page<Item> items = itemRepository.findItemsByName(search, PageRequest.of(page, 3));
        if (items.getContent().size() == 0) {
            items = itemRepository.findItemsByBrand(brandRepository.findBrandByName(search), PageRequest.of(page, 3));
        }
        if (items.getContent().size() == 0) {
            items = itemRepository.findItemsByColor(colorRepository.findColorByName(search), PageRequest.of(page, 3));
        }

        model.addAttribute("items", items.getContent());
        model.addAttribute("pages", IntStream.range(0, items.getTotalPages()).toArray());
        model.addAttribute("search", search);

        model.addAttribute("user", "anonymous");
        if (user != null) {
            model.addAttribute("user", user.getUsername());
        }
        return "search";
    }
}
