package com.projetolivraria.livraria.model;

import com.projetolivraria.livraria.model.user.User;
import jakarta.persistence.*;

@Entity
@Table(name = "cart")
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "book_id", nullable = false)
    private Book book;

    private int quantitySelected;

    public Cart(){
        
    }
    public Cart(User user, Book book, int quantitySelected) {
        this.user = user;
        this.book = book;
        this.quantitySelected = quantitySelected;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public int getQuantitySelected(){
        return quantitySelected;
    }

    public void setQuantitySelected(int quantitySelected){
        this.quantitySelected = quantitySelected;
    }
}
