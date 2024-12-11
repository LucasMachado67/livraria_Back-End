package com.projetolivraria.livraria.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Table(name = "Errands")
@Entity
@Getter
@Setter
public class Errand {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long code;
    private String email;
    private String name;
    private String phone;
    @Column(columnDefinition = "TEXT")
    private String message;
}
