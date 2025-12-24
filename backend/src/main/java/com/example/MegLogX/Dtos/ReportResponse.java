package com.example.MegLogX.Dtos;

import lombok.Data;

import java.time.LocalDate;

@Data
public class ReportResponse {
    private Long id;
    private String reportType;
    private LocalDate reportDate;
    private String description;
    private String reportUrl;
    private String patientFullName;
}
