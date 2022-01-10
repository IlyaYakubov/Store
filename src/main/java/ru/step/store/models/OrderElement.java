package ru.step.store.models;

import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.math.BigInteger;

@Data
@Entity
@Table(name = "order_elements")
public class OrderElement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long quantity;

    private BigInteger sum;

    @OnDelete(action = OnDeleteAction.CASCADE)
    @ManyToOne
    @JoinColumn(name = "item_id")
    private Item item;

    @OnDelete(action = OnDeleteAction.CASCADE)
    @ManyToOne
    private User user;

    private boolean arranged;
}
