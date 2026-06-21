package com.assignment1.LibraryInfoDisplay.dto;
import com.assignment1.LibraryInfoDisplay.model.Book;

import lombok.Data;

@Data
public class LibraryReportDTO {
    

    private int totalBooks;
    private long totalQuantity;
    private double averageBookPrice;
    private double averageQuantityPerBook;
    private double totalInventoryValue;
    private long totalAuthors;
    private long totalPublishers;
    private long totalLanguages;
    private Book maxPriceBook;
    private Book minPriceBook;
    private Book maxQuantityBook;
    private Book minQuantityBook;
    private Book latestPublishedBook;
    private Book oldestPublishedBook;
}

