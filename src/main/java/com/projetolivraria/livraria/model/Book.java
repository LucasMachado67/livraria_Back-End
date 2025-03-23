package com.projetolivraria.livraria.model;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Table(name = "books")
@Entity
public class Book {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long code;
    private String title;
    @ManyToOne
    @JoinColumn(name = "author_id", nullable = false)
    private Author author;
    private int year;
    private BigDecimal price;
    private int pages;
    private String language;
    private String bookCover;
    @Lob
    @Column(name="image",columnDefinition = "BLOB")
    @JoinColumn(name = "image")
    private byte[] image;
    private int quantity;
    @Column(columnDefinition = "TEXT")
    private String description;
    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    public Book(String title, Author author, int year, BigDecimal price, int pages, String language, String bookCover, byte[] image, int quantity, String description, Category category) {
        this.title = title;
        this.author = author;
        this.year = year;
        this.price = price;
        this.pages = pages;
        this.language = language;
        this.bookCover = bookCover;
        this.image = image;
        this.quantity = quantity;
        this.description = description;
        this.category = category;
    }

    public Book(){
    }

    public long getCode(){
        return code;
    }
    public void setCode(long code){
        this.code = code;
    }
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getBookCover() {
        return bookCover;
    }

    public void setBookCover(String bookCover) {
        this.bookCover = bookCover;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}

