package com.example.demo.repository;

import com.example.demo.model.User;

import java.util.LinkedList;

public class UserDAO {

    public static UserDAO INSTANCE = new UserDAO();

    private LinkedList<User> users = new LinkedList<>();

    private UserDAO() {
        // do nothing
    }

    public LinkedList<User> getUsers() {
        return users;
    }

    public void add(User user) {
        users.add(user);
    }

    public void remove(User user) {
        users.remove(user);
    }
}
