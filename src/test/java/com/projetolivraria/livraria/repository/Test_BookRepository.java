package com.projetolivraria.livraria.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import com.projetolivraria.livraria.interfaces.BookDetailsDTO;
import com.projetolivraria.livraria.model.Author;
import com.projetolivraria.livraria.model.Book;
import com.projetolivraria.livraria.model.Category;

@DataJpaTest
@ActiveProfiles("test")
public class Test_BookRepository {

    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private AuthorRepository authorRepository;

    private Book book1;
    private Book book2;


    //Iniciando objetos padrões para os testes
    @BeforeEach
    void setup(){
        //Deletando todos os livros caso exista        
        bookRepository.deleteAll();
        //Criando authores para colocar nos livros
        Author author = new Author("Machado de Assis");
        Author author2 = new Author("John Green");
        authorRepository.saveAll(List.of(author,author2));
        //Criando categorias para colocar nos livros
        Category category1 = new Category("Ação");
        Category category2 = new Category("Romance");
        Category category3 = new Category("Aventura");
        categoryRepository.saveAll(List.of(category1,category2,category3));

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
    @DisplayName("Test - Should Create new Book when addBook")
    void testShouldSaveNewBookWhenAddBook(){
        bookRepository.save(book1);
        bookRepository.save(book2);
        //Assert - recupera os livros do banco
        List<Book> allBooks = bookRepository.findAll();

        assertEquals(2, allBooks.size(), "Deve retornar os dois livros salvos");
        assertTrue(
            allBooks.stream().anyMatch(book -> book.getTitle().equals("Clean Code")),
            "Livro 'Clean Code' deve estar salvo"
        );
        assertTrue(
            allBooks.stream().anyMatch(book -> book.getTitle().equals("City of Paper")),
            "Livro 'City of Paper' deve estar salvo"
        );

    }

    @Test
    @DisplayName("Test - Should return Book when FindById")
    void testShouldReturnBookWhenFindById(){
        bookRepository.save(book1);

        Book bookFound = bookRepository.findById(book1.getCode()).orElseThrow();

        //Testa se a exceção é lançada caso o código não exista
        assertThrows(NoSuchElementException.class, () -> {
            bookRepository.findById(999L).orElseThrow();
        });

        //Testando se o livro encontrado pelo código é igual ao livro salvo no banco
        assertEquals(book1, bookFound);
    }

    @Test
    @DisplayName("Test - Should return book(s) when search by title")
    void testShouldReturnBookWhenSearchByTitle(){

        bookRepository.save(book1);
        bookRepository.save(book2);

        List<BookDetailsDTO> allBooks = bookRepository.searchByTitle("Clean");
        List<BookDetailsDTO> allBooks2 = bookRepository.searchByTitle("City");
        List<BookDetailsDTO> allBooks3 = bookRepository.searchByTitle("e");

        assertEquals(1, allBooks.size());
        //Testando se os títulos são iguais
        assertEquals(allBooks.get(0).getTitle(), book1.getTitle());

        //Verificando o AllBooks2
        assertEquals(1, allBooks2.size());
        //Testando se os títulos são iguais
        assertEquals(allBooks2.get(0).getTitle(), book2.getTitle());
        //Verificando o AllBooks3
        assertEquals(2, allBooks3.size());
        //Testando se os títulos são iguais
        assertEquals(allBooks3.get(0).getTitle(), book1.getTitle());
        assertEquals(allBooks3.get(1).getTitle(), book2.getTitle());
    }

    @Test
    @DisplayName("Test - Should return book(s) when search by author")
    void testShouldReturnBookWhenSearchByAuthor(){

        bookRepository.save(book1);
        bookRepository.save(book2);

        List<BookDetailsDTO> allBooks = bookRepository.searchByAuthor("Machado");
        List<BookDetailsDTO> allBooks2 = bookRepository.searchByAuthor("John");
        List<BookDetailsDTO> allBooks3 = bookRepository.searchByAuthor("e");

        assertEquals(1, allBooks.size());
        //Testando se os títulos são iguais
        assertEquals(allBooks.get(0).getTitle(), book1.getTitle());

        //Verificando o AllBooks2
        assertEquals(1, allBooks2.size());
        //Testando se os títulos são iguais
        assertEquals(allBooks2.get(0).getTitle(), book2.getTitle());
        //Verificando o AllBooks3
        assertEquals(2, allBooks3.size());
        //Testando se os títulos são iguais
        assertEquals(allBooks3.get(0).getTitle(), book1.getTitle());
        assertEquals(allBooks3.get(1).getTitle(), book2.getTitle());
    }

    @Test
    @DisplayName("Test - Should Return nothing when deleteById")
    void testShouldReturnNothingWhenDeleteById(){
        
        bookRepository.save(book1);
        bookRepository.save(book2);

        bookRepository.deleteById(book1.getCode());

        boolean exists = bookRepository.findById(book1.getCode()).isPresent();
        assertFalse(exists, "O livro deve ter sido deletado do banco");
    
    }

    @Test
    @DisplayName("Test - Should Return Updated Book when changing information of book")
    void testShouldReturnUpdatedBookWhenChangeInformationOfBook(){
        
        bookRepository.save(book1);
        Author originalAuthor = authorRepository.save(new Author("Autor Original"));
        Author newAuthor = authorRepository.save(new Author("Fiódor Dostoiévski"));
        
        book1.setAuthor(originalAuthor);
        bookRepository.save(book1);

        // Act - alterar o autor
        book1.setAuthor(newAuthor);
        bookRepository.save(book1);

        // Assert - buscar do banco e validar a alteração
        Book updatedBook = bookRepository.findById(book1.getCode()).orElseThrow();
        assertEquals("Fiódor Dostoiévski", updatedBook.getAuthor().getAuthor());
       
    }
}
