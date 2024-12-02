package com.projetolivraria.livraria.repository;

import java.util.Optional;


import org.springframework.data.jpa.repository.JpaRepository;

import com.projetolivraria.livraria.model.user.User;



public interface UserRepository extends JpaRepository<User, String>{

    Optional<User> findByEmail(String email);

    Optional<User> findById(String id);

}
