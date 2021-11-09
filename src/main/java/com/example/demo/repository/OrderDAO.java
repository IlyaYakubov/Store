package com.example.demo.repository;

import com.example.demo.model.Item;

import java.util.LinkedList;

public class OrderDAO {

    public static OrderDAO INSTANCE = new OrderDAO();

    private LinkedList<Item> cart = new LinkedList<>();

    private OrderDAO() {
        // do nothing
    }

    public LinkedList<Item> getCart() {
        return cart;
    }

    public void add(Item item) {
        cart.add(item);
    }

    public void clear() {
        cart.clear();
    }
}
