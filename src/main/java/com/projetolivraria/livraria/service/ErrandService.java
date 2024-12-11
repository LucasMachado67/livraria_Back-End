package com.projetolivraria.livraria.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.projetolivraria.livraria.model.Errand;
import com.projetolivraria.livraria.repository.ErrandRepository;

@Service
public class ErrandService {
    
    @Autowired
    private ErrandRepository repository;

    public Errand addNewErrand(Errand e){
        if (e.getEmail() == null || e.getEmail().isEmpty() ||
            e.getName() == null || e.getName().isEmpty() ||
            e.getPhone() == null || e.getPhone().isEmpty() ||
            e.getMessage() == null || e.getMessage().isEmpty()) {
                throw new IllegalArgumentException("Error: Empty values are not allowed.");
        }
        return repository.save(e);
    }

    public List<Errand> allErrands(){
        return repository.findAll();
    }
}
