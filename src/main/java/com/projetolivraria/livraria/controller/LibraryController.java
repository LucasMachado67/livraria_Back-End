package com.projetolivraria.livraria.controller;


import com.projetolivraria.livraria.interfaces.BookDetailsDTO;
import com.projetolivraria.livraria.repository.BookRepository;
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
import com.projetolivraria.livraria.service.BookService;

@Configuration
@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("book")
public class LibraryController {

    @Autowired
    private BookService service;

    @Autowired
    private BookRepository repository;

    @GetMapping("/all")
    public Iterable<Book> findAllBooks() {
        try{
            return service.findAll();
        }catch (RuntimeException e){
            throw new RuntimeException("");
        }
    }
    // Antes as String Json eram a propria classe dos objetos
    @PostMapping(value = "/new", consumes = { MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE })
    public ResponseEntity<?> saveNewBook(
        @RequestPart("book") Book book,
        @RequestPart("image") MultipartFile imageFile) {
        try {
            if (imageFile != null && !imageFile.isEmpty()) {
                book.setImage(imageFile.getBytes());
            }
            Book savedBook = service.saveNewBook(book);
            return new ResponseEntity<>(savedBook, HttpStatus.CREATED);
        } catch (IllegalArgumentException | IOException e) {
            return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).build();
        }
    }

    @GetMapping("/{code}")
    public BookDetailsDTO findBookById(@PathVariable Long code){
        return service.findById(code);
    }

    @DeleteMapping("/{code}")
    public void deleteByBookCode(@PathVariable Long code){
        service.deleteBook(code);
    }

    @PutMapping(value = "/{code}", consumes = { MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE })
    public ResponseEntity<BookDetailsDTO> editBook(@RequestPart("book") Book b, @RequestPart("image") MultipartFile imageFile) throws IOException {
        try {
            if (imageFile != null && !imageFile.isEmpty()) {
                b.setImage(imageFile.getBytes());
            }
            BookDetailsDTO dto = service.editBook(b);
            return ResponseEntity.ok(dto);
        } catch (IllegalArgumentException | IOException e) {
            return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).build();
        }
    }

//    @GetMapping("/search")
//    public ResponseEntity<List<Book>> searchBooks(@RequestParam String query) {
//        List<Book> books = service.searchBooksByTitle(query);
//        return ResponseEntity.ok(books);
//    }
}
