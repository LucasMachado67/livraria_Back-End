package com.projetolivraria.livraria.service;

import com.projetolivraria.livraria.model.user.User;
import com.projetolivraria.livraria.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {

    @Autowired
    private UserRepository repository;

    public List<User> findAll(){
        try{
            return this.repository.findAll();
        }catch (RuntimeException e){
            throw new RuntimeException("Error while trying to find users" + e.getMessage());
        }
    }

    public User findById(UUID id){
        return this.repository.findById(id)
                .orElseThrow(() -> new RuntimeException("User with id: " + id + "not found"));
    }

    public void deleteById(UUID id){
        Optional<User> user = Optional.ofNullable(this.repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + id)));

        this.repository.deleteById(id);
    }

    public User editUser(@Valid User user){
        User userToEdit = this.repository.findById(user.getId())
                .orElseThrow(() -> new RuntimeException("Book with id: " + user.getId() + "not found"));
        //Encrypting password to database
        String encryptedPassword = new BCryptPasswordEncoder().encode(user.getPassword());

        userToEdit.setName(user.getName());
        userToEdit.setEmail(user.getEmail());
        userToEdit.setPassword(encryptedPassword);
        userToEdit.setPhone(user.getPhone());
        userToEdit.setGender(user.getGender());
        return this.repository.save(userToEdit);
    }
}
