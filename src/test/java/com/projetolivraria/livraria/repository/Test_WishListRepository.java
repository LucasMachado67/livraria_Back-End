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
import com.projetolivraria.livraria.model.Category;
import com.projetolivraria.livraria.model.WishList;
import com.projetolivraria.livraria.model.enums.UserRole;
import com.projetolivraria.livraria.model.user.User;

@DataJpaTest
@ActiveProfiles("test")
public class Test_WishListRepository {

    @Autowired
    private WishListRepository wishListRepository;
    @Autowired
    private AuthorRepository authorRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private UserRepository userRepository;

    private WishList wishListBook1;
    private WishList wishListBook2;

    private Book book1;
    private Book book2;

    private User user;

    @BeforeEach
    void setup(){

        wishListRepository.deleteAll();
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
        

        wishListBook1 = new WishList(
            user,
            book1
        );
        wishListBook2 = new WishList(
            user,
            book2
        );
    }

    @Test
    @DisplayName("Test - Should Save a Book in Wishlist When Save method called")
    void testSaveWishListMethod(){

        wishListRepository.save(wishListBook1);
        wishListRepository.save(wishListBook2);
        
        List<WishList> allInWishList = wishListRepository.findAll();

        assertEquals(2, allInWishList.size());
        assertEquals("Gustavo", allInWishList.get(0).getUser().getName());
        assertEquals("Clean Code", allInWishList.get(0).getBook().getTitle());

        assertEquals("Gustavo", allInWishList.get(1).getUser().getName());
        assertEquals("City of Paper", allInWishList.get(1).getBook().getTitle());

    }

    @Test
    @DisplayName("Test - Should find books in WishList When FindByUser Method is Called")
    void testFindByuserWishListMethod(){

        wishListRepository.save(wishListBook1);
        wishListRepository.save(wishListBook2);
        
        List<WishList> foundWishLists = wishListRepository.findByUser(wishListBook1.getUser());

        assertEquals(2, foundWishLists.size());
        assertEquals("Gustavo", foundWishLists.get(0).getUser().getName());
        assertEquals("Clean Code", foundWishLists.get(0).getBook().getTitle());

        assertEquals("Gustavo", foundWishLists.get(1).getUser().getName());
        assertEquals("City of Paper", foundWishLists.get(1).getBook().getTitle());

    }

    @Test
    @DisplayName("Test - Should return true when Exists By User And Book")
    void testExistsByUserAndBookWishListMethod(){

        wishListRepository.save(wishListBook1);

        Boolean wishListBook1Exists = wishListRepository.existsByUserAndBook(wishListBook1.getUser(), wishListBook1.getBook());
        Boolean wishListBook2Exists = wishListRepository.existsByUserAndBook(wishListBook2.getUser(), wishListBook2.getBook());

        assertTrue(wishListBook1Exists);
        assertFalse(wishListBook2Exists);
    }

    @Test
    @DisplayName("Test - Should Delete a Book in WishList When Delete Method is Called")
    void testDeleteByUserAndBookWishListMethod(){

        wishListRepository.save(wishListBook1);
        wishListRepository.save(wishListBook2);
        
        wishListRepository.deleteByUserAndBook(wishListBook1.getUser(), wishListBook1.getBook());
        
        List<WishList> remainingBooksInCart = wishListRepository.findAll();

        assertEquals(1, remainingBooksInCart.size());

    }
}
