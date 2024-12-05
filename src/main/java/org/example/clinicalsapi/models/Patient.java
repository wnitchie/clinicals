package org.example.clinicalsapi.models;

//patient jpa model clas with firstname, lastname, age
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.List;

@Entity
public class Patient {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "patient_seq")
    @SequenceGenerator(name = "patient_seq", sequenceName = "patient_seq", allocationSize = 1)
    private int id;
    private String firstName;
    private String lastName;
    private int age;
    @OneToMany(mappedBy = "patient", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JsonIgnore
    private List<ClinicalData> clinicalData;

    public List<ClinicalData> getClinicalData() {
        return clinicalData;
    }

    public void setClinicalData(List<ClinicalData> clinicalData) {
        this.clinicalData = clinicalData;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getFirstName() {
        return firstName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public String getLastName() {
        return lastName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    public int getAge() {
        return age;
    }
    public void setAge(int age) {
        this.age = age;
    }
}
