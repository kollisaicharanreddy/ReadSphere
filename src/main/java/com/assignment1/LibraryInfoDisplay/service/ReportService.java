package com.assignment1.LibraryInfoDisplay.service;


import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.assignment1.LibraryInfoDisplay.dto.LibraryReportDTO;
import com.assignment1.LibraryInfoDisplay.model.Book;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ReportService {

    private final CsvReaderService csvReaderService;

    public ReportService(CsvReaderService csvReaderService) {
        this.csvReaderService = csvReaderService;
    }

    public LibraryReportDTO generateReport() {
        log.info("Generating library report");
        List<Book> books = csvReaderService.readBooks();

        LibraryReportDTO report =
                new LibraryReportDTO();

        report.setTotalBooks(books.size());

        report.setTotalQuantity(books.stream().mapToLong(Book::getQuantity).sum());

        report.setAverageBookPrice(books.stream().mapToDouble(Book::getPrice).average().orElse(0));

        report.setAverageQuantityPerBook(books.stream().mapToInt(Book::getQuantity).average().orElse(0));

        report.setTotalInventoryValue(books.stream().mapToDouble(book -> book.getPrice() * book.getQuantity()).sum());

        report.setTotalAuthors(books.stream().map(Book::getAuthorName).distinct().count());

        report.setTotalPublishers(books.stream().map(Book::getPublisher).distinct().count());

        report.setTotalLanguages(books.stream().map(Book::getLanguage).distinct().count());

        report.setMaxPriceBook(books.stream().max(Comparator.comparingDouble(Book::getPrice)).orElse(null));

        report.setMinPriceBook(books.stream().min(Comparator.comparingDouble(Book::getPrice)).orElse(null));

        report.setMaxQuantityBook(books.stream().max(Comparator.comparingInt(Book::getQuantity)).orElse(null));

        report.setMinQuantityBook(books.stream().min(Comparator.comparingInt(Book::getQuantity)).orElse(null));

        report.setLatestPublishedBook(books.stream().max(Comparator.comparingInt(Book::getPublishedYear)).orElse(null));

        report.setOldestPublishedBook(books.stream().min(Comparator.comparingInt(Book::getPublishedYear)).orElse(null));
        log.info("Library report generated successfully");
    return report;
    }
    public Map<String, List<Book>> groupByCategory(){
        return csvReaderService.getAllBooks().stream().collect(Collectors.groupingBy(Book::getCategory));
    }
    public Map<String, List<Book>> groupByPublisher(){
        return csvReaderService.getAllBooks().stream().collect(Collectors.groupingBy(Book::getPublisher));
    }
    public Map<String, List<Book>> groupByLanguage(){
        return csvReaderService.getAllBooks().stream().collect(Collectors.groupingBy(Book::getLanguage));
    }
}
