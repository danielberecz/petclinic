package com.home.spring.service;

import com.home.spring.dao.DiseaseRepository;
import com.home.spring.dao.OwnerRepository;
import com.home.spring.dao.PetRepository;
import com.home.spring.dto.Disease;
import com.home.spring.dto.Owner;
import com.home.spring.dto.Pet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OwnerService {

    @Autowired
    private OwnerRepository ownerRepository;

    @Autowired
    private PetRepository petRepository;

    @Autowired
    private DiseaseRepository diseaseRepository;

    public List<Pet> getPetsForOwner(String username) {
        Owner owner = ownerRepository.findByDomainUserUsername(username);
        return petRepository.findByOwner(owner);
    }

    public Pet getPet(Long petId) {
        return petRepository.findOne(petId);
    }

    public List<Pet> getPets() {
        return petRepository.findAll();
    }

    public void saveDisease(Disease disease) {
        diseaseRepository.save(disease);
    }
}
