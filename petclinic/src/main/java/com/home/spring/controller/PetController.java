package com.home.spring.controller;

import com.home.spring.dto.Pet;
import com.home.spring.service.PetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class PetController {

    @Autowired
    private PetService petService;

    @RequestMapping(value = "/pets")
    @PreAuthorize("hasRole('ROLE_VET')")
    public List<Pet> getPets() {
        return petService.getPets();
    }

    @RequestMapping(value = "/pet/{petId}", method = RequestMethod.GET)
    @PostAuthorize("principal.username == returnObject.owner.domainUser.username or hasRole('ROLE_VET')")
    public Pet getPet(@PathVariable("petId") Long petId) {
        return petService.getPet(petId);
    }
}
