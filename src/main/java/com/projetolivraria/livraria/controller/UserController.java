package com.projetolivraria.livraria.controller;



import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("user")
public class UserController{
//    @Autowired
//    private UserRepository userAction;
//
//    @GetMapping("/all")
//    public List<User> findAllUsers() {
//        return userAction.findAll();
//    }
//
//    @GetMapping("/{id}")
//    public Optional<User> findUserById(@PathVariable int id){
//        return userAction.findById(id);
//    }

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