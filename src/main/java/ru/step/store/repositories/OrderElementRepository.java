package ru.step.store.repositories;

import ru.step.store.models.OrderElement;
import org.springframework.data.repository.CrudRepository;

public interface OrderElementRepository extends CrudRepository<OrderElement, Long> {

}
