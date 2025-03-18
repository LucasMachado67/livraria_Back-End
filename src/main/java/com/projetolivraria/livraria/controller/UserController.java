package com.projetolivraria.livraria.controller;


import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.*;
import com.projetolivraria.livraria.model.user.User;
import com.projetolivraria.livraria.repository.UserRepository;

@Configuration
@RestController
@CrossOrigin(origins = "https://livraria-front-end-admin.vercel.app")
@RequestMapping("user")
public class UserController{
    @Autowired
    private UserRepository userAction;

    @GetMapping("/all")
    public List<User> findAllUsers() {
        return userAction.findAll();
    }

    @GetMapping("/{id}")
    public Optional<User> findUserById(@PathVariable int id){
        return userAction.findById(id);
    }

    // @DeleteMapping("/users/{code}")
    // public void deleteByUserCode(@PathVariable Integer code){
    //     userAction.deleteById(code);
    // }

    // @PutMapping("/users/{code}")
    // public ResponseEntity<User> editUser(@PathVariable Integer code, @RequestBody User u) {
    //     Optional<User> optionalUser = userAction.findById(code);
    
    //     if(optionalUser.isPresent()){
    //         User user = optionalUser.get();
    //         user.setName(u.getName());
    //         user.setEmail(u.getEmail());
    //         user.setPassword(u.getPassword());
    //         return ResponseEntity.ok(user);
    //     }else{
    //         return ResponseEntity.notFound().build();
    //     }
    // }
    

}