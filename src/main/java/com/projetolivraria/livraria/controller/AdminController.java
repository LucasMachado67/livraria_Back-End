package com.projetolivraria.livraria.controller;



import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.projetolivraria.livraria.model.Admin;
import com.projetolivraria.livraria.service.AdminService;
import org.springframework.web.server.ResponseStatusException;

@Configuration
@RestController
@RequestMapping("admin")
public class AdminController {
    
    @Autowired
    private AdminService service;
    //Method to register a new admin
    @PostMapping("/new")
    public ResponseEntity<Admin> newAdmin(@RequestBody Admin admin) {
        try{
            return ResponseEntity.ok(service.saveNewAdmin(admin));
        }catch (RuntimeException e){
            System.out.println("Error while trying to create admin " + e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }
    //Method to get all the admins
    @GetMapping("/all")
    public Iterable<Admin> AllAdmins(){
        try{
            return service.allAdmins();
        }catch (RuntimeException e){
            throw new RuntimeException("Error while trying to fetch data" + e.getMessage());
        }
    }
    //Method to get an admin based on the id
    @GetMapping("/{id}")
    public Admin findAdminById(@PathVariable Integer id){
        try {
            return service.findById(id);
        }catch (RuntimeException e){
            throw new RuntimeException("Error while trying to fetch data" + e.getMessage());
        }
    }
    //Method to delete an admin
    @DeleteMapping("/{id}")
    public void deleteByAdminId(@PathVariable int id){
        try{
            service.removeAdmin(id);
            System.out.println(HttpStatus.NO_CONTENT);
        }catch (RuntimeException e){
            throw new RuntimeException("Error while trying to fetch data" + e.getMessage());
        }
    }
    //Method to edit an admin
    @PutMapping("/{id}")
    public Admin editAdmin(@PathVariable int id, @RequestBody Admin a) {
        try{
            return service.editAdmin(a);
        }catch(EntityNotFoundException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Error: Data not found with ID: " + id);
        }catch(Exception e){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Unexpected error occurred while trying to fetch data" + e.getMessage());
        }
    }
    
}
