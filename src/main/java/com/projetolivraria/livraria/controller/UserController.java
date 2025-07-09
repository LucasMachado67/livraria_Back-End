package com.projetolivraria.livraria.controller;



import com.projetolivraria.livraria.model.user.User;
import com.projetolivraria.livraria.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("user")
public class UserController{

    @Autowired
    private UserService service;

    @GetMapping("")
    public User findUserById(@RequestParam UUID userId){
        return service.findById(userId);
    }
}