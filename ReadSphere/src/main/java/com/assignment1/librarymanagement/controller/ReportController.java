package com.assignment1.librarymanagement.controller;

import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.*;

import com.assignment1.librarymanagement.dto.LibraryReportDTO;
import com.assignment1.librarymanagement.model.Book;
import com.assignment1.librarymanagement.service.ReportService;

import lombok.extern.slf4j.Slf4j;

import org.springframework.http.ResponseEntity;

@Slf4j
@RestController
@RequestMapping("/api/books/report")
public class ReportController {

    private final ReportService reportService;

    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @GetMapping
    public ResponseEntity<LibraryReportDTO> getReport() {
        log.info("Generating library report");
        return ResponseEntity.ok(reportService.generateReport());
    }
    @GetMapping("/group/{type}")
    public ResponseEntity<Map<String, List<Book>>> groupBooks(@PathVariable String type){
        log.info("Generating {} grouping report", type);
        try{
            return ResponseEntity.ok(reportService.groupBooks(type));
        }catch(IllegalArgumentException e){
            return ResponseEntity.badRequest().build();
        }
    }
}