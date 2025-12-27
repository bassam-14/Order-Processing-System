package com.alexu.bookstore.service;

import com.alexu.bookstore.repository.ReportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;

@Service
public class ReportService {

    @Autowired
    private ReportRepository reportRepo;

    public Double getSalesPrevMonth() {
        return reportRepo.getTotalSalesPrevMonth();
    }

    public Double getSalesByDay(String date) {
        return reportRepo.getTotalSalesByDay(date);
    }

    public List<Map<String, Object>> getTopCustomers() {
        return reportRepo.getTop5Customers();
    }

    public List<Map<String, Object>> getTopBooks() {
        return reportRepo.getTop10Books();
    }

    public Integer getRestockCount(String isbn) {
        return reportRepo.getBookRestockCount(isbn);
    }
}