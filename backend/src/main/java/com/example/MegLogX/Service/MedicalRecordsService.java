package com.example.MegLogX.Service;


import com.example.MegLogX.Dtos.MedicalRecordsRequest;
import com.example.MegLogX.Dtos.MedicalRecordsResponse;
import com.example.MegLogX.Entity.MedicalRecordsEntity;
import com.example.MegLogX.Entity.PatientEntity;
import com.example.MegLogX.Repository.MedicalRecordsRepo;
import com.example.MegLogX.Repository.PatientRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MedicalRecordsService {
    @Autowired
    private MedicalRecordsRepo recordsRepository;
    @Autowired
    private PatientRepo patientRepository;

    public MedicalRecordsResponse createRecord(MedicalRecordsRequest request) {
        PatientEntity patient = patientRepository.findById(request.getPatientId())
                .orElseThrow(() -> new RuntimeException("Patient not found"));

        MedicalRecordsEntity record = new MedicalRecordsEntity();
        record.setConditionName(request.getConditionName());
        record.setDescription(request.getDescription());
        record.setDiagnosisDate(request.getDiagnosisDate());
        record.setStatus(request.getStatus());
        record.setPatient(patient);

        MedicalRecordsEntity saved = recordsRepository.save(record);
        return toResponse(saved);
    }

    public MedicalRecordsResponse getRecord(Long id) {
        MedicalRecordsEntity record = recordsRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Record not found"));
        return toResponse(record);
    }

    public void deleteRecord(Long id) {
        if (!recordsRepository.existsById(id)) {
            throw new RuntimeException("Record not found");
        }
        recordsRepository.deleteById(id);
    }

    public MedicalRecordsResponse updateRecord(Long id, MedicalRecordsRequest request) {
        MedicalRecordsEntity record = recordsRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Record not found"));

        // Update fields
        record.setConditionName(request.getConditionName());
        record.setDescription(request.getDescription());
        record.setDiagnosisDate(request.getDiagnosisDate());
        record.setStatus(request.getStatus());

        // Save updated record
        MedicalRecordsEntity updatedRecord = recordsRepository.save(record);
        return toResponse(updatedRecord);
    }


    private MedicalRecordsResponse toResponse(MedicalRecordsEntity record) {
        MedicalRecordsResponse response = new MedicalRecordsResponse();
        response.setId(record.getId());
        response.setConditionName(record.getConditionName());
        response.setDescription(record.getDescription());
        response.setDiagnosisDate(record.getDiagnosisDate());
        response.setStatus(record.getStatus());

        PatientEntity patient = record.getPatient();
        response.setPatientFullName(patient.getFirstName() + " " + patient.getLastName());

        return response;
    }
}
