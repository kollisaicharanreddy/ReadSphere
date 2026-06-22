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
        log.info("Fetching all books");
        return bookService.getAllBooks();
    }
    @GetMapping("/{id}")
    public Book getBookInfoById(@PathVariable int id){
        log.info("Fetching book with id {}", id);
        return bookService.getBookById(id);
    }
    @GetMapping("/author/{authorName}")
    public List<Book> getBookInfoByAuthor(@PathVariable String authorName){
        log.info("Fetching books written by {}", authorName);
        return bookService.getBooksByAuthor(authorName);
    }
    @GetMapping("/category/{category}")
    public List<Book> getBookInfoByCategory(@PathVariable String category){
        log.info("Fetching books from category {}", category);
        return bookService.getBooksByCategory(category);
    }
    @PostMapping
    public Book addBook(@RequestBody Book book){
        log.info("Adding new book: {}", book.getBookName());
        return bookService.addBook(book);
    }

    @PutMapping("/{id}")
    public Book updateBook(@PathVariable int id, @RequestBody Book book){
        log.info("Updating book with id {}", id);
        return bookService.updateBook(id, book);
    }

    @DeleteMapping("/{id}")
    public boolean deleteBook(@PathVariable int id){
        log.info("Deleting book with id {}", id);
        return bookService.deleteBook(id);
    }
}
