package com.assignment1.librarymanagement.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.assignment1.librarymanagement.model.Book;
import com.assignment1.librarymanagement.service.BookService;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import jakarta.validation.Valid;

@Slf4j
@RestController
@RequestMapping("/api/books")
public class BookController {
    private final BookService bookService;
    public BookController(BookService bookService){
        this.bookService = bookService;
    }

    @Value("${library.admin.role}")
    private String adminRole;

    private boolean isAdmin(String role){
        return role != null && role.equalsIgnoreCase(adminRole);
    }

    @GetMapping
    public ResponseEntity<List<Book>> getAllBooksInfo(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String direction) {

        log.info("Fetching books with pagination and sorting");

        List<Book> books = bookService.getAllBooks(page, size, sortBy, direction);

        if (books.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(books);
    }
    @GetMapping("/{id}")
    public ResponseEntity<Book> getBookInfoById(@PathVariable int id){
        log.info("Fetching book with id {}", id);
        Book book = bookService.getBookById(id);
        if(book==null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(book);
    }
    @GetMapping("/author/{authorName}")
    public ResponseEntity<List<Book>> getBookInfoByAuthor(@PathVariable String authorName){
        log.info("Fetching books written by {}", authorName);
        List<Book> books = bookService.getBooksByAuthor(authorName);
        if(books.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(books);
    }
    @GetMapping("/category/{category}")
    public ResponseEntity<List<Book>> getBookInfoByCategory(@PathVariable String category){
        log.info("Fetching books from category {}", category);
        List<Book> books = bookService.getBooksByCategory(category);

        if(books.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(books);
    }
    @PostMapping
    public ResponseEntity<Book> addBook(@RequestHeader(value = "Role", required = false) String role, @Valid @RequestBody Book book){
        
        if(!isAdmin(role)){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        log.info("Adding new book: {}", book.getBookName());
        Book savedBook = bookService.addBook(book);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedBook);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Book> updateBook(@PathVariable int id, @RequestHeader(value="Role", required = false) String role, @Valid @RequestBody Book book){
        if(!isAdmin(role)){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        log.info("Updating book with id {}", id);
        Book updatedBook = bookService.updateBook(id, book);
        if(updatedBook == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updatedBook);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable int id, @RequestHeader(value = "Role", required = false) String role){
        if(!isAdmin(role)){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        log.info("Deleting book with id {}", id);
        boolean deleted = bookService.deleteBook(id);
        if(!deleted){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }
}
