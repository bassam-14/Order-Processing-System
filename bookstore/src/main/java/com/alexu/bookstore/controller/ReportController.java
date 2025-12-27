package com.alexu.bookstore.controller;

import com.alexu.bookstore.service.ReportService; // Import Service, not Repo
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/reports")
public class ReportController {

    @Autowired
    private ReportService reportService; // Inject Service

    @GetMapping("/sales/prev-month")
    public Double getSalesPrevMonth() {
        return reportService.getSalesPrevMonth();
    }

    @GetMapping("/sales/day")
    public Double getSalesByDay(@RequestParam String date) {
        return reportService.getSalesByDay(date);
    }

    @GetMapping("/top-customers")
    public List<Map<String, Object>> getTopCustomers() {
        return reportService.getTopCustomers();
    }

    @GetMapping("/top-books")
    public List<Map<String, Object>> getTopBooks() {
        return reportService.getTopBooks();
    }

    @GetMapping("/restock/{isbn}")
    public Integer getRestockCount(@PathVariable String isbn) {
        return reportService.getRestockCount(isbn);
    }
}