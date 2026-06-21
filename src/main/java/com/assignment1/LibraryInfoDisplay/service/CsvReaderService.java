package com.assignment1.LibraryInfoDisplay.service;

import com.assignment1.LibraryInfoDisplay.model.Book;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.List;

import java.io.FileWriter;
import java.io.PrintWriter;

@Slf4j
@Service
public class CsvReaderService {
    private static final String CSV_PATH = "data/books_catalog.csv";
    public List<Book> readBooks() {
        log.info("Reading books from CSV file");
        List<Book> books = new ArrayList<>();

        try {

            BufferedReader reader = new BufferedReader(new java.io.FileReader(CSV_PATH));

            String line;
            reader.readLine();

            while ((line = reader.readLine()) != null) {

                String[] data = line.split(",");

                Book book = new Book();

                book.setId(Integer.parseInt(data[0]));
                book.setBookName(data[1]);
                book.setAuthorName(data[2]);
                book.setCategory(data[3]);
                book.setPublisher(data[4]);
                book.setPrice(Double.parseDouble(data[5]));
                book.setQuantity(Integer.parseInt(data[6]));
                book.setPublishedYear(Integer.parseInt(data[7]));
                book.setIsbn((long) Double.parseDouble(data[8]));
                book.setLanguage(data[9]);

                books.add(book);
            }
            reader.close();
            log.info("Successfully loaded {} books", books.size());
        } catch (Exception e) {
            log.error("Error reading CSV file", e);
            throw new RuntimeException("Error reading CSV file", e);
        }

        return books;
    }

    private List<Book> books;
    @PostConstruct
    public void init() {
     books = readBooks();
     log.info("Loaded {} books from CSV file", books.size());
    }   
    public Book readBookById(int id){
        return books.stream().filter(book->book.getId()==id).findFirst().orElse(null);
    }
    public List<Book> readBookByAuthor(String authorName){
        return books.stream().filter(book->book.getAuthorName().equalsIgnoreCase(authorName)).toList();
    }
    public List<Book> readBooksByCategory(String category){
        return books.stream().filter(book->book.getCategory().equalsIgnoreCase(category)).toList();
    }
    public List<Book> getAllBooks(){
        return books;
    }
    public Book addBook(Book book){
        int nextId = books.stream().mapToInt(Book::getId).max().orElse(0) + 1;
        book.setId(nextId);
        books.add(book);
        saveBooksToCsv();
        log.info("Added new book with id {} and title {}", book.getId(), book.getBookName());
        return book;
    }
    public Book updateBook(int id, Book updatedBook){
        log.info("Updating book with id {}", id);
        Book existingBook = readBookById(id);
        if(existingBook == null){
            log.warn("Book with id {} not found", id);
            return null;
        }
        existingBook.setBookName(updatedBook.getBookName());
        existingBook.setAuthorName(updatedBook.getAuthorName());
        existingBook.setCategory(updatedBook.getCategory());
        existingBook.setPublisher(updatedBook.getPublisher());
        existingBook.setPrice(updatedBook.getPrice());
        existingBook.setQuantity(updatedBook.getQuantity());
        existingBook.setPublishedYear(updatedBook.getPublishedYear());
        existingBook.setIsbn(updatedBook.getIsbn());
        existingBook.setLanguage(updatedBook.getLanguage());
        saveBooksToCsv();
        log.info("Book with id {} updated successfully", id);
        return existingBook;
    }
    public boolean deleteBook(int id){
        boolean deleted = books.removeIf(book -> book.getId() == id);
        if(deleted){
            log.info("Book with id {} deleted successfully", id);
        }
        else{
            log.warn("Book with id {} not found", id);
        }
        return deleted;    
    }
    private void saveBooksToCsv() {
        log.info("Saving books to CSV file");
    try {

        PrintWriter writer =new PrintWriter(new FileWriter(CSV_PATH));

        writer.println("id,bookName,authorName,category,publisher,price,quantity,publishedYear,isbn,language");

        for(Book book : books) {

            writer.println(
                    book.getId() + "," +
                    book.getBookName() + "," +
                    book.getAuthorName() + "," +
                    book.getCategory() + "," +
                    book.getPublisher() + "," +
                    book.getPrice() + "," +
                    book.getQuantity() + "," +
                    book.getPublishedYear() + "," +
                    book.getIsbn() + "," +
                    book.getLanguage()
            );
        }
        log.info("Successfully saved {} books to CSV", books.size());
        writer.close();

    }
    catch(Exception e) {
        log.error("Error saving CSV file", e);
        throw new RuntimeException("Error saving CSV file", e);
    }
    
}
}
