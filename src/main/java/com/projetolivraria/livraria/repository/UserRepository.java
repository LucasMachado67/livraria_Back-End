package com.projetolivraria.livraria.repository;
import java.util.UUID;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

import com.projetolivraria.livraria.model.user.User;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, UUID >{

    User findByEmail(String email);

    @Transactional
    @Modifying
    @Query(value = """
               UPDATE users
               SET password = :password
               WHERE email = :email
            """, nativeQuery = true)
    void changePassword (@Param("password") String password,@Param("email") String email);

}
