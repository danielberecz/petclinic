package com.home.spring.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.home.spring.security.dto.DomainUser;

import javax.persistence.*;
import java.util.List;

@Entity
public class Vet {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Long id;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, optional = false, orphanRemoval = true)
    @JsonIgnore
    private DomainUser domainUser;

    private String firstName;
    private String lastName;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "vet", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Pet> patients;

    public Vet() {
    }

    public Vet(String firstName, String lastName, DomainUser domainUser) {
        this.domainUser = domainUser;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public DomainUser getDomainUser() {
        return domainUser;
    }

    public void setDomainUser(DomainUser domainUser) {
        this.domainUser = domainUser;
    }

    public Long getId() {
        return id;
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

    public List<Pet> getPatients() {
        return patients;
    }

    public void setPatients(List<Pet> patients) {
        this.patients = patients;
    }
}
