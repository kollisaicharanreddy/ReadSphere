package com.assignment1.LibraryInfoDisplay.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.assignment1.LibraryInfoDisplay.model.Book;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class BookService {
    private final CsvReaderService csvReaderService;
    BookService(CsvReaderService csvReaderService){
        this.csvReaderService = csvReaderService;
    }
    public List<Book> getAllBooks(){
        return csvReaderService.readBooks();
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
    public Book addBook(Book book){
        return csvReaderService.addBook(book);
    }
    public Book updateBook(int id, Book book){
        return csvReaderService.updateBook(id, book);
    }

    public boolean deleteBook(int id){
        return csvReaderService.deleteBook(id);
    }
}
