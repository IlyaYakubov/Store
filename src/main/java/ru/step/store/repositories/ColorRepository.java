package ru.step.store.repositories;

import ru.step.store.models.Color;
import org.springframework.data.repository.CrudRepository;

public interface ColorRepository extends CrudRepository<Color, Long> {

    Color findColorByName(String name);
}
