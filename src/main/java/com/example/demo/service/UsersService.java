package com.example.demo.service;

import com.example.demo.model.User;

import java.util.List;

public interface UsersService {

    List<User> getUsers();

    User getById(int userId);

    void add(User user);

    void remove(User user);

    void block(User user);

    int nextId();
}
