package com.example.MegLogX.Repository;

import com.example.MegLogX.Entity.PrescriptionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PrescriptionRepo extends JpaRepository<PrescriptionEntity, Long> {

}
