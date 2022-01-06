package ru.step.store.models;

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
    private String size;
    private BigInteger price;
    private String filename;

    @ManyToOne
    private Brand brand;

    @ManyToOne
    private Color color;

    @ManyToOne
    private Category category;

    @Override
    public String toString() {
        return "наименование: " + name +
                ", размер: " + size +
                ", цена: " + price +
                ", брэнд: " + brand +
                ", цвет: " + color;
    }
}
