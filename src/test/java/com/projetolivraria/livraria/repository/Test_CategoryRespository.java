package com.projetolivraria.livraria.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.NoSuchElementException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import com.projetolivraria.livraria.model.Category;

@DataJpaTest
@ActiveProfiles("test")
public class Test_CategoryRespository {
    
    @Autowired
    private CategoryRepository repository;

    private Category category1;
    private Category category2;

    @BeforeEach()
    void setup(){
        repository.deleteAll();;

        category1 = new Category("Romance");
        category2 = new Category("Aventura");

    }

    @Test
    @DisplayName("Should create new Author in database when save")
    void ShouldCreateNewCategoryWhenSave(){

        repository.save(category1);
        repository.save(category2);

        List<Category> allCategories = repository.findAll();

        assertEquals(2, allCategories.size(), "Deve retornar as duas categorias salvas");
        assertTrue(
            allCategories.stream().anyMatch(author -> category1.getCategory().equals("Romance")),
            "A Categoria Romance está salva"
        );
        assertTrue(
            allCategories.stream().anyMatch(author -> category2.getCategory().equals("Aventura")),
            "A Categoria Aventura está salva"
        );
    }

    @Test
    @DisplayName("Should return Category When findById")
    void ShouldReturnCategoryWhenFindById(){
        repository.save(category1);

        Category categoryFound = repository.findById(category1.getId()).orElseThrow();

        assertThrows(NoSuchElementException.class, () -> {
            repository.findById(999).orElseThrow();
        });

        //Testando se a categoria encontrada pelo código é igual a categoria salva no banco
        assertEquals(category1, categoryFound);
    }

    @Test
    @DisplayName("Test - Should Return nothing when deleteById")
    void testShouldReturnNothingWhenDeleteById(){
        
        repository.save(category1);

        repository.deleteById(category1.getId());

        boolean exists = repository.findById(category1.getId()).isPresent();
        assertFalse(exists, "A categoria deve ter sido deletado do banco");
    
    }

    @Test
    @DisplayName("Test - Should Return Updated Category when changing information of Category")
    void testShouldReturnUpdatedCategoryWhenChangeInformationOfCategory(){
        
        repository.save(category1);
        
        Category categoryToUpdate = new Category("Nome Antigo");
        repository.save(categoryToUpdate);

        // Act - altera o nome e salva de novo
        categoryToUpdate.setCategory("Nome Atualizado");
        repository.save(categoryToUpdate);

        // Assert - busca novamente do banco e verifica
        Category updatedCategory = repository.findById(categoryToUpdate.getId()).orElseThrow();
        assertEquals("Nome Atualizado", updatedCategory.getCategory());
       
    }
}
