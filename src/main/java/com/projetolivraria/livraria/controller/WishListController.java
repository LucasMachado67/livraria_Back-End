package com.projetolivraria.livraria.controller;

import com.projetolivraria.livraria.model.Book;
import com.projetolivraria.livraria.model.WishList;
import com.projetolivraria.livraria.model.user.User;
import com.projetolivraria.livraria.service.BookService;
import com.projetolivraria.livraria.service.UserAuthenticationService;
import com.projetolivraria.livraria.service.WishListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("wishlist")
public class WishListController {

    @Autowired
    private WishListService wishListService;

    @Autowired
    private UserAuthenticationService userService;

    @Autowired
    private BookService bookService;

    @PostMapping("/add")
    public void addToWishList(@RequestParam long bookId){
        User user = userService.getAuthenticatedUser();
        Book book = bookService.findBookById(bookId);
        wishListService.addToWishList(user, book);
    }

    @GetMapping("/all")
    public List<Book> getUserWishList(){
        User user = userService.getAuthenticatedUser();
        List<WishList> wishList = wishListService.getWishList(user);
        return wishList.stream()
                .map(WishList::getBook)
                .collect(Collectors.toList());
    }

    @DeleteMapping("/{bookId}")
    public void removeFromWishList(@PathVariable long bookId){
        User user = userService.getAuthenticatedUser();
        Book book = bookService.findBookById(bookId);
        wishListService.removeFromWishList(user, book);
    }


}
