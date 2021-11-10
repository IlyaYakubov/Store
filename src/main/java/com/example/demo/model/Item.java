package com.example.demo.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class Item {

    private int id;
    private int categoryId;
    private String name;
    private String brand;
    private String size;
    private String color;
    private String price;
    private Image image;

}
