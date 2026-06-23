package com.assignment1.LibraryInfoDisplay.controller;

import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.*;

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
    @GetMapping("/group/{type}")
    public Map<String, List<Book>> groupBooks(@PathVariable String type){
        log.info("Generating {} grouping report", type);
        return reportService.groupBooks(type);
    }
}