package com.projetolivraria.livraria.controller;

import com.projetolivraria.livraria.model.Author;
import com.projetolivraria.livraria.service.AuthorService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RequestMapping("author")
@RestController
public class AuthorController {

    @Autowired
    private AuthorService service;

    @PostMapping("/new")
    public ResponseEntity<Author> newAuthor(@RequestBody Author author) {
        try{
            return ResponseEntity.ok(service.newAuthor(author));
        }catch (RuntimeException e ){
            System.out.println("Error while trying to create author" + e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/all")
    public Iterable<Author> AllAuthor(){
        try{
            return service.findAll();
        }catch (RuntimeException e){
            throw new RuntimeException("Error while trying to fetch data" + e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public Author findAuthorById(@PathVariable int id){
        try {
            return service.findById(id);
        }catch (RuntimeException e){
            throw new RuntimeException("Error while trying to fetch data" + e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public Author editAuthor(@PathVariable int id, @RequestBody Author a){
        try{
            return service.editAuthor(a);
        }catch(EntityNotFoundException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Error: Data not found with ID: " + id);
        }catch(Exception e){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Unexpected error occurred while trying to fetch data" + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public void deleteByAuthorId(@PathVariable int id){
        try{
            service.deleteAuthor(id);
            System.out.println(HttpStatus.NO_CONTENT);
        }catch (RuntimeException e){
            throw new RuntimeException("Error while trying to fetch data" + e.getMessage());
        }
    }
}
