package com.example.MegLogX.Dtos;

import lombok.Data;

import java.time.LocalDate;

@Data
public class PrescriptionResponse {
    private Long id;
    private String doctorName;
    private LocalDate issuedDate;
    private String medicines;
    private String notes;
    private String patientFullName;
}
