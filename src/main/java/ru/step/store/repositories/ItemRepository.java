package ru.step.store.repositories;

import ru.step.store.models.Item;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ItemRepository extends CrudRepository<Item, Long> {

    Item findItemByName(String name);

    Item findItemByCategory_Id(Long categoryId);

    List<Item> findItemsByCategory_Id(Long categoryId);
}
