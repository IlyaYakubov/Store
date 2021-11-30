package com.example.demo.models;

import lombok.Data;

import javax.persistence.*;
import java.math.BigInteger;

@Data
@Entity
@Table(name = "cart")
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Long quantity;
    private BigInteger sum;

    @ManyToOne
    @JoinColumn(name = "item_id")
    private Item item;

    @OneToOne
    private Order order;
}
