package com.example.MegLogX.Dtos;

import lombok.Data;

import java.time.LocalDate;

@Data
public class PrescriptionRequest {
    private Long patientId;
    private String doctorName;
    private LocalDate issuedDate;
    private String medicines;
    private String notes;
}
