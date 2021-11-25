package com.example.demo.services;

import com.example.demo.models.User;

import java.util.List;

public interface UsersService {

    List<User> getUsers();

    User getById(int userId);

    void add(User user);

    void remove(User user);

    void block(User user);

    Long nextId();
}
