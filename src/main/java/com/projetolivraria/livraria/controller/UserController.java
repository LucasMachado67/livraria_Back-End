package com.projetolivraria.livraria.controller;

import com.projetolivraria.livraria.model.user.User;
import com.projetolivraria.livraria.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("user")
public class UserController{
    @Autowired
    private UserService service;

    @GetMapping("/all")
    public List<User> findAllUsers() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public User findUserById(@PathVariable @Valid UUID id){
        return service.findById(id);
    }

     @DeleteMapping("/{id}")
     public void deleteByUserCode(@PathVariable @Valid UUID id){
         service.deleteById(id);
     }

     @PutMapping("/{id}")
     public ResponseEntity<User> editUser(@RequestBody @Valid User u) {
         try {
             User user = service.editUser(u);
             return ResponseEntity.ok(user);
         } catch (IllegalArgumentException e) {
             return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).build();
         }
     }
    

}