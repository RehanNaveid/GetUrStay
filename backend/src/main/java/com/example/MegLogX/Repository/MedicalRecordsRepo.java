package com.example.MegLogX.Repository;

import com.example.MegLogX.Entity.MedicalRecordsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MedicalRecordsRepo extends JpaRepository<MedicalRecordsEntity, Long> {

}
