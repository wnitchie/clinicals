package org.example.clinicalsapi.controllers;

import org.example.clinicalsapi.dto.ClinicalDataRequest;
import org.example.clinicalsapi.models.ClinicalData;
import org.example.clinicalsapi.models.Patient;
import org.example.clinicalsapi.repos.ClinicalDataRepository;
import org.example.clinicalsapi.repos.PatientRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/clinicaldata")
public class ClinicalDataController {

    private static final Logger logger = LoggerFactory.getLogger(ClinicalDataController.class);

    @Autowired
    private ClinicalDataRepository clinicalDataRepository;

    @Autowired
    private PatientRepository patientRepository;

    @GetMapping
    public List<ClinicalData> getAllClinicalData() {
        logger.info("Fetching all clinical data");
        return clinicalDataRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClinicalData> getClinicalDataById(@PathVariable Integer id) {
        logger.info("Fetching clinical data with id: {}", id);
        Optional<ClinicalData> clinicalData = clinicalDataRepository.findById(id);
        return clinicalData.map(ResponseEntity::ok).orElseGet(() -> {
            logger.warn("Clinical data with id: {} not found", id);
            return ResponseEntity.notFound().build();
        });
    }

    @PostMapping
    public ClinicalData createClinicalData(@RequestBody ClinicalData clinicalData) {
        logger.info("Creating new clinical data");
        return clinicalDataRepository.save(clinicalData);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClinicalData> updateClinicalData(@PathVariable Integer id, @RequestBody ClinicalData clinicalDataDetails) {
        logger.info("Updating clinical data with id: {}", id);
        Optional<ClinicalData> clinicalData = clinicalDataRepository.findById(id);
        if (clinicalData.isPresent()) {
            ClinicalData updatedClinicalData = clinicalData.get();
            updatedClinicalData.setComponentName(clinicalDataDetails.getComponentName());
            updatedClinicalData.setComponentValue(clinicalDataDetails.getComponentValue());
            updatedClinicalData.setMeasuredDateTime(clinicalDataDetails.getMeasuredDateTime());
            return ResponseEntity.ok(clinicalDataRepository.save(updatedClinicalData));
        } else {
            logger.warn("Clinical data with id: {} not found", id);
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteClinicalData(@PathVariable Integer id) {
        logger.info("Deleting clinical data with id: {}", id);
        Optional<ClinicalData> clinicalData = clinicalDataRepository.findById(id);
        if (clinicalData.isPresent()) {
            clinicalDataRepository.delete(clinicalData.get());
            return ResponseEntity.noContent().build();
        } else {
            logger.warn("Clinical data with id: {} not found", id);
            return ResponseEntity.notFound().build();
        }
    }

    //method that receives patient id, clinical data and saves it to the database
    @PostMapping("/clinicals")
    public ClinicalData saveClinicalData(@RequestBody ClinicalDataRequest dataRequest) {
        logger.info("Saving clinical data for patient id: {}", dataRequest.getPatientId());
        ClinicalData clinicalData = new ClinicalData();
        clinicalData.setComponentName(dataRequest.getComponentName());
        clinicalData.setComponentValue(dataRequest.getComponentValue());
        Patient patient = patientRepository.findById(dataRequest.getPatientId()).get();
        clinicalData.setPatient(patient);
        return clinicalDataRepository.save(clinicalData);
    }
}
