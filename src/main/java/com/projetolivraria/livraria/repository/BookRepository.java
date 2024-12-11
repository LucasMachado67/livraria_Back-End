package com.projetolivraria.livraria.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

import com.projetolivraria.livraria.model.Book;

public interface BookRepository extends JpaRepository<Book, Long>{
    

    Optional<Book> findByCode(long code);

    int countByCode(long code);

    // List<Book> findByTitleContainingIgnoreCase(String title);
    @Query("SELECT b FROM Book b WHERE LOWER(b.title) LIKE LOWER(CONCAT('%', :query, '%'))")
    List<Book> searchByTitle(@Param("query") String query);
}
