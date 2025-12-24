package com.example.MegLogX.Dtos;

import lombok.Data;

import java.time.LocalDate;

@Data
public class PatientRequest {
    private String firstName;
    private String lastName;
    private String gender;
    private LocalDate dateOfBirth;
    private String relationToUser;
    private Long userId;
}
