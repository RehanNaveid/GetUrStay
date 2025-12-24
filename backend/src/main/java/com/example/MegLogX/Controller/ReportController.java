package com.example.MegLogX.Controller;

import com.example.MegLogX.Dtos.ReportRequest;
import com.example.MegLogX.Dtos.ReportResponse;
import com.example.MegLogX.Service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reports")
public class ReportController {

    @Autowired
    private ReportService reportService;

    // ✅ Create a new report
    @PostMapping
    public ResponseEntity<ReportResponse> createReport(@RequestBody ReportRequest request) {
        ReportResponse response = reportService.createReport(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    // ✅ Get all reports for a patient by patientId
    @GetMapping("/patient/{patientId}")
    public ResponseEntity<List<ReportResponse>> getReportsByPatientId(@PathVariable Long patientId) {
        List<ReportResponse> reports = reportService.getReportsByPatientId(patientId);
        return ResponseEntity.ok(reports);
    }

    // ✅ Get a single report by reportId
    @GetMapping("/{id}")
    public ResponseEntity<ReportResponse> getReportById(@PathVariable Long id) {
        ReportResponse response = reportService.getReportById(id);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ReportResponse> updateReport(@PathVariable Long id, @RequestBody ReportRequest request) {
        return ResponseEntity.ok(reportService.updateReport(id, request));
    }

    // ✅ Delete a report by reportId
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReport(@PathVariable Long id) {
        reportService.deleteReport(id);
        return ResponseEntity.noContent().build();
    }
}

