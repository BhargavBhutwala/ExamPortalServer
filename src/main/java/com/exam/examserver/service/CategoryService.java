package com.exam.examserver.service;

import java.util.Optional;
import java.util.Set;

import com.exam.examserver.entity.exam.Category;

public interface CategoryService {

    public Category addCategory(Category category);

    public Category updateCategory(Category category);

    public Set<Category> getCategories();

    public Optional<Category> getCategory(long id);

    public void deleteCategory(long id);

}
