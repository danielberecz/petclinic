package com.home.spring.service;

import com.home.spring.dao.OwnerRepository;
import com.home.spring.dao.PetRepository;
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

    public List<Pet> getPetsForOwner(Long ownerId) {
        Owner owner = ownerRepository.findOne(ownerId);
        return petRepository.findByOwner(owner);
    }

    public Pet getPet(Long petId) {
        return petRepository.findOne(petId);
    }
}
