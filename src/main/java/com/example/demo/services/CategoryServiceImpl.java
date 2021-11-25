package com.example.demo.services;

import com.example.demo.models.Category;
import com.example.demo.repositories.CategoryDAO;

import java.util.LinkedList;

public class CategoryServiceImpl implements CategoryService {

    public static CategoryServiceImpl INSTANCE = new CategoryServiceImpl();

    private CategoryServiceImpl() {
        // do nothing
    }

    @Override
    public LinkedList<Category> getAllCategories() {
        return CategoryDAO.INSTANCE.getCategories();
    }

    @Override
    public Category getById(int id) {
        for (Category category : CategoryDAO.INSTANCE.getCategories()) {
            if (category.getId() == id) {
                return category;
            }
        }
        return null;
    }

    @Override
    public void add(Category category) {
        CategoryDAO.INSTANCE.add(category);
    }

    @Override
    public void remove(Category category) {
        CategoryDAO.INSTANCE.remove(category);
    }

    @Override
    public int nextId() {
        return (CategoryDAO.INSTANCE.getCategories().isEmpty()) ? 1 : CategoryDAO.INSTANCE.getCategories().getLast().getId() + 1;
    }
}
