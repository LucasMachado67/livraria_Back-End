package com.projetolivraria.livraria.service;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.projetolivraria.livraria.model.Admin;
import com.projetolivraria.livraria.repository.AdminRepository;

@Service
public class AdminService {
    
    @Autowired
    private AdminRepository repository;

    public Admin saveNewAdmin(Admin admin){
        if(admin.getEmail().isEmpty() || admin.getName().isEmpty() || admin.getPassword().isEmpty() || admin.getPhone().isEmpty() || admin.getSex().isEmpty()){
            throw new IllegalArgumentException("Error: Empty values are not allowed.");
        }
        return repository.save(admin);
    }

    public Admin findById(int id){
        if(id <= 0){
            throw new IllegalArgumentException("Invalid ID");
        }else{
            return repository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Admin with id: " + id + "not found"));
        }
    }

    public List<Admin> allAdmins(){

        try {
            List<Admin> admins = repository.findAll();
            if (admins.isEmpty()) {
                System.out.println("No data found");
                return admins;
            }
            return admins;
            
        } catch (Exception e) {
            System.err.println("Error while fetching Employees: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    public Admin editAdmin(Admin admin) {
        Admin adminToEdit = repository.findById(admin.getId())
                .orElseThrow(() -> new RuntimeException("Admin with id: " + admin.getId() + "not found"));
        adminToEdit.setEmail(admin.getEmail());
        adminToEdit.setName(admin.getName());
        adminToEdit.setPassword(admin.getPassword());
        adminToEdit.setPhone(admin.getPassword());
        adminToEdit.setSex(admin.getSex());
        return repository.save(admin);
    }

    public void removeAdmin(int id) {
        if (id < 0) {
            throw new IllegalArgumentException("Invalid ID");
        }
        repository.deleteById(id);
        ResponseEntity.noContent().build();
    }
}
