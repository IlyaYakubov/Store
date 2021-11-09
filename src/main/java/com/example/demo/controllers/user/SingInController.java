package com.example.demo.controllers.user;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.example.demo.model.User;
import com.example.demo.service.UsersServiceImpl;

@Controller
public class SingInController {

    @GetMapping("/singin")
    public String singIn() {
        return "user/singin";
    }

    @GetMapping("/cabinet/{userId}")
    public String openCabinet(@PathVariable("userId") int userId, Model model) {
        model.addAttribute("user", UsersServiceImpl.INSTANCE.getById(userId));
        return "user/cabinet";
    }

    @GetMapping("/add/user")
    public String user(@RequestParam("name") String name,
                       @RequestParam("surname") String surname,
                       @RequestParam("middleName") String middleName,
                       @RequestParam("country") String country,
                       @RequestParam("city") String city,
                       @RequestParam("phone") String phone,
                       @RequestParam("email") String email) {
        User user = new User();
        user.setId(UsersServiceImpl.INSTANCE.nextId());
        user.setName(name);
        user.setSurname(surname);
        user.setMiddleName(middleName);
        user.setCountry(country);
        user.setCity(city);
        user.setPhone(phone);
        user.setEmail(email);

        UsersServiceImpl.INSTANCE.add(user);

        return "redirect:/";
    }

    @PostMapping("/cabinet/{userId}")
    public String editUserInCabinet(@PathVariable("userId") int userId,
                         @RequestParam("name") String name,
                         @RequestParam("surname") String surname,
                         @RequestParam("middleName") String middleName,
                         @RequestParam("country") String country,
                         @RequestParam("city") String city,
                         @RequestParam("phone") String phone,
                         @RequestParam("email") String email) {
        for (User currentUser : UsersServiceImpl.INSTANCE.getUsers()) {
            if (currentUser.getId() == userId) {
                currentUser.setName(name);
                currentUser.setSurname(surname);
                currentUser.setMiddleName(middleName);
                currentUser.setCountry(country);
                currentUser.setCity(city);
                currentUser.setPhone(phone);
                currentUser.setEmail(email);
            }
        }
        return "user/cabinet";
    }
}
