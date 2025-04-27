package com.projetolivraria.livraria.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.projetolivraria.livraria.model.Errand;
import com.projetolivraria.livraria.repository.ErrandRepository;

//STILL WORKING IN THIS CLASS, IT WILL BE USED FOR THE USER SEND
//COMMENTS/SUGGESTIONS TO THE ADMIN SYSTEM SO THEY CAN IMPROVE
@Service
public class ErrandService {
    
    @Autowired
    private ErrandRepository repository;
    //Method to register a new errand
    public Errand addNewErrand(Errand e){
        if (e.getEmail() == null || e.getEmail().isEmpty() ||
            e.getName() == null || e.getName().isEmpty() ||
            e.getPhone() == null || e.getPhone().isEmpty() ||
            e.getMessage() == null || e.getMessage().isEmpty()) {
                throw new IllegalArgumentException("Error: Empty values are not allowed.");
        }
        return repository.save(e);
    }
    //Method to get errand based on the id
    public Errand findErrandById(long id){
        if(id <= 0){
            throw new IllegalArgumentException("Invalid ID");
        }else{
            return repository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Errand with id: " + id + "not found"));
        }
    }
    //Method to get all the errands
    public List<Errand> allErrands(){
        return repository.findAll();
    }
    //Method to delete an Errand
    public void deleteErrandById(long id){
        Errand selectedErrand = this.findErrandById(id);
        repository.deleteById(selectedErrand.getCode());
    }
}
