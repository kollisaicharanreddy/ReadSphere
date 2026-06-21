package com.assignment1.LibraryInfoDisplay.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.assignment1.LibraryInfoDisplay.model.Book;
import com.assignment1.LibraryInfoDisplay.service.BookService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/books")
public class BookController {
    private final BookService bookService;
    BookController(BookService bookService){
        this.bookService = bookService;
    }
    @GetMapping
    public List<Book> getAllBooksInfo(){
        log.info("GET /api/books called");
        return bookService.getAllBooks();
    }
    @GetMapping("/{id}")
    public Book getBookInfoById(@PathVariable int id){
        log.info("GET /api/books/{} called", id);
        return bookService.getBookById(id);
    }
    @GetMapping("/author/{authorName}")
    public List<Book> getBookInfoByAuthor(@PathVariable String authorName){
        return bookService.getBooksByAuthor(authorName);
    }
    @GetMapping("/category/{category}")
    public List<Book> getBookInfoByCategory(@PathVariable String category){
        return bookService.getBooksByCategory(category);
    }
    @PostMapping
    public Book addBook(@RequestBody Book book){
        log.info("POST /api/books called");
        return bookService.addBook(book);
    }

    @PutMapping("/{id}")
    public Book updateBook(@PathVariable int id, @RequestBody Book book){
        log.info("PUT /api/books/{} called", id);
        return bookService.updateBook(id, book);
    }

    @DeleteMapping("/{id}")
    public boolean deleteBook(@PathVariable int id){
        log.info("DELETE /api/books/{} called", id);
        return bookService.deleteBook(id);
    }
}
