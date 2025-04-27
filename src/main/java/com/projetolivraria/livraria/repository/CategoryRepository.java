package com.projetolivraria.livraria.repository;

import com.projetolivraria.livraria.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {

    //Method to get all the categories registered with the selected book
    @Query(value = """
            SELECT
                b.id AS book_id,
                b.title,
                GROUP_CONCAT(c.category SEPARATOR ', ') AS categories
            FROM
                books b
            LEFT JOIN
                book_category bc ON b.id = bc.book_id
            LEFT JOIN
                categories c ON bc.category_id = c.id
            WHERE
            	b.id = 8
            GROUP BY
                b.id, b.title;
            """, nativeQuery = true)
    List<Category> getCategoriesOfSelectedBook(@Param("id") long code);
}
