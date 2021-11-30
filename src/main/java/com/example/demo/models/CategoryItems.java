package com.example.demo.models;

import lombok.Data;

import javax.persistence.*;

@Data
//@Entity
//@Table(name = "category_items")
public class CategoryItems {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne
    private Item item;

    @OneToOne
    private Category category;
}
