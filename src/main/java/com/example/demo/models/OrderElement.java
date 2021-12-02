package com.example.demo.models;

import lombok.Data;

import javax.persistence.*;
import java.math.BigInteger;

@Data
@Entity
@Table(name = "order_elements")
public class OrderElement {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Long quantity;
    private BigInteger sum;

    @ManyToOne
    @JoinColumn(name = "item_id")
    private Item item;
}
