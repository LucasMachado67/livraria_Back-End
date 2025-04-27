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
        private CategoryRepository categoryRepository;

        //Method to register a new book
        @Transactional
        public Book saveNewBook(Book book) throws IOException {
            //Getting the categories selected to save in a List
            List<Category> persistedCategories = new ArrayList<>();
            for(Category category : book.getCategories()){
                Category existingCategory = categoryRepository.findById(category.getId())
                        .orElseThrow(() -> new RuntimeException("Category not found with ID: " + category.getId()));
                persistedCategories.add(existingCategory);
            }
            //Setting the categories to the book
            book.setCategories(persistedCategories);
            return repository.save(book);
        }
        //Method to get a book based on the id
        public BookDetailsDTO findById(long id) {
            return repository.findBookDetailsById(id);
        }

        //Method to get all the books
        public List<Book> findAll() {
            return repository.findAll();
        }
        //Method to edit books (NEED's REFACTOR - maybe the error is in the front)
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
        //Method to delete books
        public void deleteBook(long id) {
            if (id < 0) {
                throw new IllegalArgumentException("Invalid ID");
            }
            repository.deleteById(id);
            ResponseEntity.noContent().build();
        }
        //Method to get the categories selected to assist the edit method (NEED's REFACTOR - maybe the error is in the front)
        public List<Category> getSelectCategoriesPerBook(long code){
            return categoryRepository.getCategoriesOfSelectedBook(code);
        }
        //Code for searching books by title (NEED's REFACTOR)
    //    public List<Book> searchBooksByTitle(String query) {
    //        return repository.searchByTitle(query);
    //    }
}
