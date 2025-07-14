package com.projetolivraria.livraria.controller;

import com.projetolivraria.livraria.model.Book;
import com.projetolivraria.livraria.model.Cart;
import com.projetolivraria.livraria.model.user.User;
import com.projetolivraria.livraria.service.BookService;
import com.projetolivraria.livraria.service.CartService;
import com.projetolivraria.livraria.service.UserAuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @Autowired
    private UserAuthenticationService userService;

    @Autowired
    private BookService bookService;

    @PostMapping("/add")
    public void addToCart(@RequestParam long bookId, @RequestParam int quantity){
        User user = userService.getAuthenticatedUser();
        Book book = bookService.findBookById(bookId);
        cartService.addToCart(user, book, quantity);
    }

    @GetMapping("/all")
    public List<Cart> getUserCart(){
        User user = userService.getAuthenticatedUser();
        return cartService.getCart(user);
        //return cart.stream()
                //.map(Cart::getBook)
                //.collect(Collectors.toList());
    }

    @DeleteMapping("/{bookId}")
    public void removeFromCart(@PathVariable long bookId){
        User user = userService.getAuthenticatedUser();
        Book book = bookService.findBookById(bookId);
        cartService.removeFromCart(user, book);
    }
}
