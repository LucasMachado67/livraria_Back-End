package com.projetolivraria.livraria.service;

import com.projetolivraria.livraria.model.Author;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.projetolivraria.livraria.model.Book;
import com.projetolivraria.livraria.repository.BookRepository;
import java.util.List;
import java.io.IOException;
import java.util.Optional;


@Service
public class BookService {
    

    @Autowired
    private BookRepository repository;

    public Book saveNewBook(Book book, MultipartFile imageFile) throws IOException {
        if (book.getTitle() == null || book.getTitle().trim().isEmpty()) {
            throw new IllegalArgumentException("The title needs to be fulfilled");
        }

        if (imageFile != null && !imageFile.isEmpty()) {
            book.setImage(imageFile.getBytes());
        }

        return repository.save(book);
    }

    public Book findById(long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Author with id: " + id + "not found"));
    }
    

    public List<Book> findAll() {
        return repository.findAll();
    }

    public Book editBook(Book book){
        Book bookToEdit = repository.findById(book.getCode())
                .orElseThrow(() -> new RuntimeException("Book with id: " + book.getCode() + "not found"));
        bookToEdit.setTitle(book.getTitle());
        bookToEdit.setAuthor(book.getAuthor());
        bookToEdit.setYear(book.getYear());
        bookToEdit.setPrice(book.getPrice());
        bookToEdit.setPages(book.getPages());
        bookToEdit.setLanguage(book.getLanguage());
        bookToEdit.setBookCover(book.getBookCover());
        bookToEdit.setImage(book.getImage());
        bookToEdit.setQuantity(book.getQuantity());
        return repository.save(book);
    }

    public void deleteBook(long id) {
        if (id < 0) {
            throw new IllegalArgumentException("Invalid ID");
        }
        repository.deleteById(id);
        ResponseEntity.noContent().build();
    }
    public List<Book> searchBooksByTitle(String query) {
        return repository.searchByTitle(query);
    }
}
