package com.example.MegLogX.Repository;

import com.example.MegLogX.Entity.ReportEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReportRepo extends JpaRepository<ReportEntity, Long> {
    List<ReportEntity> findByPatientId(Long patientId);
}
