package ru.step.store.repositories;

import ru.step.store.models.Category;
import org.springframework.data.repository.CrudRepository;

public interface CategoryRepository extends CrudRepository<Category, Long> {

    Category findCategoryById(Long categoryId);
}
