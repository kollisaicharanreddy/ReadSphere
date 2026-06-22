package com.assignment1.LibraryInfoDisplay.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.assignment1.LibraryInfoDisplay.model.Book;

@ExtendWith(MockitoExtension.class)
class BookServiceUnitTest {

    @Mock
    private CsvReaderService csvReaderService;

    @InjectMocks
    private BookService bookService;

    @Test
    void shouldReturnBookById() {

        Book book = Book.builder()
                .id(1)
                .bookName("Clean Code")
                .authorName("Robert C. Martin")
                .build();

        when(csvReaderService.readBookById(1)).thenReturn(book);

        Book result = bookService.getBookById(1);

        assertNotNull(result);
        assertEquals(1, result.getId());
        assertEquals("Clean Code", result.getBookName());
    }

    @Test
    void shouldReturnBooksByCategory() {

        List<Book> books = List.of(
                Book.builder()
                        .id(1)
                        .bookName("Clean Code")
                        .category("Programming")
                        .build(),
                Book.builder()
                        .id(2)
                        .bookName("Effective Java")
                        .category("Programming")
                        .build()
        );

        when(csvReaderService.readBooksByCategory("Programming"))
                .thenReturn(books);

        List<Book> result = bookService.getBooksByCategory("Programming");

        assertEquals(2, result.size());
        assertEquals("Programming", result.get(0).getCategory());
    }
        @Test
        void shouldReturnNullWhenBookNotFound() {

                when(csvReaderService.readBookById(999))
                        .thenReturn(null);
                Book result = bookService.getBookById(999);

                assertNull(result);
        }
}