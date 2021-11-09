package com.example.demo.service;

import com.example.demo.repository.OrderDAO;
import com.example.demo.model.Item;

import java.util.LinkedList;

public class OrderServiceImpl implements OrderService {

    public static OrderServiceImpl INSTANCE = new OrderServiceImpl();

    private OrderServiceImpl() {
        // do nothing
    }

    @Override
    public LinkedList<Item> getAllItems() {
        return OrderDAO.INSTANCE.getCart();
    }

    @Override
    public void add(Item item) {
        OrderDAO.INSTANCE.add(item);
    }

    @Override
    public void clear() {
        OrderDAO.INSTANCE.clear();
    }
}
