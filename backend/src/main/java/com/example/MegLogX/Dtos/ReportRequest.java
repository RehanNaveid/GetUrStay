package com.example.MegLogX.Dtos;

import lombok.Data;

import java.time.LocalDate;

@Data
public class ReportRequest {
    private Long patientId;
    private String reportType;
    private LocalDate reportDate;
    private String description;
    private String reportUrl;
}
