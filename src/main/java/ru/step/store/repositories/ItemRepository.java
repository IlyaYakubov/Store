package ru.step.store.repositories;

import ru.step.store.models.Item;
import org.springframework.data.repository.CrudRepository;

public interface ItemRepository extends CrudRepository<Item, Long> {

    Item findItemByName(String name);

    Item findItemByCategory_Id(Long categoryId);
}
