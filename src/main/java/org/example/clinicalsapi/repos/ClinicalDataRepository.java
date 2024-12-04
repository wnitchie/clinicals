package org.example.clinicalsapi.repos;

import org.example.clinicalsapi.models.ClinicalData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClinicalDataRepository extends JpaRepository<ClinicalData, Integer> {
}