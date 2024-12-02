package com.projetolivraria.livraria.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import com.projetolivraria.livraria.model.Book;

public interface BookRepository extends JpaRepository<Book, Long>{
    

    Book findByCode(long code);

    int countByCode(long code);

    List<Book> findByTitleContainingIgnoreCase(String title);
}
