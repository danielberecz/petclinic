package com.home.spring.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.List;

@Entity
public class Owner {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Long id;

    private String firstName;
    private String lastName;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "owner", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Pet> pets;

    public Owner() {
    }

    public Owner(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
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

    public List<Pet> getPets() {
        return pets;
    }

    public void setPets(List<Pet> pets) {
        this.pets = pets;
    }
}
