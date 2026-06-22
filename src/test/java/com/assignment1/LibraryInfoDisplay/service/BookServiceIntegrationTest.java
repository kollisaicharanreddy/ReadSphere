package com.assignment1.LibraryInfoDisplay.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.assignment1.LibraryInfoDisplay.model.Book;

@SpringBootTest
class BookServiceIntegrationTest {

    @Autowired
    private BookService bookService;

    @Test
    void shouldReturnBookById() {

        Book book = bookService.getBookById(1);

        assertNotNull(book);
        assertEquals(1, book.getId());
    }

    @Test
    void shouldReturnBooksByCategory() {

        List<Book> books = bookService.getBooksByCategory("Programming");

        assertFalse(books.isEmpty());
    }
}