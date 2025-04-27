package com.projetolivraria.livraria.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Table(name = "Errands")
@Entity
public class Errand {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long code;
    private String email;
    private String name;
    private String phone;
    @Column(columnDefinition = "TEXT")
    private String message;

    public Errand(String email, String name, String phone, String message) {
        this.email = email;
        this.name = name;
        this.phone = phone;
        this.message = message;
    }

    public Errand(){
        
    }
    public Long getCode() {
        return code;
    }

    public void setCode(Long code) {
        this.code = code;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
