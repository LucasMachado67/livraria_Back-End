package com.projetolivraria.livraria.service;

import com.projetolivraria.livraria.model.Category;
import com.projetolivraria.livraria.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository repository;

    //Method to register a new Category
    public Category save(Category category) {
        if (category.getCategory().isEmpty()) {
            throw new RuntimeException("Error while trying to create new Category");
        } else {

            List<String> registeredCategories = repository.findAll().stream()
                    .map(Category::getCategory).toList();
            for(String c : registeredCategories){
                if(c.equals(category.getCategory())){
                    throw new RuntimeException(("Duplicated Category: there is already a category with this name"));
                }
            }
            return repository.save(category);
        }
    }

    //Method to get all categories
    public List<Category> findAll() {
        List<Category> categories = repository.findAll();
        if (categories.isEmpty()) {
            System.out.println("No data found");
            return categories;
        }
        return categories;
    }
    //Method to get a category by id
    public Category findById(int id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category with id: " + id + "not found"));
    }
    //Method to edit categories
    public Category editCategory(Category category) {
        var categoryToEdit = repository.findById(category.getId())
                .orElseThrow(() -> new RuntimeException("Category with id: " + category.getId() + "not found"));
        categoryToEdit.setCategory(category.getCategory());
        return repository.save(category);
    }
    //Method to delete a category based on the id
    public void deleteCategory(int id) {
        if(id < 0){
            throw new IllegalArgumentException("Invalid Id");
        }
        repository.deleteById(id);
        System.out.println("Category deleted");
        
    }
}
