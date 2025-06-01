package com.projetolivraria.livraria.controller;

import com.projetolivraria.livraria.model.Author;
import com.projetolivraria.livraria.service.AuthorService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
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
    //Post method to register a new author
    @PostMapping("/new")
    public ResponseEntity<Author> newAuthor(@RequestBody @Valid Author author) {
        try{
            return ResponseEntity.ok(service.newAuthor(author));
        }catch (RuntimeException e ){
            System.out.println("Error while trying to create author" + e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }
    //Get method to get all the authors
    @GetMapping("/all")
    public Iterable<Author> AllAuthor(){
        try{
            return service.findAll();
        }catch (RuntimeException e){
            throw new RuntimeException("Error while trying to fetch data" + e.getMessage());
        }
    }
    //Get method to get an author based on the id
    @GetMapping("/{id}")
    public Author findAuthorById(@PathVariable @Valid int id){
        try {
            return service.findById(id);
        }catch (RuntimeException e){
            throw new RuntimeException("Error while trying to fetch data" + e.getMessage());
        }
    }
    //Put method to edit an author
    @PutMapping("/{id}")
    public Author editAuthor(@PathVariable @Valid int id, @RequestBody @Valid Author a){
        try{
            return service.editAuthor(a);
        }catch(EntityNotFoundException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Error: Data not found with ID: " + id);
        }catch(Exception e){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Unexpected error occurred while trying to fetch data" + e.getMessage());
        }
    }
    //Delete method to delete an author
    @DeleteMapping("/{id}")
    public void deleteByAuthorId(@PathVariable @Valid int id){
        try{
            service.deleteAuthor(id);
            System.out.println(HttpStatus.NO_CONTENT);
        }catch (RuntimeException e){
            throw new RuntimeException("Error while trying to fetch data" + e.getMessage());
        }
    }
}
