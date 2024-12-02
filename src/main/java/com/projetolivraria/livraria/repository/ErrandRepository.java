package com.projetolivraria.livraria.repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.projetolivraria.livraria.model.Errand;

public interface ErrandRepository extends JpaRepository<Errand, Long>{

    Errand findErrandByCode(Long code);
    int countByCode(long code);
}
