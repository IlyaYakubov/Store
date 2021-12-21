package ru.step.store.models;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    private User user;

    @OneToMany(cascade = CascadeType.ALL)
    private List<OrderElement> orderElements;

    @Override
    public String toString() {
        StringBuilder items = new StringBuilder();
        for (OrderElement orderElement : orderElements) {
            items.append(orderElement.getItem()).append(", ");
        }
        return "Заказ номер: " + id + ", Товары: " + items;
    }
}
