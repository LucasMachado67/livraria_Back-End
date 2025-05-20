package com.projetolivraria.livraria.repository;

import com.projetolivraria.livraria.interfaces.BookDetailsDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

import com.projetolivraria.livraria.model.Book;

public interface BookRepository extends JpaRepository<Book, Long> {
    //NEED'S REFACTOR
    //List<Book> findByTitleContainingIgnoreCase(String title);
    //@Query("SELECT b FROM Book b WHERE LOWER(b.title) LIKE LOWER(CONCAT('%', :query, '%'))")
    //List<Book> searchByTitle(@Param("query") String query);

    //Method to get all the data of one book
    @Query(value = """
            SELECT
                b.id AS bookId,
                b.title,
                b.year,
                b.price,
                b.pages,
                b.language,
                b.book_cover AS bookCover,
                b.image,
                b.quantity,
                b.description,
                a.id AS authorId,
                a.author AS authorName,
                GROUP_CONCAT(c.category SEPARATOR ', ') AS categories
            FROM
                books b
            JOIN
                authors a ON b.author_id = a.id
            LEFT JOIN
                book_category bc ON b.id = bc.book_id
            LEFT JOIN
                categories c ON bc.category_id = c.id
            WHERE
                b.id = :id
            GROUP BY
                b.id, b.title, b.year, b.price, b.pages, b.language, b.book_cover,
                b.image, b.quantity, b.description, a.id, a.author
            """, nativeQuery = true)
    BookDetailsDTO findBookDetailsById(@Param("id") Long id);

    //Search Book By Title
    @Query(value = """
                SELECT
                		b.id AS bookId,
                        b.title,
                        b.year,
                        b.price,
                        b.pages,
                        b.language,
                        b.book_cover AS bookCover,
                        b.image,
                        b.quantity,
                        b.description,
                        a.id AS authorId,
                        a.author AS authorName,
                GROUP_CONCAT(c.category SEPARATOR ', ') AS categories
                FROM
                    books b
                JOIN
                    authors a ON b.author_id = a.id
                LEFT JOIN
                    book_category bc ON b.id = bc.book_id
                LEFT JOIN
                    categories c ON bc.category_id = c.id
                WHERE
                    LOWER(b.title) LIKE LOWER(CONCAT('%', :term, '%'))
                GROUP BY
                    b.id, b.title, b.year, b.price, b.pages, b.language, b.book_cover,
                	b.image, b.quantity, b.description, a.id, a.author;
            """, nativeQuery = true)
    List<BookDetailsDTO> searchByTitle(@Param("term") String term);


    @Query(value = """
                SELECT
                		b.id AS bookId,
                        b.title,
                        b.year,
                        b.price,
                        b.pages,
                        b.language,
                        b.book_cover AS bookCover,
                        b.image,
                        b.quantity,
                        b.description,
                        a.id AS authorId,
                        a.author AS authorName,
                GROUP_CONCAT(c.category SEPARATOR ', ') AS categories
                FROM
                    books b
                JOIN
                    authors a ON b.author_id = a.id
                LEFT JOIN
                    book_category bc ON b.id = bc.book_id
                LEFT JOIN
                    categories c ON bc.category_id = c.id
                WHERE
                    LOWER(a.author) LIKE LOWER(CONCAT('%', :term, '%'))
                GROUP BY
                    b.id, b.title, b.year, b.price, b.pages, b.language, b.book_cover,
                	b.image, b.quantity, b.description, a.id, a.author;
            """, nativeQuery = true)
    List<BookDetailsDTO> searchByAuthor(@Param("term") String term);
}
