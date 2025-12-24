package com.example.MegLogX.Entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Data
@Entity
public class ReportEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String reportType;
    private LocalDate reportDate;
    private String fileUrl;
    private String description;
    @ManyToOne
    @JoinColumn(name = "patient_id")
    private PatientEntity patient;
}
