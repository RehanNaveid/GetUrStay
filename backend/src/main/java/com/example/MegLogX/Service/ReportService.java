package com.example.MegLogX.Service;

import com.example.MegLogX.Dtos.ReportRequest;
import com.example.MegLogX.Dtos.ReportResponse;
import com.example.MegLogX.Entity.PatientEntity;
import com.example.MegLogX.Entity.ReportEntity;
import com.example.MegLogX.Repository.PatientRepo;
import com.example.MegLogX.Repository.ReportRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReportService {
    @Autowired
    private ReportRepo reportRepository;
    @Autowired
    private  PatientRepo patientRepository;


    public ReportResponse createReport(ReportRequest dto) {
        PatientEntity patient = patientRepository.findById(dto.getPatientId())
                .orElseThrow(() -> new RuntimeException("Patient not found"));

        ReportEntity report = new ReportEntity();
        report.setReportType(dto.getReportType());
        report.setReportDate(dto.getReportDate());
        report.setFileUrl(dto.getReportUrl());
        report.setDescription(dto.getDescription());
        report.setPatient(patient);

        report = reportRepository.save(report);

        return mapToResponseDTO(report);
    }

    public List<ReportResponse> getReportsByPatientId(Long patientId) {
        return reportRepository.findByPatientId(patientId)
                .stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }

    public ReportResponse getReportById(Long id) {
        ReportEntity report = reportRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Report not found"));

        return mapToResponseDTO(report);
    }

    public ReportResponse updateReport(Long id, ReportRequest request) {
        ReportEntity report = reportRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Report not found"));

        report.setReportType(request.getReportType());
        report.setReportDate(request.getReportDate());
        report.setDescription(request.getDescription());
        report.setFileUrl(request.getReportUrl());

        ReportEntity updatedReport = reportRepository.save(report);
        return mapToResponseDTO(updatedReport);
    }


    public void deleteReport(Long id) {
        if (!reportRepository.existsById(id)) {
            throw new RuntimeException("Report not found");
        }
        reportRepository.deleteById(id);
    }


    // 🔁 Private utility to map Entity to DTO
    private ReportResponse mapToResponseDTO(ReportEntity report) {
        ReportResponse dto = new ReportResponse();
        dto.setId(report.getId());
        dto.setReportType(report.getReportType());
        dto.setReportDate(report.getReportDate());
        dto.setReportUrl(report.getFileUrl());
        dto.setDescription(report.getDescription());
        dto.setPatientFullName(report.getPatient().getFirstName()+" "+report.getPatient().getLastName());
        return dto;
    }

}
