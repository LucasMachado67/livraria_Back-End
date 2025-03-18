package com.projetolivraria.livraria.controller;

import java.util.Optional;


import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.projetolivraria.livraria.model.user.LoginRequestDTO;
import com.projetolivraria.livraria.model.user.RegisterRequestDTO;
import com.projetolivraria.livraria.model.user.ResponseDTO;
import com.projetolivraria.livraria.model.user.User;
import com.projetolivraria.livraria.repository.UserRepository;
import com.projetolivraria.livraria.security.TokenService;


import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "http://localhost:4200")
public class AuthController {
    private UserRepository repository;
    private PasswordEncoder passwordEncoder;
    private TokenService tokenService;

    @PostMapping("/login")    
    public ResponseEntity<ResponseDTO> login(@RequestBody LoginRequestDTO body){
        User user = this.repository.findByEmail(body.email()).orElseThrow(() -> new RuntimeException("User not Found"));
        if(passwordEncoder.matches(body.password(), user.getPassword())){
            String token = this.tokenService.generateToken(user);
            return ResponseEntity.ok(new ResponseDTO(user.getName(), token));
        }
        return ResponseEntity.badRequest().build();
    }

    @PostMapping("/register")    
    public ResponseEntity<ResponseDTO> register(@RequestBody RegisterRequestDTO body){
        Optional<User> user = this.repository.findByEmail(body.email());

        if(user.isEmpty()){
            User newUser = new User();
            newUser.setPassword(passwordEncoder.encode(body.password()));
            newUser.setEmail(body.email());
            newUser.setName(body.name());
            this.repository.save(newUser);

            String token = this.tokenService.generateToken(newUser);
            return ResponseEntity.ok(new ResponseDTO(newUser.getName(), token));
        }
        
        return ResponseEntity.badRequest().build();
    }
}
