package com.exam.examserver.service.impl;

import java.util.LinkedHashSet;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.exam.examserver.entity.exam.Category;
import com.exam.examserver.repository.CategoryRepository;
import com.exam.examserver.service.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService{

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public Category addCategory(Category category) {
        return this.categoryRepository.save(category);
    }

    @Override
    public Category updateCategory(Category category) {
        return this.categoryRepository.save(category);
    }

    @Override
    public Set<Category> getCategories() {
        
        Set<Category> categories = new LinkedHashSet<>();

        for(Category c: this.categoryRepository.findAll()){
            categories.add(c);
        }

        return categories;
    }

    @Override
    public Optional<Category> getCategory(long id) {
        return this.categoryRepository.findById(id);
    }

    @Override
    public void deleteCategory(long id) {
        this.categoryRepository.deleteById(id);
    }

}
