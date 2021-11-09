package com.example.demo.service;

import com.example.demo.model.Item;

import java.util.LinkedList;

public interface ItemService {

    LinkedList<Item> getAllItems();

    Item getById(int itemId);

    LinkedList<Item> getByName(String name);

    void add(Item item);

    void remove(Item item);

    int nextId();
}
