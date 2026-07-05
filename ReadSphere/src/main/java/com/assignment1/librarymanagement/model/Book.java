package com.assignment1.librarymanagement.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Book {
    private int id;
    @NotBlank(message = "Book name cannot be empty")
    private String bookName;

    @NotBlank(message = "Author name cannot be empty")
    private String authorName;
    
    @NotBlank(message = "Category cannot be empty")
    private String category;

    @NotBlank(message = "Publisher cannot be empty")
    private String publisher;

    @Positive(message = "Price must be greater than zero")
    private double price;

    @PositiveOrZero(message = "Quantity cannot be negative")
    private int quantity;

    @Min(value = 1900, message = "Invalid publication year")
    @Max(value = 2100, message = "Invalid publication year")
    private int publishedYear;

    @NotNull(message = "ISBN cannot be null")
    private Long isbn;
    
    @NotBlank(message = "Language cannot be empty")
    private String language;

}
