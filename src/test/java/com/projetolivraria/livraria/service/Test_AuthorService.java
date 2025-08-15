package com.projetolivraria.livraria.service;

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

import com.projetolivraria.livraria.model.Author;
import com.projetolivraria.livraria.repository.AuthorRepository;

@ExtendWith(MockitoExtension.class)
public class Test_AuthorService {

    @Mock
    private AuthorRepository authorRepository;

    @InjectMocks
    private AuthorService authorService;

    Author author1;
    Author author2;

    @BeforeEach
    void setup(){
        author1 = new Author();
        author1.setId(1);
        author1.setAuthor("John Green");
        author2 = new Author();
        author2.setId(2);
        author2.setAuthor("Machado de Assis");
    }

    @Test
    @DisplayName("Should save Author")
    void testSaveAuthorMethod(){

        when(authorRepository.save(any(Author.class))).thenReturn(author1);

        Author saved = authorService.newAuthor(author1);

        assertEquals("John Green", saved.getAuthor());
        assertEquals(1, saved.getId());
    }

    @Test
    @DisplayName("Should return author by id")
    void testFindAuthorById(){
        when(authorRepository.findById(1)).thenReturn(Optional.of(author1));

        Author result = authorService.findById(1);

        assertEquals("John Green", result.getAuthor());
    }

    @Test
    @DisplayName("Should return all authors")
    void testFindAllAuthors(){
        List<Author> list = Arrays.asList(author1,author2);
        when(authorRepository.findAll()).thenReturn(list);

        List<Author> result = authorService.findAll();

        assertEquals(list.size(), result.size());
        assertEquals("Machado de Assis", result.get(1).getAuthor());
    }

    @Test
    @DisplayName("Should delete author by id")
    void testDeleteAuthor(){
        doNothing().when(authorRepository).deleteById(1);

        authorService.deleteAuthor(1);

        verify(authorRepository, times(1)).deleteById(1);

    }

    @Test
    @DisplayName("Should update author")
    void testUpdateAuthor(){
        Author existingAuthor = new Author();
        existingAuthor.setId(1);
        existingAuthor.setAuthor("John Green");

        Author updatedAuthor = new Author();
        updatedAuthor.setId(1);
        updatedAuthor.setAuthor("Greamy Simsion");

        when(authorRepository.findById(1)).thenReturn(Optional.of(existingAuthor));
        when(authorRepository.save(any(Author.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Author result = authorService.editAuthor(updatedAuthor);

        assertEquals("Greamy Simsion", result.getAuthor());
        assertEquals(1, result.getId());
    
    }
}
