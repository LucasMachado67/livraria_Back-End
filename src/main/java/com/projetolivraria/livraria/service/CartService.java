package com.projetolivraria.livraria.service;

import com.projetolivraria.livraria.model.Book;
import com.projetolivraria.livraria.model.Cart;
import com.projetolivraria.livraria.model.user.User;
import com.projetolivraria.livraria.repository.CartRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartService {

    @Autowired
    private CartRepository repository;

    public void addToCart(User user, Book book, int quantitySelected){

        if(!repository.existsByUserAndBook(user, book)){
            Cart cart = new Cart();
            cart.setQuantitySelected(quantitySelected);
            cart.setUser(user);
            cart.setBook(book);
            repository.save(cart);
        }
    }

    public List<Cart> getCart(User user){
        return repository.findByUser(user);
    }

    @Transactional
    public void removeFromCart(User user, Book book){
        repository.deleteByUserAndBook(user,book);
    }
}
