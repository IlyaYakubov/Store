package ru.step.store.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import ru.step.store.models.Item;

public interface ItemRepository extends CrudRepository<Item, Long> {

    Item findItemByName(String name);

    Item findItemByCategory_Id(Long categoryId);

    Page<Item> findItemsByCategory_Id(Long categoryId, Pageable pageable);

    Page<Item> findAll(Pageable pageable);
}
