package com.projetolivraria.livraria.controller;


import com.projetolivraria.livraria.interfaces.BookDetailsDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import com.projetolivraria.livraria.model.Book;
import com.projetolivraria.livraria.service.BookService;

@Configuration
@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("book")
public class LibraryController {

    @Autowired
    private BookService service;

    //Get method to get all the books
    @GetMapping("/all")
    public Iterable<Book> findAllBooks() {
        try{
            return service.findAll();
        }catch (RuntimeException e){
            throw new RuntimeException("");
        }
    }
    //MediaType.APPLICATION_JSON_VALUE sends the data as Json Values
    //MediaType.MULTIPART_FORM_DATA_VALUE sends archives as multiple parts to upload the image
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
    //Get Method to get books based on the id
    @GetMapping("/{code}")
    public BookDetailsDTO findBookById(@PathVariable Long code){
        return service.findById(code);
    }
    //Delete method to delete books
    @DeleteMapping("/{code}")
    public void deleteByBookCode(@PathVariable Long code){
        service.deleteBook(code);
    }
    //Put method to edit book (NEED's REFACTOR)
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
    //Method to search for books (NEED's REFACTOR)
//    @GetMapping("/search")
//    public ResponseEntity<List<Book>> searchBooks(@RequestParam String query) {
//        List<Book> books = service.searchBooksByTitle(query);
//        return ResponseEntity.ok(books);
//    }
}
