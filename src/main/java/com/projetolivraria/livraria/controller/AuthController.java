package com.projetolivraria.livraria.controller;

import com.projetolivraria.livraria.model.user.AuthenticationDTO;
import com.projetolivraria.livraria.model.user.ChangePasswordDTO;
import com.projetolivraria.livraria.model.user.RegisterRequestDTO;
import com.projetolivraria.livraria.model.user.User;
import com.projetolivraria.livraria.service.UserAuthenticationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

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

    @PostMapping("/signup")
    public ResponseEntity<Object> register(@RequestBody RegisterRequestDTO registerDTO){
        try{
            return ResponseEntity.ok(authenticationService.register(registerDTO));
        }catch (RuntimeException e){
            throw new RuntimeException(e.getMessage());
        }catch (Exception e){
            throw  new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Internal Server Error", e);
        }
    }

    @PutMapping("/password")
    public void changePassword(@RequestBody ChangePasswordDTO dto){
        try{
            this.authenticationService.changePassword(dto.getPassword(), dto.getEmail());
        } catch (RuntimeException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @GetMapping("/me")
    public ResponseEntity<User> getCurrentUser() {
        User user = authenticationService.getAuthenticatedUser();
        return ResponseEntity.ok(user); 
    }

}
