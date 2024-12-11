package com.projetolivraria.livraria.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.projetolivraria.livraria.model.Book;
import com.projetolivraria.livraria.repository.BookRepository;
import java.util.List;
import java.io.IOException;


@Service
public class BookService {
    

    @Autowired
    private BookRepository bookRepository;
    
    // Validando a inserção de dados para um novo livro
    public Book saveNewBook(Book book, MultipartFile imageFile) throws IOException {
        if (book.getTitle() == null || book.getTitle().trim().isEmpty()) {
            throw new IllegalArgumentException("The title needs to be fulfilled");
        }

        if (book.getAuthor() == null || book.getAuthor().trim().isEmpty()) {
            throw new IllegalArgumentException("The author needs to be fulfilled");
        }

        if (imageFile != null && !imageFile.isEmpty()) {
            book.setImage(imageFile.getBytes());
        }

        return bookRepository.save(book);
    }
    

    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    public List<Book> searchBooksByTitle(String query) {
        return bookRepository.searchByTitle(query);
    }
}
