package com.projetolivraria.livraria.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.projetolivraria.livraria.interfaces.BookDetailsDTO;
import com.projetolivraria.livraria.model.Author;
import com.projetolivraria.livraria.model.Book;
import com.projetolivraria.livraria.model.Category;
import com.projetolivraria.livraria.repository.AuthorRepository;
import com.projetolivraria.livraria.repository.BookRepository;
import com.projetolivraria.livraria.repository.CategoryRepository;

@ExtendWith(MockitoExtension.class)
public class Test_BookService {


    @Mock
    private AuthorRepository authorRepository;

    @Mock
    private CategoryRepository categoryRepository;
    
    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookService bookService;

    private Book book1;
    private Book book2;

    @BeforeEach
    void setup(){
        //Deletando todos os livros caso exista        
        bookRepository.deleteAll();
        //Criando authores para colocar nos livros
        Author author = new Author("Machado de Assis");
        author.setId(1);
        Author author2 = new Author("John Green");
        author2.setId(2);

        Category category1 = new Category("Ação");
        category1.setId(1);
        Category category2 = new Category("Romance");
        category2.setId(2);
        Category category3 = new Category("Aventura");
        category3.setId(3);

        // Mockando o retorno do authorRepository
        when(authorRepository.findById(1)).thenReturn(Optional.of(author));
        when(authorRepository.findById(2)).thenReturn(Optional.of(author2));

        // Mockando o retorno do categoryRepository
        when(categoryRepository.findById(1)).thenReturn(Optional.of(category1));
        when(categoryRepository.findById(2)).thenReturn(Optional.of(category2));
        when(categoryRepository.findById(3)).thenReturn(Optional.of(category3));
        //Criando os livros
        book1 = new Book(
            "Clean Code",
            author,
            2008,
            new BigDecimal("99.90"),
            450,
            "Inglês",
            "Capa dura",
            new byte[0],
            10,
            "Um guia de boas práticas de programação.",
            new ArrayList<>(List.of(category1, category3))
        );
        book2 = new Book(
            "City of Paper",
            author2,
            2015,
            new BigDecimal("120.90"),
            296,
            "Inglês",
            "Capa Mole",
            new byte[0],
            4,
            "Um livro de Romance",
            new ArrayList<>(List.of(category2))
        );

    }

    @Test
    @DisplayName("Should create new Book when addNewBook")
    void testCreateNewBook() throws IOException{

        when(bookRepository.save(any(Book.class))).thenReturn(book1);
        when(bookRepository.findById(book1.getCode())).thenReturn(Optional.of(book1));

        bookService.saveNewBook(book1); // método real, sem mock
        BookDetailsDTO foundBook = bookService.findById(book1.getCode()); // método real

        // Assert
        assertEquals(book1.getTitle(), foundBook.getTitle());
        assertEquals(book1.getAuthor().getAuthor(), foundBook.getAuthorName());
        assertEquals(book1.getPublicationYear(), foundBook.getPublicationYear());
        assertEquals(book1.getLanguage(), foundBook.getLanguage());
        assertEquals(book1.getPrice(), foundBook.getPrice());
    }


}
