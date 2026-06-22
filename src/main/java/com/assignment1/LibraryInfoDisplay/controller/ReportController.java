package com.assignment1.LibraryInfoDisplay.controller;

import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.assignment1.LibraryInfoDisplay.dto.LibraryReportDTO;
import com.assignment1.LibraryInfoDisplay.model.Book;
import com.assignment1.LibraryInfoDisplay.service.ReportService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/books/report")
public class ReportController {

    private final ReportService reportService;

    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @GetMapping
    public LibraryReportDTO getReport() {
        log.info("Generating library report");
        return reportService.generateReport();
    }
    @GetMapping("/group/category")
    public Map<String, List<Book>> groupByCategory(){
        log.info("Generating category-wise grouping report");        
        return reportService.groupByCategory();
    }

    @GetMapping("/group/publisher")
    public Map<String, List<Book>> groupByPublisher(){
        log.info("Generating publisher-wise grouping report");
        return reportService.groupByPublisher();
    }

    @GetMapping("/group/language")
    public Map<String, List<Book>> groupByLanguage(){
        log.info("Generating language-wise grouping report");
        return reportService.groupByLanguage();
    }
}