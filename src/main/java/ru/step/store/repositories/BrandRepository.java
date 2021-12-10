package ru.step.store.repositories;

import ru.step.store.models.Brand;
import org.springframework.data.repository.CrudRepository;

public interface BrandRepository extends CrudRepository<Brand, Long> {

    Brand findBrandByName(String name);
}
