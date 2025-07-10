package com.projetolivraria.livraria.service;

import com.projetolivraria.livraria.model.Book;
import com.projetolivraria.livraria.model.WishList;
import com.projetolivraria.livraria.model.user.User;
import com.projetolivraria.livraria.repository.WishListRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WishListService {

    @Autowired
    private WishListRepository repository;

    public void addToWishList(User user, Book book){

        if(!repository.existsByUserAndBook(user, book)){
            WishList wish = new WishList();
            wish.setUser(user);
            wish.setBook(book);
            repository.save(wish);
        }
    }

    public List<WishList> getWishList(User user){
        return repository.findByUser(user);
    }

    @Transactional
    public void removeFromWishList(User user, Book book){
        repository.deleteByUserAndBook(user,book);
    }
}
