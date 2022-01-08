package ru.step.store.models;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "realizations")
public class Realization {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String phone;

    @OneToOne
    private Order order;
}
