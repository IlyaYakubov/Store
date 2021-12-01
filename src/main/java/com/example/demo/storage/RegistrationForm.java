package com.example.demo.storage;

import com.example.demo.models.Role;
import com.example.demo.models.User;
import lombok.Data;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collections;

@Data
public class RegistrationForm {

    private String username;
    private String password;
    private String surname;
    private String middleName;
    private String country;
    private String city;
    private String phone;
    private String email;
    private boolean block;

    public User toUser(PasswordEncoder passwordEncoder) {
        User user = new User();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        user.setSurname(surname);
        user.setMiddleName(middleName);
        user.setCountry(country);
        user.setCity(city);
        user.setPhone(phone);
        user.setEmail(email);
        user.setBlocked(block);
        user.setRoles(Collections.singleton(Role.USER));

        return user;
    }
}
