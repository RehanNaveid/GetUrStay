package com.example.MegLogX.Controller;


import com.example.MegLogX.Dtos.MedicalRecordsRequest;
import com.example.MegLogX.Dtos.MedicalRecordsResponse;
import com.example.MegLogX.Service.MedicalRecordsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/records")
public class MedicalRecordsController {

    @Autowired
    private MedicalRecordsService medicalRecordsService;

    @GetMapping
    public ResponseEntity<String> testEndpoint() {
        return ResponseEntity.ok("API is working");
    }
    @PostMapping
    public ResponseEntity<MedicalRecordsResponse> createRecord(@RequestBody MedicalRecordsRequest request) {
        return ResponseEntity.ok(medicalRecordsService.createRecord(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<MedicalRecordsResponse> getRecord(@PathVariable Long id) {
        return ResponseEntity.ok(medicalRecordsService.getRecord(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRecord(@PathVariable Long id) {
        medicalRecordsService.deleteRecord(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<MedicalRecordsResponse> updateRecord(@PathVariable Long id,@RequestBody MedicalRecordsRequest request) {
        return ResponseEntity.ok(medicalRecordsService.updateRecord(id, request));
    }

}
