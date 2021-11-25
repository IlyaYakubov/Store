package com.example.demo.controllers.admin;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import com.example.demo.services.UsersServiceImpl;

@Controller
public class AdminController {

    @GetMapping("/admin")
    public String users(Model model) {
        model.addAttribute("users", UsersServiceImpl.INSTANCE.getUsers());
        return "admin/users";
    }

    @GetMapping("/users/{userId}")
    public String editUser(@PathVariable("userId") int userId, Model model) {
        model.addAttribute("user", UsersServiceImpl.INSTANCE.getById(userId));
        return "admin/user";
    }

    @GetMapping("/users/delete/{userId}")
    public String removeUser(@PathVariable("userId") int userId) {
        UsersServiceImpl.INSTANCE.remove(UsersServiceImpl.INSTANCE.getById(userId));
        return "redirect:/admin";
    }

    @GetMapping("/users/block/{userId}")
    public String blockUser(@PathVariable("userId") int userId) {
        UsersServiceImpl.INSTANCE.block(UsersServiceImpl.INSTANCE.getById(userId));
        return "redirect:/admin";
    }
}
