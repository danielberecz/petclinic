package com.home.spring.controller;

import com.home.spring.dto.Pet;
import com.home.spring.service.OwnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class OwnerController {

    @Autowired
    private OwnerService ownerService;

    @RequestMapping(value = "/pets/{id}", method = RequestMethod.GET)
    public List<Pet> getPetsForOwner(@PathVariable("id") Long id) {
        return ownerService.getPetsForOwner(id);
    }

    @RequestMapping(value = "/pet/{petId}", method = RequestMethod.GET)
    public Pet getPet(@PathVariable("petId") Long petId) {
        return ownerService.getPet(petId);
    }

}
