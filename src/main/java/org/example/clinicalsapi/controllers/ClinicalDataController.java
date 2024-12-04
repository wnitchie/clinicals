package org.example.clinicalsapi.controllers;

import org.example.clinicalsapi.models.ClinicalData;
import org.example.clinicalsapi.repos.ClinicalDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/clinicaldata")
public class ClinicalDataController {

    @Autowired
    private ClinicalDataRepository clinicalDataRepository;

    @GetMapping
    public List<ClinicalData> getAllClinicalData() {
        return clinicalDataRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClinicalData> getClinicalDataById(@PathVariable Integer id) {
        Optional<ClinicalData> clinicalData = clinicalDataRepository.findById(id);
        return clinicalData.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ClinicalData createClinicalData(@RequestBody ClinicalData clinicalData) {
        return clinicalDataRepository.save(clinicalData);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClinicalData> updateClinicalData(@PathVariable Integer id, @RequestBody ClinicalData clinicalDataDetails) {
        Optional<ClinicalData> clinicalData = clinicalDataRepository.findById(id);
        if (clinicalData.isPresent()) {
            ClinicalData updatedClinicalData = clinicalData.get();
            updatedClinicalData.setComponentName(clinicalDataDetails.getComponentName());
            updatedClinicalData.setComponentValue(clinicalDataDetails.getComponentValue());
            updatedClinicalData.setMeasuredDateTime(clinicalDataDetails.getMeasuredDateTime());
            return ResponseEntity.ok(clinicalDataRepository.save(updatedClinicalData));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteClinicalData(@PathVariable Integer id) {
        Optional<ClinicalData> clinicalData = clinicalDataRepository.findById(id);
        if (clinicalData.isPresent()) {
            clinicalDataRepository.delete(clinicalData.get());
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
