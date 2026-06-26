package com.assignment1.librarymanagement.service;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.assignment1.librarymanagement.model.Book;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.List;

import java.io.FileWriter;
import java.io.PrintWriter;

@Slf4j
@Service
public class CsvReaderService {
   @Value("${library.csv.path}")
    private String csvFilePath;

    @Value("${library.csv.header}")
    private String csvHeader;

    public List<Book> readBooks() {
        log.info("Reading books from CSV file");
        List<Book> books = new ArrayList<>();

        try {

            BufferedReader reader = new BufferedReader(new java.io.FileReader(csvFilePath));

            String line;
            reader.readLine();

            while ((line = reader.readLine()) != null) {

                String[] data = line.split(",");

                Book book = Book.builder()
                        .id(Integer.parseInt(data[0]))
                        .bookName(data[1])
                        .authorName(data[2])
                        .category(data[3])
                        .publisher(data[4])
                        .price(Double.parseDouble(data[5]))
                        .quantity(Integer.parseInt(data[6]))
                        .publishedYear(Integer.parseInt(data[7]))
                        .isbn((long) Double.parseDouble(data[8]))
                        .language(data[9])
                        .build();

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
    public List<Book> getAllBooks(int page, int size, String sortBy, String direction) {
        List<Book> sortedBooks = new ArrayList<>(books);

        switch (sortBy.toLowerCase()) {

            case "bookname" ->
                sortedBooks.sort(java.util.Comparator.comparing(Book::getBookName));

            case "authorname" ->
                sortedBooks.sort(java.util.Comparator.comparing(Book::getAuthorName));

            case "category" ->
                sortedBooks.sort(java.util.Comparator.comparing(Book::getCategory));

            case "publisher" ->
                sortedBooks.sort(java.util.Comparator.comparing(Book::getPublisher));

            case "price" ->
                sortedBooks.sort(java.util.Comparator.comparingDouble(Book::getPrice));

            case "quantity" ->
                sortedBooks.sort(java.util.Comparator.comparingInt(Book::getQuantity));

            case "publishedyear" ->
                sortedBooks.sort(java.util.Comparator.comparingInt(Book::getPublishedYear));

            case "language" ->
                sortedBooks.sort(java.util.Comparator.comparing(Book::getLanguage));

            default ->
                sortedBooks.sort(java.util.Comparator.comparingInt(Book::getId));
        }

        if (direction.equalsIgnoreCase("desc")) {
            java.util.Collections.reverse(sortedBooks);
        }

        return sortedBooks.stream()
                .skip((long) page * size)
                .limit(size)
                .toList();
    }
    public Book addBook(Book book){
        boolean isbnExists = books.stream()
            .anyMatch(existingBook -> existingBook.getIsbn().equals(book.getIsbn()));

        if (isbnExists) {
            throw new IllegalArgumentException("A book with ISBN " + book.getIsbn() + " already exists.");
        }
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
        boolean isbnExists = books.stream()
            .anyMatch(book ->
                    book.getId() != id &&
                    book.getIsbn().equals(updatedBook.getIsbn()));

            if(isbnExists){
                throw new IllegalArgumentException(
                        "Book with ISBN " + updatedBook.getIsbn() + " already exists.");
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

        PrintWriter writer =new PrintWriter(new FileWriter(csvFilePath));

        writer.println(csvHeader);

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
