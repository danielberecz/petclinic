package com.home.spring.dto;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Disease {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Long id;

    private String name;
    private String treatment;

    public Disease() {
    }

    public Disease(String name, String treatment) {
        this.name = name;
        this.treatment = treatment;
    }

    public Long getId() {
        return id;
    }

    public String getName() {

        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTreatment() {
        return treatment;
    }

    public void setTreatment(String treatment) {
        this.treatment = treatment;
    }
}
