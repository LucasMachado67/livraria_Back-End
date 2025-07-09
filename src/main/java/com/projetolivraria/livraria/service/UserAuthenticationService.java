package com.projetolivraria.livraria.service;

import com.projetolivraria.livraria.model.enums.UserRole;
import com.projetolivraria.livraria.model.user.AuthenticationDTO;
import com.projetolivraria.livraria.model.user.LoginRequestDTO;
import com.projetolivraria.livraria.model.user.RegisterRequestDTO;
import com.projetolivraria.livraria.model.user.User;
import com.projetolivraria.livraria.repository.UserRepository;
import com.projetolivraria.livraria.security.TokenService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.context.ApplicationContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import static com.projetolivraria.livraria.model.enums.UserRole.USER;

@Service
public class UserAuthenticationService implements UserDetailsService {
    
    @Autowired
    private ApplicationContext context;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TokenService tokenService;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException{
        return  userRepository.findByEmail(email);
    }

    public ResponseEntity<Object> login(@RequestBody @Valid AuthenticationDTO data){
        AuthenticationManager authenticationManager = context.getBean(AuthenticationManager.class);

        var usernamePassword = new UsernamePasswordAuthenticationToken(data.email(), data.password());
        var auth = authenticationManager.authenticate(usernamePassword);
        var user = (User) auth.getPrincipal();
        var token = tokenService.generateToken(user);

        return ResponseEntity.ok(
                new LoginResponse(
                        token,
                        user.getEmail(),
                        user.getName(),
                        user.getPhone(),
                        user.getGender()
                )
        );

    }

    public ResponseEntity<Object> register(@RequestBody RegisterRequestDTO registerDto){
        if (this.userRepository.findByEmail(registerDto.email()) != null ) return ResponseEntity.badRequest().build();
        String encryptedPassword = new BCryptPasswordEncoder().encode(registerDto.password());

        User newUser = new User();
        newUser.setName(registerDto.name());
        newUser.setEmail(registerDto.email());
        newUser.setPassword(encryptedPassword);
        newUser.setPhone(registerDto.phone());
        newUser.setGender(registerDto.gender());
        newUser.setRole(UserRole.USER);
        this.userRepository.save(newUser);
        return ResponseEntity.ok().build();
    }


    public User getAuthenticatedUser(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth == null || !auth.isAuthenticated()) {
            throw new RuntimeException("User not authenticated");
        }

        return (User) auth.getPrincipal();

    @Transactional
    public void changePassword(String password, String email){

        //Searching for the given email in the database
        System.out.println("Email: " + email);
        User validUser = userRepository.findByEmail(email);

        //Validating password
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        if (encoder.matches(password, validUser.getPassword())) {
            throw new RuntimeException("ERROR: Same password");
        }

        if (password.length() < 5) {
            throw new RuntimeException("ERROR: Invalid Password");
        }

        userRepository.changePassword(encoder.encode(password), validUser.getEmail());
    }
}
