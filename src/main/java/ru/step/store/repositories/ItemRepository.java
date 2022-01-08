package ru.step.store.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import ru.step.store.models.Brand;
import ru.step.store.models.Color;
import ru.step.store.models.Item;

public interface ItemRepository extends CrudRepository<Item, Long> {

    Item findItemById(Long id);

    Item findItemByName(String name);

    Page<Item> findItemsByName(String name, Pageable pageable);

    Page<Item> findItemsByBrand(Brand brand, Pageable pageable);

    Page<Item> findItemsByColor(Color color, Pageable pageable);

    Page<Item> findItemsByCategory_Id(Long categoryId, Pageable pageable);

    Page<Item> findAll(Pageable pageable);
}
