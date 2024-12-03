package com.projetolivraria.livraria.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RestController;

import com.projetolivraria.livraria.model.Admin;
import com.projetolivraria.livraria.repository.AdminRepository;
import com.projetolivraria.livraria.service.AdminService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Configuration
@RestController

@CrossOrigin(origins = "https://livraria-front-end-admin.vercel.app/")
public class AdminController {
    
    @Autowired
    private AdminService service;
    @Autowired
    private AdminRepository repository;

    @PostMapping("/newAdmin")
    public ResponseEntity<Admin> newAdmin(@RequestBody Admin admin) {
        return ResponseEntity.ok(service.saveNewAdmin(admin));
    }

    @GetMapping("/allAdmins")
    public Iterable<Admin> AllAdmins(){
        List<Admin> admins = service.allAdmins();
        return admins;
    }

    @GetMapping("/allAdmins/{id}")
    public Admin findAdminById(@PathVariable Long id){
        return repository.findAdminById(id);
    }

    @DeleteMapping("/allAdmins/{id}")
    public void deleteByAdminId(@PathVariable Long id){
        repository.deleteById(id);
    }

    @PutMapping("/allAdmins/{id}")
    public Admin editAdmin(@PathVariable Long id, @RequestBody Admin a) {
        
        Optional<Admin> optionalAdmin = repository.findById(id);
        Admin admin = optionalAdmin.get();
        admin.setEmail(a.getEmail());
        admin.setName(a.getName());
        admin.setPassword(a.getPassword());
        admin.setPhone(a.getPhone());
        admin.setSex(a.getSex());
        return repository.save(admin);
        
    }
    
}
