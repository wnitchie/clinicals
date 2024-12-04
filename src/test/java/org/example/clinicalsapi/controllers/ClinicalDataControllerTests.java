package org.example.clinicalsapi.controllers;

import org.example.clinicalsapi.dto.ClinicalDataRequest;
import org.example.clinicalsapi.models.ClinicalData;
import org.example.clinicalsapi.models.Patient;
import org.example.clinicalsapi.repos.ClinicalDataRepository;
import org.example.clinicalsapi.repos.PatientRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.times;

class ClinicalDataControllerTests {

    @Mock
    private ClinicalDataRepository clinicalDataRepository;

    @Mock
    private PatientRepository patientRepository;

    @InjectMocks
    private ClinicalDataController clinicalDataController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllClinicalData_returnsListOfClinicalData() {
        List<ClinicalData> clinicalDataList = Arrays.asList(new ClinicalData(), new ClinicalData());
        when(clinicalDataRepository.findAll()).thenReturn(clinicalDataList);

        List<ClinicalData> result = clinicalDataController.getAllClinicalData();

        assertEquals(2, result.size());
        verify(clinicalDataRepository, times(1)).findAll();
    }

    @Test
    void getClinicalDataById_returnsClinicalData_whenClinicalDataExists() {
        ClinicalData clinicalData = new ClinicalData();
        when(clinicalDataRepository.findById(1)).thenReturn(Optional.of(clinicalData));

        ResponseEntity<ClinicalData> response = clinicalDataController.getClinicalDataById(1);

        assertEquals(ResponseEntity.ok(clinicalData), response);
        verify(clinicalDataRepository, times(1)).findById(1);
    }

    @Test
    void getClinicalDataById_returnsNotFound_whenClinicalDataDoesNotExist() {
        when(clinicalDataRepository.findById(1)).thenReturn(Optional.empty());

        ResponseEntity<ClinicalData> response = clinicalDataController.getClinicalDataById(1);

        assertEquals(ResponseEntity.notFound().build(), response);
        verify(clinicalDataRepository, times(1)).findById(1);
    }

    @Test
    void createClinicalData_savesAndReturnsClinicalData() {
        ClinicalData clinicalData = new ClinicalData();
        when(clinicalDataRepository.save(any(ClinicalData.class))).thenReturn(clinicalData);

        ClinicalData result = clinicalDataController.createClinicalData(clinicalData);

        assertEquals(clinicalData, result);
        verify(clinicalDataRepository, times(1)).save(clinicalData);
    }

    @Test
    void updateClinicalData_updatesAndReturnsClinicalData_whenClinicalDataExists() {
        ClinicalData existingClinicalData = new ClinicalData();
        ClinicalData updatedDetails = new ClinicalData();
        updatedDetails.setComponentName("Component");
        when(clinicalDataRepository.findById(1)).thenReturn(Optional.of(existingClinicalData));
        when(clinicalDataRepository.save(any(ClinicalData.class))).thenReturn(existingClinicalData);

        ResponseEntity<ClinicalData> response = clinicalDataController.updateClinicalData(1, updatedDetails);

        assertEquals(ResponseEntity.ok(existingClinicalData), response);
        verify(clinicalDataRepository, times(1)).findById(1);
        verify(clinicalDataRepository, times(1)).save(existingClinicalData);
    }

    @Test
    void updateClinicalData_returnsNotFound_whenClinicalDataDoesNotExist() {
        when(clinicalDataRepository.findById(1)).thenReturn(Optional.empty());

        ResponseEntity<ClinicalData> response = clinicalDataController.updateClinicalData(1, new ClinicalData());

        assertEquals(ResponseEntity.notFound().build(), response);
        verify(clinicalDataRepository, times(1)).findById(1);
        verify(clinicalDataRepository, times(0)).save(any(ClinicalData.class));
    }

    @Test
    void deleteClinicalData_deletesClinicalData_whenClinicalDataExists() {
        ClinicalData clinicalData = new ClinicalData();
        when(clinicalDataRepository.findById(1)).thenReturn(Optional.of(clinicalData));

        ResponseEntity<Void> response = clinicalDataController.deleteClinicalData(1);

        assertEquals(ResponseEntity.noContent().build(), response);
        verify(clinicalDataRepository, times(1)).findById(1);
        verify(clinicalDataRepository, times(1)).delete(clinicalData);
    }

    @Test
    void deleteClinicalData_returnsNotFound_whenClinicalDataDoesNotExist() {
        when(clinicalDataRepository.findById(1)).thenReturn(Optional.empty());

        ResponseEntity<Void> response = clinicalDataController.deleteClinicalData(1);

        assertEquals(ResponseEntity.notFound().build(), response);
        verify(clinicalDataRepository, times(1)).findById(1);
        verify(clinicalDataRepository, times(0)).delete(any(ClinicalData.class));
    }

    @Test
    void saveClinicalData_savesAndReturnsClinicalData() {
        ClinicalDataRequest dataRequest = new ClinicalDataRequest();
        dataRequest.setComponentName("Component");
        dataRequest.setComponentValue("Value");
        dataRequest.setPatientId(1);

        Patient patient = new Patient();
        when(patientRepository.findById(1)).thenReturn(Optional.of(patient));
        ClinicalData clinicalData = new ClinicalData();
        when(clinicalDataRepository.save(any(ClinicalData.class))).thenReturn(clinicalData);

        ClinicalData result = clinicalDataController.saveClinicalData(dataRequest);

        assertEquals(clinicalData, result);
        verify(patientRepository, times(1)).findById(1);
        verify(clinicalDataRepository, times(1)).save(any(ClinicalData.class));
    }
}
