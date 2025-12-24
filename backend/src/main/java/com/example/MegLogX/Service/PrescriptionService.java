package com.example.MegLogX.Service;

import com.example.MegLogX.Dtos.PrescriptionRequest;
import com.example.MegLogX.Dtos.PrescriptionResponse;
import com.example.MegLogX.Entity.PatientEntity;
import com.example.MegLogX.Entity.PrescriptionEntity;
import com.example.MegLogX.Repository.PatientRepo;
import com.example.MegLogX.Repository.PrescriptionRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PrescriptionService {
    @Autowired
    private PrescriptionRepo prescriptionRepository;
    @Autowired
    private PatientRepo patientRepository;


    public PrescriptionResponse createPrescription(PrescriptionRequest request) {
        PatientEntity patient = patientRepository.findById(request.getPatientId())
                .orElseThrow(() -> new RuntimeException("Patient not found"));

        PrescriptionEntity entity = new PrescriptionEntity();
        entity.setDoctorName(request.getDoctorName());
        entity.setIssuedDate(request.getIssuedDate());
        entity.setMedicines(request.getMedicines());
        entity.setNotes(request.getNotes());
        entity.setPatient(patient);

        PrescriptionEntity saved = prescriptionRepository.save(entity);
        return toResponse(saved);
    }

    public PrescriptionResponse getPrescription(Long id) {
        PrescriptionEntity entity = prescriptionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Prescription not found"));
        return toResponse(entity);
    }
    public PrescriptionResponse updatePrescription(Long id, PrescriptionRequest request) {
        PrescriptionEntity entity = prescriptionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Prescription not found"));

        entity.setDoctorName(request.getDoctorName());
        entity.setIssuedDate(request.getIssuedDate());
        entity.setMedicines(request.getMedicines());
        entity.setNotes(request.getNotes());

        PrescriptionEntity updated = prescriptionRepository.save(entity);
        return toResponse(updated);
    }

    public void deletePrescription(Long id) {
        if (!prescriptionRepository.existsById(id)) {
            throw new RuntimeException("Prescription not found");
        }
        prescriptionRepository.deleteById(id);
    }

    private PrescriptionResponse toResponse(PrescriptionEntity entity) {
        PrescriptionResponse res = new PrescriptionResponse();
        res.setId(entity.getId());
        res.setDoctorName(entity.getDoctorName());
        res.setIssuedDate(entity.getIssuedDate());
        res.setMedicines(entity.getMedicines());
        res.setNotes(entity.getNotes());

        PatientEntity patient = entity.getPatient();
        res.setPatientFullName(patient.getFirstName() + " " + patient.getLastName());

        return res;
    }
}
