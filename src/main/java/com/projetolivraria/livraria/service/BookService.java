package com.projetolivraria.livraria.service;

import com.projetolivraria.livraria.interfaces.BookDetailsDTO;
import com.projetolivraria.livraria.model.Category;
import com.projetolivraria.livraria.repository.AuthorRepository;
import com.projetolivraria.livraria.repository.CategoryRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.projetolivraria.livraria.model.Book;
import com.projetolivraria.livraria.repository.BookRepository;
import java.util.ArrayList;
import java.util.List;
import java.io.IOException;


@Service
public class BookService {
    

    @Autowired
    private BookRepository repository;

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Transactional
    public Book saveNewBook(Book book) throws IOException {

        List<Category> persistedCategories = new ArrayList<>();

        for(Category category : book.getCategories()){
            Category existingCategory = categoryRepository.findById(category.getId())
                    .orElseThrow(() -> new RuntimeException("Category not found with ID: " + category.getId()));
            persistedCategories.add(existingCategory);
        }

        book.setCategories(persistedCategories);
        return repository.save(book);
    }

    public BookDetailsDTO findById(long id) {
        return repository.findBookDetailsById(id);
    }
    

    public List<Book> findAll() {
        return repository.findAll();
    }

    public BookDetailsDTO editBook(Book book){
        Book bookToEdit = repository.findById(book.getCode())
                .orElseThrow(() -> new RuntimeException("Book with id: " + book.getCode() + "not found"));

        bookToEdit.setTitle(book.getTitle());
        bookToEdit.setAuthor(book.getAuthor());
        bookToEdit.setYear(book.getYear());
        bookToEdit.setPrice(book.getPrice());
        bookToEdit.setPages(book.getPages());
        bookToEdit.setLanguage(book.getLanguage());
        bookToEdit.setBookCover(book.getBookCover());
        bookToEdit.setImage(book.getImage());
        bookToEdit.setQuantity(book.getQuantity());

        List<Category> categoryOfSelectedBooks = getSelectCategoriesPerBook(book.getCode());
        bookToEdit.setCategories(categoryOfSelectedBooks);
        repository.save(bookToEdit);
        return repository.findBookDetailsById(book.getCode());
    }

    public void deleteBook(long id) {
        if (id < 0) {
            throw new IllegalArgumentException("Invalid ID");
        }
        repository.deleteById(id);
        ResponseEntity.noContent().build();
    }

    public List<Category> getSelectCategoriesPerBook(long code){
        return categoryRepository.getCategoriesOfSelectedBook(code);
    }
    //Code for searching books by title
//    public List<Book> searchBooksByTitle(String query) {
//        return repository.searchByTitle(query);
//    }
}
