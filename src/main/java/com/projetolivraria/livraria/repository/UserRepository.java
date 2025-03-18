package com.projetolivraria.livraria.repository;

import java.util.Optional;


import org.springframework.data.jpa.repository.JpaRepository;

import com.projetolivraria.livraria.model.user.User;



public interface UserRepository extends JpaRepository<User, Integer>{

    Optional<User> findByEmail(String email);

    Optional<User> findById(Integer id);

}
