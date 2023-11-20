package ru.step.store.repositories;

import org.springframework.data.repository.CrudRepository;
import ru.step.store.models.User;

public interface UserRepository extends CrudRepository<User, Long> {

    User findUserByUsername(String name);
}
