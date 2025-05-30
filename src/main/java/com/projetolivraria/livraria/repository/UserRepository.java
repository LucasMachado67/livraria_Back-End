package com.projetolivraria.livraria.repository;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.projetolivraria.livraria.model.user.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepository extends JpaRepository<User, UUID >{

    UserDetails findByEmail(String email);

}
