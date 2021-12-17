package ru.step.store.repositories;

import ru.step.store.models.OrderElement;
import org.springframework.data.repository.CrudRepository;
import ru.step.store.models.User;

import java.util.List;

public interface OrderElementRepository extends CrudRepository<OrderElement, Long> {

    List<OrderElement> findOrderElementsByUserAndArranged(User user, boolean arranged);
}
