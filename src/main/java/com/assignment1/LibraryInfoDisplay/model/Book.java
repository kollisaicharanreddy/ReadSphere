package com.assignment1.LibraryInfoDisplay.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Book {
    private int id;
    private String bookName;
    private String authorName;
    private String category;
    private String publisher;
    private double price;
    private int quantity;
    private int publishedYear;
    private Long isbn;
    private String language;

}
