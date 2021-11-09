package com.example.demo.service;

import com.example.demo.model.Item;

import java.util.LinkedList;

public interface OrderService {

    LinkedList<Item> getAllItems();

    void add(Item item);

    void clear();
}
