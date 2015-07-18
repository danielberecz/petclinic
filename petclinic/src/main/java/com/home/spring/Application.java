package com.home.spring;

import com.home.spring.dao.DiseaseRepository;
import com.home.spring.dao.OwnerRepository;
import com.home.spring.dao.PetRepository;
import com.home.spring.dao.VetRepository;
import com.home.spring.dto.Disease;
import com.home.spring.dto.Owner;
import com.home.spring.dto.Pet;
import com.home.spring.dto.Vet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Collections;
import java.util.Date;

@SpringBootApplication
public class Application implements CommandLineRunner {

    @Autowired
    private DiseaseRepository diseaseRepository;

    @Autowired
    private OwnerRepository ownerRepository;

    @Autowired
    private PetRepository petRepository;

    @Autowired
    private VetRepository vetRepository;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Override
    @Transactional
    public void run(String... strings) throws Exception {
        Disease parvoVirus = new Disease("Parvo Virus", "CPV-2b");
        Disease arthiris = new Disease("Arthiris", "Acupuncture");
        Disease rabies = new Disease("Rabies", "Vaccination");

        Vet vetA = new Vet("Vet", "A");
        Vet vetB = new Vet("Vet", "B");

        Owner ownerA = new Owner("Owner", "A");
        Owner ownerB = new Owner("Owner", "B");

        Pet cat = new Pet("Cat", ownerA, vetB, Collections.singletonList(rabies), new Date());
        Pet dog = new Pet("Dog", ownerA, vetA, Collections.singletonList(parvoVirus), new Date());
        Pet hamster = new Pet("Hamster", ownerB, vetA, Arrays.asList(arthiris, rabies), new Date());

        vetA.setPatients(Arrays.asList(dog, hamster));
        vetB.setPatients(Collections.singletonList(cat));

        ownerA.setPets(Arrays.asList(cat, dog));
        ownerB.setPets(Collections.singletonList(hamster));

        diseaseRepository.save(Arrays.asList(parvoVirus, arthiris, rabies));
        petRepository.save(Arrays.asList(cat, dog, hamster));
        ownerRepository.save(Arrays.asList(ownerA, ownerB));
        vetRepository.save(Arrays.asList(vetA, vetB));
    }
}
