package com.assignment1.LibraryInfoDisplay.service.strategy;

import java.util.List;
import java.util.Map;

import com.assignment1.LibraryInfoDisplay.model.Book;

public interface GroupingStrategy {

    Map<String, List<Book>> group(List<Book> books);
}