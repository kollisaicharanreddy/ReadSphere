package com.assignment1.LibraryInfoDisplay.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.assignment1.LibraryInfoDisplay.model.Book;

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
        return csvReaderService.readBookById(id);
    }
    public List<Book> getBookByAuthor(String authorName){
        return csvReaderService.readBookByAuthor(authorName);
    }
    public List<Book> getBooksByCategory(String category){
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
