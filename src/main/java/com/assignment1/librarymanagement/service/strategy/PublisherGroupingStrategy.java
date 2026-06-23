package com.assignment1.librarymanagement.service.strategy;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.assignment1.librarymanagement.model.Book;

@Component
public class PublisherGroupingStrategy implements GroupingStrategy {

    @Override
    public Map<String, List<Book>> group(List<Book> books) {
        return books.stream()
                .collect(Collectors.groupingBy(Book::getPublisher));
    }
}