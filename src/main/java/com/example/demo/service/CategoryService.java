package com.example.demo.service;

import com.example.demo.model.Category;

import java.util.LinkedList;

public interface CategoryService {

    LinkedList<Category> getAllCategories();

    Category getById(int categoryId);

    void add(Category category);

    void remove(Category category);

    int nextId();
}
