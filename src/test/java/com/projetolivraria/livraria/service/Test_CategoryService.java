package com.projetolivraria.livraria.service;

import com.projetolivraria.livraria.model.Category;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.projetolivraria.livraria.repository.CategoryRepository;

@ExtendWith(MockitoExtension.class)
public class Test_CategoryService {

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private CategoryService categoryService;

    Category category1;
    Category category2;

    @BeforeEach
    void setup(){
        category1 = new Category();
        category1.setId(1);
        category1.setCategory("Romance");
        category2 = new Category();
        category2.setId(2);
        category2.setCategory("Action");
    }

    @Test
    @DisplayName("Should save Category")
    void testSaveCategoryMethod(){

        when(categoryRepository.save(any(Category.class))).thenReturn(category1);

        Category saved = categoryService.save(category1);

        assertEquals("Romance", saved.getCategory());
        assertEquals(1, saved.getId());
    }

    @Test
    @DisplayName("Should return  category by id")
    void testFindCategoryById(){
        when(categoryRepository.findById(1)).thenReturn(Optional.of(category1));

        Category result = categoryService.findById(1);

        assertEquals("Romance", result.getCategory());
    }

    @Test
    @DisplayName("Should return all categories")
    void testFindAllCategories(){
        List<Category> list = Arrays.asList(category1,category2);
        when(categoryRepository.findAll()).thenReturn(list);

        List<Category> result = categoryService.findAll();

        assertEquals(list.size(), result.size());
        assertEquals("Action", result.get(1).getCategory());
    }

    @Test
    @DisplayName("Should delete category by id")
    void testDeleteCategory(){
        doNothing().when(categoryRepository).deleteById(1);

        categoryService.deleteCategory(1);

        verify(categoryRepository, times(1)).deleteById(1);

    }

    @Test
    @DisplayName("Should update category")
    void testUpdateCategory(){
        Category existingCategory = new Category();
        existingCategory.setId(1);
        existingCategory.setCategory("Aventura");

        Category updatedCategory = new Category();
        updatedCategory.setId(1);
        updatedCategory.setCategory("Romance");

        when(categoryRepository.findById(1)).thenReturn(Optional.of(existingCategory));
        when(categoryRepository.save(any(Category.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Category result = categoryService.editCategory(updatedCategory);

        assertEquals("Romance", result.getCategory());
        assertEquals(1, result.getId());
    
    }
}
