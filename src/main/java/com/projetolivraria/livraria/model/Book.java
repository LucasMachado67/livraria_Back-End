package com.projetolivraria.livraria.model;

import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import java.util.List;

@Table(name = "books")
@Entity
@Getter
@Setter
public class Book {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long code;
    private String title;
    private String author;
    private int year;
    private BigDecimal price;
    private int pages;
    private String language;
    private String bookCover;
    @Lob
    private byte[] image;
    private int quantity;
    @Column(columnDefinition = "TEXT")
    private String description;
    @ElementCollection
    private List<String> categories;
}