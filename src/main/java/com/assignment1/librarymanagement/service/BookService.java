package com.assignment1.librarymanagement.service;

import java.util.List;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.assignment1.librarymanagement.model.Book;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class BookService {
    private final CsvReaderService csvReaderService;
    BookService(CsvReaderService csvReaderService){
        this.csvReaderService = csvReaderService;
    }
    @Cacheable(value = "books", key= "#page + '-' + #size + '-' + #sortBy + '-' + #direction")
    public List<Book> getAllBooks(int page, int size, String sortBy, String direction){
        log.info("Fetching books with pagination and sorting");
        // System.out.println("BOOK SERVICE EXECUTED");
        return csvReaderService.getAllBooks(page, size, sortBy, direction);
    }
    public Book getBookById(int id){
        log.info("Fetching book with id {}", id);
        return csvReaderService.readBookById(id);
    }
    public List<Book> getBooksByAuthor(String authorName){
        log.info("Fetching books by author {}", authorName);
        return csvReaderService.readBookByAuthor(authorName);
    }
    public List<Book> getBooksByCategory(String category){
        log.info("Fetching books for category {}", category);
        return csvReaderService.readBooksByCategory(category);
    }
    @CacheEvict(value="books", allEntries= true)
    public Book addBook(Book book){
        return csvReaderService.addBook(book);
    }
    @CacheEvict(value="books", allEntries= true)
    public Book updateBook(int id, Book book){
        return csvReaderService.updateBook(id, book);
    }
    @CacheEvict(value="books", allEntries= true)
    public boolean deleteBook(int id){
        return csvReaderService.deleteBook(id);
    }
}
