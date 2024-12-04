package org.example.clinicalsapi.controllers;

import org.example.clinicalsapi.models.Patient;
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
import static org.mockito.Mockito.*;

class PatientControllerTests {

    @Mock
    private PatientRepository patientRepository;

    @InjectMocks
    private PatientController patientController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllPatients_returnsListOfPatients() {
        List<Patient> patients = Arrays.asList(new Patient(), new Patient());
        when(patientRepository.findAll()).thenReturn(patients);

        List<Patient> result = patientController.getAllPatients();

        assertEquals(2, result.size());
        verify(patientRepository, times(1)).findAll();
    }

    @Test
    void getPatientById_returnsPatient_whenPatientExists() {
        Patient patient = new Patient();
        when(patientRepository.findById(1)).thenReturn(Optional.of(patient));

        ResponseEntity<Patient> response = patientController.getPatientById(1);

        assertEquals(ResponseEntity.ok(patient), response);
        verify(patientRepository, times(1)).findById(1);
    }

    @Test
    void getPatientById_returnsNotFound_whenPatientDoesNotExist() {
        when(patientRepository.findById(1)).thenReturn(Optional.empty());

        ResponseEntity<Patient> response = patientController.getPatientById(1);

        assertEquals(ResponseEntity.notFound().build(), response);
        verify(patientRepository, times(1)).findById(1);
    }

    @Test
    void createPatient_savesAndReturnsPatient() {
        Patient patient = new Patient();
        when(patientRepository.save(any(Patient.class))).thenReturn(patient);

        Patient result = patientController.createPatient(patient);

        assertEquals(patient, result);
        verify(patientRepository, times(1)).save(patient);
    }

    @Test
    void updatePatient_updatesAndReturnsPatient_whenPatientExists() {
        Patient existingPatient = new Patient();
        Patient updatedDetails = new Patient();
        updatedDetails.setFirstName("John");
        when(patientRepository.findById(1)).thenReturn(Optional.of(existingPatient));
        when(patientRepository.save(any(Patient.class))).thenReturn(existingPatient);

        ResponseEntity<Patient> response = patientController.updatePatient(1, updatedDetails);

        assertEquals(ResponseEntity.ok(existingPatient), response);
        verify(patientRepository, times(1)).findById(1);
        verify(patientRepository, times(1)).save(existingPatient);
    }

    @Test
    void updatePatient_returnsNotFound_whenPatientDoesNotExist() {
        when(patientRepository.findById(1)).thenReturn(Optional.empty());

        ResponseEntity<Patient> response = patientController.updatePatient(1, new Patient());

        assertEquals(ResponseEntity.notFound().build(), response);
        verify(patientRepository, times(1)).findById(1);
        verify(patientRepository, times(0)).save(any(Patient.class));
    }

    @Test
    void deletePatient_deletesPatient_whenPatientExists() {
        Patient patient = new Patient();
        when(patientRepository.findById(1)).thenReturn(Optional.of(patient));

        ResponseEntity<Void> response = patientController.deletePatient(1);

        assertEquals(ResponseEntity.noContent().build(), response);
        verify(patientRepository, times(1)).findById(1);
        verify(patientRepository, times(1)).delete(patient);
    }

    @Test
    void deletePatient_returnsNotFound_whenPatientDoesNotExist() {
        when(patientRepository.findById(1)).thenReturn(Optional.empty());

        ResponseEntity<Void> response = patientController.deletePatient(1);

        assertEquals(ResponseEntity.notFound().build(), response);
        verify(patientRepository, times(1)).findById(1);
        verify(patientRepository, times(0)).delete(any(Patient.class));
    }
}
