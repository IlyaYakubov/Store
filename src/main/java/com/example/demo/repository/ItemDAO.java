package com.example.demo.repository;

import com.example.demo.model.Item;

import java.util.LinkedList;

public class ItemDAO {

    public static ItemDAO INSTANCE = new ItemDAO();

    private LinkedList<Item> items = new LinkedList<>();

    private ItemDAO() {
        // do nothing
    }

    public LinkedList<Item> getItems() {
        return items;
    }

    public void add(Item item) {
        items.add(item);
    }

    public void remove(Item item) {
        items.remove(item);
    }
}
