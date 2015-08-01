package com.home.spring.service;

import com.home.spring.dao.OwnerRepository;
import com.home.spring.dao.PetRepository;
import com.home.spring.dto.Owner;
import com.home.spring.dto.Pet;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OwnerService {

    @Autowired
    private OwnerRepository ownerRepository;

    @Autowired
    private PetRepository petRepository;

    @HystrixCommand(fallbackMethod = "fallbackPetsForOwner",
        commandProperties = {@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "3000")}
    )
    public List<Pet> getPetsForOwner(String username) {
        Owner owner = ownerRepository.findByDomainUserUsername(username);
        return petRepository.findByOwner(owner);
    }

    public List<Pet> fallbackPetsForOwner(String username) {
        return new ArrayList<>();
    }
}
