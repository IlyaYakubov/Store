package com.example.demo.service;

import com.example.demo.model.User;
import com.example.demo.repository.UserDAO;

import java.util.List;

public class UsersServiceImpl implements UsersService {

    public static UsersServiceImpl INSTANCE = new UsersServiceImpl();

    private UsersServiceImpl() {
        // do nothing
    }

    @Override
    public List<User> getUsers() {
        return UserDAO.INSTANCE.getUsers();
    }

    @Override
    public User getById(int userId) {
        return UserDAO.INSTANCE.getUsers().stream().filter(user -> userId == user.getId()).findFirst().get();
    }

    @Override
    public void add(User user) {
        UserDAO.INSTANCE.add(user);
    }

    @Override
    public void remove(User user) {
        UserDAO.INSTANCE.remove(user);
    }

    @Override
    public void block(User user) {
        user.setBlock(true);
    }

    @Override
    public int nextId() {
        return (UserDAO.INSTANCE.getUsers().isEmpty()) ? 1 : UserDAO.INSTANCE.getUsers().getLast().getId() + 1;
    }
}
