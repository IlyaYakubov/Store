package com.example.demo.services;

import com.example.demo.models.Item;

import java.util.LinkedList;

public interface OrderService {

    LinkedList<Item> getAllItems();

    void add(Item item);

    void clear();
}
