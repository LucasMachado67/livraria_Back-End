package com.projetolivraria.livraria.repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.projetolivraria.livraria.model.Errand;
//STILL WORKING IN THIS CLASS, IT WILL BE USED FOR THE USER SEND
//COMMENTS/SUGGESTIONS TO THE ADMIN SYSTEM SO THEY CAN IMPROVE
public interface ErrandRepository extends JpaRepository<Errand, Long>{

}
