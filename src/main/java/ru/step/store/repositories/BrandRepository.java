package ru.step.store.repositories;

import org.springframework.data.repository.CrudRepository;
import ru.step.store.models.Brand;

public interface BrandRepository extends CrudRepository<Brand, Long> {

    Brand findBrandByName(String name);
}
