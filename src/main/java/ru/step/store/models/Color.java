package ru.step.store.models;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "colors")
public class Color {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @Override
    public String toString() {
        return name;
    }
}
