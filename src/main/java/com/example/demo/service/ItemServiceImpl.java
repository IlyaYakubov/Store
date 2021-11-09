package com.example.demo.service;

import com.example.demo.model.Item;
import com.example.demo.repository.ItemDAO;

import java.util.LinkedList;
import java.util.stream.Collectors;

public class ItemServiceImpl implements ItemService {

    public static ItemServiceImpl INSTANCE = new ItemServiceImpl();

    private ItemServiceImpl() {
        // do nothing
    }

    @Override
    public LinkedList<Item> getAllItems() {
        return ItemDAO.INSTANCE.getItems();
    }

    @Override
    public Item getById(int itemIdId) {
        for (Item item : ItemDAO.INSTANCE.getItems()) {
            if (item.getId() == itemIdId) {
                return item;
            }
        }
        return null;
    }

    @Override
    public void add(Item item) {
        ItemDAO.INSTANCE.add(item);
    }

    @Override
    public void remove(Item item) {
        ItemDAO.INSTANCE.remove(item);
    }

    @Override
    public LinkedList<Item> getByName(String name) {
        return ItemDAO.INSTANCE.getItems().stream()
                .filter(item -> item.getName().toLowerCase().contains(name.toLowerCase()))
                .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    public int nextId() {
        return (ItemDAO.INSTANCE.getItems().isEmpty()) ? 1 : ItemDAO.INSTANCE.getItems().getLast().getId() + 1;
    }
}
