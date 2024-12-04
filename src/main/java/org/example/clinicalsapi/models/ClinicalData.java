package org.example.clinicalsapi.models;

//clinical data jpa model class with component name, component value, patient id, measured date
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;

@Entity
@Table(name = "clinicaldata")
public class ClinicalData {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "clinicaldata_seq")
    @SequenceGenerator(name = "clinicaldata_seq", sequenceName = "clinicaldata_seq", allocationSize = 1)
    private int id;
    private String componentName;
    private String componentValue;

    @CreationTimestamp
    private Date measuredDateTime;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_id", nullable = false)
    @JsonIgnore
    private Patient patient;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getComponentName() {
        return componentName;
    }

    public void setComponentName(String componentName) {
        this.componentName = componentName;
    }

    public String getComponentValue() {
        return componentValue;
    }

    public void setComponentValue(String componentValue) {
        this.componentValue = componentValue;
    }

    public Date getMeasuredDateTime() {
        return measuredDateTime;
    }

    public void setMeasuredDateTime(Date measuredDateTime) {
        this.measuredDateTime = measuredDateTime;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }
}
