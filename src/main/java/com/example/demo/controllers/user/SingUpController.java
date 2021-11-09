package com.example.demo.controllers.user;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import com.example.demo.model.User;
import com.example.demo.service.UsersServiceImpl;

@Controller
public class SingUpController {

    @GetMapping("/singup")
    public String singUp() {
        return "user/singup";
    }

    @PostMapping("/update/user/{userId}")
    public String editUser(@PathVariable("userId") int userId,
                             @RequestParam("name") String name,
                             @RequestParam("surname") String surname,
                             @RequestParam("middleName") String middleName,
                             @RequestParam("country") String country,
                             @RequestParam("city") String city,
                             @RequestParam("phone") String phone,
                             @RequestParam("email") String email) {
        User user = UsersServiceImpl.INSTANCE.getById(userId);
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
}
