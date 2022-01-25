package ru.step.store.repositories;

import org.springframework.data.repository.CrudRepository;
import ru.step.store.models.Order;
import ru.step.store.models.User;

import java.util.List;

public interface OrderRepository extends CrudRepository<Order, Long> {

    List<Order> findOrdersByUser(User user);
}
