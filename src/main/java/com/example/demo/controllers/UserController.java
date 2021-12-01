package com.example.demo.controllers;

import com.example.demo.models.User;
import com.example.demo.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/admin")
    public String getUsers(Model model) {
        model.addAttribute("users", userRepository.findAll());
        return "admin/users";
    }

    @GetMapping("/user/add")
    public String getUser(@RequestParam("username") String username,
                          @RequestParam("surname") String surname,
                          @RequestParam("middleName") String middleName,
                          @RequestParam("country") String country,
                          @RequestParam("city") String city,
                          @RequestParam("phone") String phone,
                          @RequestParam("email") String email) {
        User user = new User();
        fillUser(username, surname, middleName, country, city, phone, email, user);

        userRepository.save(user);

        return "redirect:/";
    }

    private void fillUser(@RequestParam("username") String username,
                          @RequestParam("surname") String surname,
                          @RequestParam("middleName") String middleName,
                          @RequestParam("country") String country,
                          @RequestParam("city") String city,
                          @RequestParam("phone") String phone,
                          @RequestParam("email") String email,
                          User user) {
        user.setUsername(username);
        user.setSurname(surname);
        user.setMiddleName(middleName);
        user.setCountry(country);
        user.setCity(city);
        user.setPhone(phone);
        user.setEmail(email);
    }

    @GetMapping("/cabinet/{id}")
    public String getCabinet(@PathVariable("id") Long id, Model model) {
        model.addAttribute("user", userRepository.findById(id).get());
        return "user/cabinet";
    }

    @GetMapping("/user/{id}")
    public String editUser(@PathVariable("id") Long id, Model model) {
        model.addAttribute("user", userRepository.findById(id).get());
        return "admin/user";
    }

    @PostMapping("/update/user/{id}")
    public String editUser(@PathVariable("id") Long id,
                           @RequestParam("username") String username,
                           @RequestParam("surname") String surname,
                           @RequestParam("middleName") String middleName,
                           @RequestParam("country") String country,
                           @RequestParam("city") String city,
                           @RequestParam("phone") String phone,
                           @RequestParam("email") String email) {
        updateUser(id, username, surname, middleName, country, city, phone, email);

        return "redirect:/";
    }

    @PostMapping("/cabinet/{id}")
    public String editUserInCabinet(@PathVariable("id") Long id,
                                    @RequestParam("username") String username,
                                    @RequestParam("surname") String surname,
                                    @RequestParam("middleName") String middleName,
                                    @RequestParam("country") String country,
                                    @RequestParam("city") String city,
                                    @RequestParam("phone") String phone,
                                    @RequestParam("email") String email) {
        updateUser(id, username, surname, middleName, country, city, phone, email);

        return "user/cabinet";
    }

    private void updateUser(@PathVariable("id") Long id,
                            @RequestParam("username") String username,
                            @RequestParam("surname") String surname,
                            @RequestParam("middleName") String middleName,
                            @RequestParam("country") String country,
                            @RequestParam("city") String city,
                            @RequestParam("phone") String phone,
                            @RequestParam("email") String email) {
        User user = userRepository.findById(id).get();
        fillUser(username, surname, middleName, country, city, phone, email, user);
        userRepository.save(user);
    }

    @GetMapping("/user/delete/{id}")
    public String deleteUser(@PathVariable("id") Long id) {
        userRepository.delete(userRepository.findById(id).get());
        return "redirect:/admin";
    }

    @GetMapping("/user/block/{id}")
    public String blockUser(@PathVariable("id") Long id) {
        User user = userRepository.findById(id).get();
        user.setBlock(true);
        userRepository.save(user);
        return "redirect:/admin";
    }
}