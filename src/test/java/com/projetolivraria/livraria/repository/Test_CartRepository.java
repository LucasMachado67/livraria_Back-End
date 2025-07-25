package com.projetolivraria.livraria.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import com.projetolivraria.livraria.model.Author;
import com.projetolivraria.livraria.model.Book;
import com.projetolivraria.livraria.model.Cart;
import com.projetolivraria.livraria.model.Category;
import com.projetolivraria.livraria.model.enums.UserRole;
import com.projetolivraria.livraria.model.user.User;

@DataJpaTest
@ActiveProfiles("test")
public class Test_CartRepository {

    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private AuthorRepository authorRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private UserRepository userRepository;

    private Cart cart1;
    private Cart cart2;

    private Book book1;
    private Book book2;

    private User user;

    @BeforeEach
    void setup(){

        cartRepository.deleteAll();
        //Criando as categorias e o autores para o livro
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
        bookRepository.saveAll(List.of(book1,book2));
        
        user = new User(
            "Gustavo",
            "gustavo@gmail.com",
            "123321",
            "47999999999",
            "Male",
            UserRole.USER
        );
        userRepository.save(user);
        

        cart1 = new Cart(
            user,
            book1,
            12
        );
        cart2 = new Cart(
            user,
            book2,
            1
        );
    }

    @Test
    @DisplayName("Test - Should Save a Book in Cart When Save method called")
    void testSaveCartMethod(){

        cartRepository.save(cart1);
        cartRepository.save(cart2);
        
        List<Cart> allInCart = cartRepository.findAll();

        assertEquals(2, allInCart.size());
        assertEquals("Gustavo", allInCart.get(0).getUser().getName());
        assertEquals("Clean Code", allInCart.get(0).getBook().getTitle());
        assertEquals(12, allInCart.get(0).getQuantitySelected());

        assertEquals("Gustavo", allInCart.get(1).getUser().getName());
        assertEquals("City of Paper", allInCart.get(1).getBook().getTitle());
        assertEquals(1, allInCart.get(1).getQuantitySelected());

    }

    @Test
    @DisplayName("Test - Should find books in Cart When FindByUser Method is Called")
    void testFindByuserCartMethod(){

        cartRepository.save(cart1);
        cartRepository.save(cart2);
        
        List<Cart> foundCart = cartRepository.findByUser(cart1.getUser());

        assertEquals(2, foundCart.size());
        assertEquals("Gustavo", foundCart.get(0).getUser().getName());
        assertEquals("Clean Code", foundCart.get(0).getBook().getTitle());
        assertEquals(12, foundCart.get(0).getQuantitySelected());

        assertEquals("Gustavo", foundCart.get(1).getUser().getName());
        assertEquals("City of Paper", foundCart.get(1).getBook().getTitle());
        assertEquals(1, foundCart.get(1).getQuantitySelected());

    }

    @Test
    @DisplayName("Test - Should return true when Exists By User And Book")
    void testExistsByUserAndBookCartMethod(){

        cartRepository.save(cart1);

        Boolean cart1Exists = cartRepository.existsByUserAndBook(cart1.getUser(), cart1.getBook());
        Boolean cart2Exists = cartRepository.existsByUserAndBook(cart2.getUser(), cart2.getBook());

        assertTrue(cart1Exists);
        assertFalse(cart2Exists);
    }

    @Test
    @DisplayName("Test - Should Delete a Book in Cart When Delete Method is Called")
    void testDeleteByUserAndBookCartMethod(){

        cartRepository.save(cart1);
        cartRepository.save(cart2);
        
        cartRepository.deleteByUserAndBook(cart1.getUser(), cart1.getBook());
        
        List<Cart> remainingBooksInCart = cartRepository.findAll();

        assertEquals(1, remainingBooksInCart.size());

    }
}
