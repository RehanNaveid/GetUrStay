package com.example.MegLogX.Repository;


import com.example.MegLogX.Entity.PatientEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PatientRepo extends JpaRepository<PatientEntity, Long> {
    List<PatientEntity> findAllByCreatedById(Long userId);
}

