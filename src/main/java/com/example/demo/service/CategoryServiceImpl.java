package com.example.demo.service;

import com.example.demo.model.Category;
import com.example.demo.repository.CategoryDAO;

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
    public Category getById(int categoryId) {
        for (Category category : CategoryDAO.INSTANCE.getCategories()) {
            if (category.getId() == categoryId) {
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
