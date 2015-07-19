package com.home.spring.service;

import com.home.spring.dao.DiseaseRepository;
import com.home.spring.dao.OwnerRepository;
import com.home.spring.dao.PetRepository;
import com.home.spring.dao.VetRepository;
import com.home.spring.dto.Disease;
import com.home.spring.dto.Owner;
import com.home.spring.dto.Pet;
import com.home.spring.dto.Vet;
import com.home.spring.security.dao.DomainUserRepository;
import com.home.spring.security.dto.DomainUser;
import com.home.spring.security.dto.Role;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@RunWith(value = SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = OwnerServiceTestConfiguration.class)
@Transactional
public class OwnerServiceTest {

    @Autowired
    private OwnerService ownerService;

    @Autowired
    private DiseaseRepository diseaseRepository;

    @Autowired
    private PetRepository petRepository;

    @Autowired
    private OwnerRepository ownerRepository;

    @Autowired
    private VetRepository vetRepository;

    @Autowired
    private DomainUserRepository domainUserRepository;

    private List<Pet> pets;

    @Before
    public void setUp() throws Exception {
        Disease parvoVirus = new Disease("Parvo Virus", "CPV-2b");
        Disease arthiris = new Disease("Arthiris", "Acupuncture");
        Disease rabies = new Disease("Rabies", "Vaccination");

        DomainUser domainUserOwnerA = new DomainUser("ownera", "pass", Role.ROLE_OWNER);
        DomainUser domainUserOwnerB = new DomainUser("ownerb", "pass", Role.ROLE_OWNER);
        DomainUser domainUserVetA = new DomainUser("veta", "vet", Role.ROLE_VET);
        DomainUser domainUserVetB = new DomainUser("vetb", "vet", Role.ROLE_VET);

        Vet vetA = new Vet("Vet", "A", domainUserVetA);
        Vet vetB = new Vet("Vet", "B", domainUserVetB);

        Owner ownerA = new Owner("OwnerA", "Owner", domainUserOwnerA);
        Owner ownerB = new Owner("OwnerB", "Owner", domainUserOwnerB);

        Pet cat = new Pet("Cat", ownerA, vetB, Collections.singletonList(rabies), new Date());
        Pet dog = new Pet("Dog", ownerA, vetA, Collections.singletonList(parvoVirus), new Date());
        Pet hamster = new Pet("Hamster", ownerB, vetA, Arrays.asList(arthiris, rabies), new Date());

        vetA.setPatients(Arrays.asList(dog, hamster));
        vetB.setPatients(Collections.singletonList(cat));

        ownerA.setPets(Arrays.asList(cat, dog));
        ownerB.setPets(Collections.singletonList(hamster));

        diseaseRepository.save(Arrays.asList(parvoVirus, arthiris, rabies));
        pets = petRepository.save(Arrays.asList(cat, dog, hamster));
        ownerRepository.save(Arrays.asList(ownerA, ownerB));
        vetRepository.save(Arrays.asList(vetA, vetB));
        domainUserRepository.save(Arrays.asList(domainUserOwnerA, domainUserOwnerB, domainUserVetA, domainUserVetB));
    }


    @After
    public void tearDown() throws Exception {
        petRepository.deleteAll();
        ownerRepository.deleteAll();
        vetRepository.deleteAll();
        diseaseRepository.deleteAll();
        domainUserRepository.deleteAll();
    }

    @Test
    public void testGetPetsForOwner() throws Exception {
        List<Pet> petsForOwner = ownerService.getPetsForOwner("OwnerA");
        Assert.assertTrue(petsForOwner.stream().allMatch(p -> p.getName().equals("Cat") || p.getName().equals("Dog")));
    }

    @Test
    public void testGetPet() throws Exception {
        Optional<Pet> cat = pets.stream().filter(p -> p.getName().equals("Cat")).findFirst();
        Pet pet = ownerService.getPet(cat.get().getId());
        Assert.assertEquals("Cat", pet.getName());
    }
}