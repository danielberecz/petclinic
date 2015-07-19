package com.home.spring.dto;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity(name = "pets")
public class Pet {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    @Column(name = "pet_id")
    private Long id;

    private String name;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    private Owner owner;

    @ManyToOne
    @JoinColumn(name = "vet_id")
    private Vet vet;

    @ManyToMany
    @JoinTable(name = "pets_diseases", joinColumns = @JoinColumn(name = "disease_id"), inverseJoinColumns = @JoinColumn(name = "pet_id"))
    private List<Disease> diseases;

    @Temporal(TemporalType.DATE)
    private Date birthDate;

    public Pet() {
    }

    public Pet(String name, Owner owner, Vet vet, List<Disease> diseases, Date birthDate) {
        this.name = name;
        this.owner = owner;
        this.vet = vet;
        this.diseases = diseases;
        this.birthDate = birthDate;
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

    public Owner getOwner() {
        return owner;
    }

    public void setOwner(Owner owner) {
        this.owner = owner;
    }

    public Vet getVet() {
        return vet;
    }

    public void setVet(Vet vet) {
        this.vet = vet;
    }

    public List<Disease> getDiseases() {
        return diseases;
    }

    public void setDiseases(List<Disease> diseases) {
        this.diseases = diseases;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }
}
