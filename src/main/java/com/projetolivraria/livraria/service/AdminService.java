package com.projetolivraria.livraria.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.projetolivraria.livraria.model.Admin;
import com.projetolivraria.livraria.repository.AdminRepository;

@Service
public class AdminService {
    
    @Autowired
    private AdminRepository repository;

    public Admin saveNewAdmin(Admin admin){
        if(admin.getEmail() =="" || admin.getName() =="" || admin.getPassword() =="" || admin.getPhone() =="" || admin.getSex() == ""){
            throw new IllegalArgumentException("Error: Empty values are not allowed.");
        }
        return repository.save(admin);
        
    }

    public List<Admin> allAdmins(){

        try {
             return repository.findAll();
            
        } catch (Exception e) {
            System.err.println("Error while fetching Employees: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    public ResponseEntity<?> findAdminByCode(Long id){

        return new ResponseEntity<>(repository.findAdminById(id), HttpStatus.OK);

    }

}
