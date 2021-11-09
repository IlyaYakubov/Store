package com.example.demo.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class User {

    private int id;
    private String surname;
    private String name;
    private String middleName;
    private String country;
    private String city;
    private String phone;
    private String email;
    private boolean block;

}
