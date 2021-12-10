package ru.step.store.repositories;

import ru.step.store.models.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {

    User findUserByUsername(String name);
}
