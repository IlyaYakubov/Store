package com.example.demo.controllers.user;

import com.example.demo.models.RegistrationForm;
import com.example.demo.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/registration")
public class RegistrationController {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public String registration() {
        return "registration";
    }

    @PostMapping
    public String processUser(RegistrationForm form) {
        userRepository.save(form.toUser(passwordEncoder));
        return "redirect:/login";
    }

    /*@PostMapping("/update/user/{userId}")
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
    }*/
}
