package com.projetolivraria.livraria.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

import com.projetolivraria.livraria.model.Book;
import com.projetolivraria.livraria.model.Message;
import com.projetolivraria.livraria.repository.BookRepository;
import com.projetolivraria.livraria.service.BookService;

@Configuration
@RestController
// @CrossOrigin(origins = "http://localhost:4200")
@CrossOrigin(origins = "https://livraria-front-end-admin.vercel.app")
public class LibraryController {

    @Autowired
    private Message message;

    @Autowired
    private BookRepository bookAction;

    @Autowired
    private BookService bookService;

    @GetMapping("/books")
    public Iterable<Book> findAllBooks() {
        List<Book> books = bookAction.findAll();
        return books;
    }
    
    @PostMapping(value = "/newBook", consumes = { MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE })
    public ResponseEntity<?> saveNewBook(
        @RequestPart("book") Book book, 
        @RequestPart("image") MultipartFile imageFile) {

        try {
            Book savedBook = bookService.saveNewBook(book, imageFile);
            return new ResponseEntity<>(savedBook, HttpStatus.CREATED);

        } catch (IllegalArgumentException e) {
            message.setMessage("Field is missing");
            return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);

        } catch (IOException e) {
            message.setMessage("Error processing the image file");
            return new ResponseEntity<>(message, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/books/{code}")
    public Optional<Book> findBookById(@PathVariable Long code){
        return bookAction.findByCode(code);
    }

    @DeleteMapping("/books/{code}")
    public void deleteByBookCode(@PathVariable Long code){
        bookAction.deleteById(code);
    }

    @PutMapping("/books/{code}")
    public Book editBook(@PathVariable Long code, @RequestBody Book b) {
    
        Optional<Book> optionalBook = bookAction.findById(code);
        Book book = optionalBook.get();
        book.setTitle(b.getTitle());
        book.setAuthor(b.getAuthor());
        book.setYear(b.getYear());
        book.setPrice(b.getPrice());
        book.setPages(b.getPages());
        book.setLanguage(b.getLanguage());
        book.setBookCover(b.getBookCover());
        book.setImage(b.getImage());
        return bookAction.save(book);
    }

    @GetMapping("/books/search")
    public ResponseEntity<List<Book>> searchBooks(@RequestParam String query) {
        List<Book> books = bookService.searchBooksByTitle(query);
        return ResponseEntity.ok(books);
    }
}
