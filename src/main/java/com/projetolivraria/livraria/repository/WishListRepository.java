package com.projetolivraria.livraria.repository;

import com.projetolivraria.livraria.model.Book;
import com.projetolivraria.livraria.model.WishList;
import com.projetolivraria.livraria.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WishListRepository extends JpaRepository<WishList, Long> {

    List<WishList> findByUser(User user);

    boolean existsByUserAndBook(User user, Book book);


    void deleteByUserAndBook(User user, Book book);
}
