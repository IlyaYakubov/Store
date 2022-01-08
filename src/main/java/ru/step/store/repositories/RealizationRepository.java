package ru.step.store.repositories;

import org.springframework.data.repository.CrudRepository;
import ru.step.store.models.Realization;

import java.util.List;

public interface RealizationRepository extends CrudRepository<Realization, Long> {

    List<Realization> findRealizationsByPhone(String phone);
}
