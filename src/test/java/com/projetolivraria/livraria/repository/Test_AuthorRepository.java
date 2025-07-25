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

import com.projetolivraria.livraria.model.Author;

@DataJpaTest
@ActiveProfiles("test")
public class Test_AuthorRepository {


    @Autowired
    private AuthorRepository repository;

    private Author author1;
    private Author author2;

    @BeforeEach()
    void setup(){
        repository.deleteAll();;

        author1 = new Author("Machado de Assis");
        author2 = new Author("John Green");

    }

    @Test
    @DisplayName("Should create new Author in database when save")
    void ShouldCreateNewAuthorWhenSave(){

        repository.save(author1);
        repository.save(author2);

        List<Author> allAuthors = repository.findAll();

        assertEquals(2, allAuthors.size(), "Deve retornar os dois autores salvos");
        assertTrue(
            allAuthors.stream().anyMatch(author -> author1.getAuthor().equals("Machado de Assis")),
            "O Autor Machado de assis está salvo"
        );
        assertTrue(
            allAuthors.stream().anyMatch(author -> author2.getAuthor().equals("John Green")),
            "O Autor John Green está salvo"
        );
    }

    @Test
    @DisplayName("Should return Author When findById")
    void ShouldReturnAuthorWhenFindById(){
        repository.save(author1);

        Author authorFound = repository.findById(author1.getId()).orElseThrow();

        assertThrows(NoSuchElementException.class, () -> {
            repository.findById(999).orElseThrow();
        });

        //Testando se o autor encontrado pelo código é igual ao autor salvo no banco
        assertEquals(author1, authorFound);
    }

    @Test
    @DisplayName("Test - Should Return nothing when deleteById")
    void testShouldReturnNothingWhenDeleteById(){
        
        repository.save(author1);

        repository.deleteById(author1.getId());

        boolean exists = repository.findById(author1.getId()).isPresent();
        assertFalse(exists, "O author deve ter sido deletado do banco");
    
    }

    @Test
    @DisplayName("Test - Should Return Updated Author when changing information of Author")
    void testShouldReturnUpdatedAuthorWhenChangeInformationOfAuthor(){
        
        repository.save(author1);
        
        Author authorToUpdate = new Author("Nome Antigo");
        repository.save(authorToUpdate);

        // Act - altera o nome e salva de novo
        authorToUpdate.setAuthor("Nome Atualizado");
        repository.save(authorToUpdate);

        // Assert - busca novamente do banco e verifica
        Author updatedAuthor = repository.findById(authorToUpdate.getId()).orElseThrow();
        assertEquals("Nome Atualizado", updatedAuthor.getAuthor());
       
    }
}
