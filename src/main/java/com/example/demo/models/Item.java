package com.example.demo.models;

import lombok.Data;

import javax.persistence.*;
import java.math.BigInteger;

@Data
@Entity
@Table(name = "items")
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private Long size;
    private BigInteger price;

    @ManyToOne
    private Brand brand;

    @ManyToOne
    private Color color;

    @OneToOne
    private Image image;

    @ManyToOne
    private Category category;
}
