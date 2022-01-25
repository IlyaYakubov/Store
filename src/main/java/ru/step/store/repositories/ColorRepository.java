package ru.step.store.repositories;

import org.springframework.data.repository.CrudRepository;
import ru.step.store.models.Color;

public interface ColorRepository extends CrudRepository<Color, Long> {

    Color findColorByName(String name);
}
