package com.example.demo.repositories;

import com.example.demo.models.Color;
import org.springframework.data.repository.CrudRepository;

public interface ColorRepository extends CrudRepository<Color, Long> {

    Color findColorByName(String name);
}
