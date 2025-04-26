package com.projetolivraria.livraria.service;


import com.projetolivraria.livraria.model.Category;
import com.projetolivraria.livraria.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository repository;

    public Category newCategory(Category category) {
        if (category.getCategory().isEmpty()) {
            throw new RuntimeException("Error while trying to create new Category");
        } else {
            return repository.save(category);
        }
    }

    public List<Category> findAll() {
        List<Category> categories = repository.findAll();
        if (categories.isEmpty()) {
            System.out.println("No data found");
            return categories;
        }
        return categories;
    }

    public Category findById(int id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category with id: " + id + "not found"));
    }

    public Category editCategory(Category category) {
        var categoryToEdit = repository.findById(category.getId())
                .orElseThrow(() -> new RuntimeException("Category with id: " + category.getId() + "not found"));
        categoryToEdit.setCategory(category.getCategory());
        return repository.save(category);
    }

    public void deleteCategory(int id) {
        if(id < 0){
            throw new IllegalArgumentException("Invalid Id");
        }
        repository.deleteById(id);
        System.out.println("Category deleted");
    }
}
