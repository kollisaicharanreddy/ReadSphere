package com.assignment1.LibraryInfoDisplay.service.factory;

import org.springframework.stereotype.Component;

import com.assignment1.LibraryInfoDisplay.service.strategy.CategoryGroupingStrategy;
import com.assignment1.LibraryInfoDisplay.service.strategy.GroupingStrategy;
import com.assignment1.LibraryInfoDisplay.service.strategy.LanguageGroupingStrategy;
import com.assignment1.LibraryInfoDisplay.service.strategy.PublisherGroupingStrategy;

@Component
public class GroupingStrategyFactory {

    private final CategoryGroupingStrategy categoryStrategy;
    private final PublisherGroupingStrategy publisherStrategy;
    private final LanguageGroupingStrategy languageStrategy;

    public GroupingStrategyFactory(
            CategoryGroupingStrategy categoryStrategy,
            PublisherGroupingStrategy publisherStrategy,
            LanguageGroupingStrategy languageStrategy) {

        this.categoryStrategy = categoryStrategy;
        this.publisherStrategy = publisherStrategy;
        this.languageStrategy = languageStrategy;
    }

    public GroupingStrategy getStrategy(String type) {

        return switch (type.toLowerCase()) {
            case "category" -> categoryStrategy;
            case "publisher" -> publisherStrategy;
            case "language" -> languageStrategy;
            default -> throw new IllegalArgumentException("Invalid grouping type");
        };
    }
}