package com.projetolivraria.livraria.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

import com.projetolivraria.livraria.model.Book;
import com.projetolivraria.livraria.repository.BookRepository;
import com.projetolivraria.livraria.service.BookService;

@Configuration
@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("book")
public class LibraryController {

    @Autowired
    private BookService service;

    @GetMapping("/all")
    public Iterable<Book> findAllBooks() {
        try{
            return service.findAll();
        }catch (RuntimeException e){
            throw new RuntimeException("");
        }
    }
    
    @PostMapping(value = "/new", consumes = { MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE })
    public ResponseEntity<?> saveNewBook(
        @RequestPart("book") Book book, 
        @RequestPart("image") MultipartFile imageFile) {

        try {
            Book savedBook = service.saveNewBook(book, imageFile);
            return new ResponseEntity<>(savedBook, HttpStatus.CREATED);

        } catch (IllegalArgumentException | IOException e) {
            return (ResponseEntity<?>) ResponseEntity.status(405);

        }
    }

    @GetMapping("/{code}")
    public Book findBookById(@PathVariable Long code){
        return service.findById(code);
    }

    @DeleteMapping("/{code}")
    public void deleteByBookCode(@PathVariable Long code){
        service.deleteBook(code);
    }

    @PutMapping(value = "/{code}", consumes = { MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE })
    public Book editBook(@PathVariable Long code, @RequestBody Book b, @RequestPart("image") MultipartFile imageFile) throws IOException {
        return service.saveNewBook(b,imageFile);
    }

    @GetMapping("/search")
    public ResponseEntity<List<Book>> searchBooks(@RequestParam String query) {
        List<Book> books = service.searchBooksByTitle(query);
        return ResponseEntity.ok(books);
    }
}
