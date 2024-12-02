package com.projetolivraria.livraria.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RestController;

import com.projetolivraria.livraria.model.Errand;
import com.projetolivraria.livraria.repository.ErrandRepository;
import com.projetolivraria.livraria.service.ErrandService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class ErrandController {
    

    @Autowired
    private ErrandService service;

    @Autowired
    private ErrandRepository repository;

    @PostMapping("/newErrand")
    public ResponseEntity<Errand> newErrand(@RequestBody Errand e) {
        return ResponseEntity.ok(service.addNewErrand(e));
    }

    @GetMapping("/allErrands")
    public Iterable<Errand> allErrands(){
        
        List<Errand> errands = service.allErrands();
        return errands;
    }

    @GetMapping("/allErrands/{code}")
    public Errand findErrandByCode(@PathVariable Long code){
        return repository.findErrandByCode(code);
    }

    @DeleteMapping("/allErrands/{code}")
    public void deleteErrand(@PathVariable Long code){
        repository.deleteById(code);
    }
}
