package ru.step.store.repositories;

import org.springframework.data.repository.CrudRepository;
import ru.step.store.models.Category;

public interface CategoryRepository extends CrudRepository<Category, Long> {

    Category findCategoryById(Long categoryId);
}
