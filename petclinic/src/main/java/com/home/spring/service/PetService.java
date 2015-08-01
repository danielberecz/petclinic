package com.home.spring.service;

import com.home.spring.dao.PetRepository;
import com.home.spring.dto.Pet;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PetService {

    @Autowired
    private PetRepository petRepository;

    @HystrixCommand(fallbackMethod = "fallbackGetPet",
            commandProperties = {@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "1500")}
    )
    public Pet getPet(Long petId) {
        return petRepository.findOne(petId);
    }

    @HystrixCommand(fallbackMethod = "fallbackGetPets",
            commandProperties = {@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "3000")}
    )
    public List<Pet> getPets() {
        return petRepository.findAll();
    }

    public Pet fallbackGetPet(Long petId) {
        return new Pet();
    }

    public List<Pet> fallbackGetPets() {
        return new ArrayList<>();
    }

}
