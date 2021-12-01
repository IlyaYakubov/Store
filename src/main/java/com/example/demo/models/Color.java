package com.example.demo.models;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "colors")
public class Color {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;

    @Override
    public String toString() {
        return name;
    }
}
