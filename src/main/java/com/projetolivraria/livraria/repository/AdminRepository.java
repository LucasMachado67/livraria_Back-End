package com.projetolivraria.livraria.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.projetolivraria.livraria.model.Admin;

public interface AdminRepository extends JpaRepository<Admin, Long>{
    
    Admin findAdminByName(String name);

    Admin findAdminById(Long id);
}
