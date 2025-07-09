package com.projetolivraria.livraria.controller;

import java.util.List;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.projetolivraria.livraria.model.Errand;
import com.projetolivraria.livraria.service.ErrandService;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("errand")
public class ErrandController {
    

    @Autowired
    private ErrandService service;
    //Post method to register a new errand
    @PostMapping("/new")
    public ResponseEntity<Errand> newErrand(@RequestBody @Valid Errand e) {
        return ResponseEntity.ok(service.save(e));
    }
    //Get method to get all the errands
    @GetMapping("/all")
    public List<Errand> allErrands(){
        return service.getAll();
    }
    //Get method to get an errand based on the id
    @GetMapping("/{code}")
    public Errand findErrandByCode(@PathVariable @Valid long code){
        return service.findById(code);
    }
    //Method to delete Errand
    @DeleteMapping("/{code}")
    public void deleteErrand(@PathVariable @Valid long code){
        service.deleteErrandById(code);
    }
}
