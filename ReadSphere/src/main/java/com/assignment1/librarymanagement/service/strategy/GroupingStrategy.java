package com.assignment1.librarymanagement.service.strategy;

import java.util.List;
import java.util.Map;

import com.assignment1.librarymanagement.model.Book;

public interface GroupingStrategy {

    Map<String, List<Book>> group(List<Book> books);
}