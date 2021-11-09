package com.example.demo.repository;

import com.example.demo.model.Category;

import java.util.LinkedList;

public class CategoryDAO {

    public static CategoryDAO INSTANCE = new CategoryDAO();

    private LinkedList<Category> categories = new LinkedList<>();

    private CategoryDAO() {
        // do nothing
    }

    public LinkedList<Category> getCategories() {
        return categories;
    }

    public void add(Category category) {
        categories.add(category);
    }

    public void remove(Category category) {
        categories.remove(category);
    }
}
