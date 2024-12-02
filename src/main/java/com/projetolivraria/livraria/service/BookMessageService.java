// package com.projetolivraria.livraria.service;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.http.HttpStatus;
// import org.springframework.http.ResponseEntity;
// import org.springframework.stereotype.Service;

// import com.projetolivraria.livraria.model.Book;
// import com.projetolivraria.livraria.model.Message;
// import com.projetolivraria.livraria.repository.BookRepository;

// @Service
// public class BookMessageService {

//     @Autowired
//     private Message message;

//     @Autowired
//     private BookRepository bookAction;

//     //Method to save Books
//     public ResponseEntity<?> saveNewBook(Book b){
//         if(b.getTitle().equals("")){
//          message.setMessage("The title need's to be fulfilled");
//           return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
//         }else if(b.getAuthor().equals("")){
//             message.setMessage("The Author need's to be fulfilled");
//            return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
//         }else{
//            return new ResponseEntity<>(bookAction.save(b), HttpStatus.CREATED);
//         }

//     }

//     //Method to select Books
//     public ResponseEntity<?> findAllBooks(){
//         return new ResponseEntity<>(bookAction.findAll(), HttpStatus.OK);
//     }

//     //Method to select Book by code
//     public ResponseEntity<?> findBookByCode(int code){
//         if(bookAction.countByCode(code) == 0){
//             message.setMessage("No data found");
//             return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
//         }else{
//             return new ResponseEntity<>(bookAction.findByCode(code), HttpStatus.OK);
//         }
//     }

    
// }
