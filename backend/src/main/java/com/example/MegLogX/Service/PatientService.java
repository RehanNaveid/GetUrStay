package com.example.MegLogX.Service;


import com.example.MegLogX.Dtos.PatientRequest;
import com.example.MegLogX.Dtos.PatientResponse;
import com.example.MegLogX.Entity.PatientEntity;
import com.example.MegLogX.Entity.UserEntity;
import com.example.MegLogX.Repository.PatientRepo;
import com.example.MegLogX.Repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PatientService {
    @Autowired
    private PatientRepo patientRepository;

    @Autowired
    private UserRepo userRepo;

    public PatientResponse createPatient(PatientRequest request) {
        UserEntity user = userRepo.findById(request.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));
        PatientEntity patient = new PatientEntity();
        patient.setFirstName(request.getFirstName());
        patient.setLastName(request.getLastName());
        patient.setGender(request.getGender());
        patient.setDateOfBirth(request.getDateOfBirth());
        patient.setRelationToUser(request.getRelationToUser());
        patient.setCreatedBy(user);

        PatientEntity saved = patientRepository.save(patient);
        return toResponse(saved);
    }

    public PatientResponse getPatientById(Long id) {
        PatientEntity patient = patientRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Patient not found"));
        return toResponse(patient);
    }

    public PatientResponse updatePatient(Long id, PatientRequest request) {
        PatientEntity patient = patientRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Patient not found"));

        patient.setFirstName(request.getFirstName());
        patient.setLastName(request.getLastName());
        patient.setGender(request.getGender());
        patient.setDateOfBirth(request.getDateOfBirth());
        patient.setRelationToUser(request.getRelationToUser());

        PatientEntity updated = patientRepository.save(patient);
        return toResponse(updated);
    }

    public void deletePatient(Long id) {
        if (!patientRepository.existsById(id)) {
            throw new RuntimeException("Patient not found");
        }
        patientRepository.deleteById(id);
    }

    private PatientResponse toResponse(PatientEntity patient) {
        PatientResponse response = new PatientResponse();
        response.setId(patient.getId());
        response.setFullName(patient.getFirstName() + " " + patient.getLastName());
        response.setGender(patient.getGender());
        response.setDateOfBirth(patient.getDateOfBirth());
        response.setRelationToUser(patient.getRelationToUser());
        return response;
    }
}
