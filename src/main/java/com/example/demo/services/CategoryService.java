package com.example.demo.services;

import com.example.demo.models.Category;

import java.util.LinkedList;

public interface CategoryService {

    LinkedList<Category> getAllCategories();

    Category getById(int categoryId);

    void add(Category category);

    void remove(Category category);

    int nextId();
}
