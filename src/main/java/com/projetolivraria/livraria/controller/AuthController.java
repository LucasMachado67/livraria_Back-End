package com.projetolivraria.livraria.controller;

import com.projetolivraria.livraria.model.user.AuthenticationDTO;
import com.projetolivraria.livraria.model.user.LoginRequestDTO;
import com.projetolivraria.livraria.model.user.RegisterRequestDTO;
import com.projetolivraria.livraria.model.user.User;
import com.projetolivraria.livraria.service.UserAuthenticationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("auth")
@CrossOrigin(origins = "http://localhost:4200")
public class AuthController {

    @Autowired
    UserAuthenticationService authenticationService;

    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody @Valid AuthenticationDTO authenticationDTO){
        authenticationService.loadUserByUsername(authenticationDTO.email());
        return authenticationService.login(authenticationDTO);
    }

    @PostMapping("/register")
    public ResponseEntity<Object> login(@RequestBody @Valid RegisterRequestDTO registerDTO){
        return authenticationService.register(registerDTO);
    }

    @GetMapping("/me")
    public ResponseEntity<User> getCurrentUser() {
        User user = authenticationService.getAuthenticatedUser();
        return ResponseEntity.ok(user); 
    }

}
