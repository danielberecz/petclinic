package com.home.spring.controller;

import com.home.spring.dto.Disease;
import com.home.spring.dto.Pet;
import com.home.spring.service.OwnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class OwnerController {

    @Autowired
    private OwnerService ownerService;

    @RequestMapping(value = "/pets")
    @PreAuthorize("hasRole('ROLE_VET')")
    public List<Pet> getPets() {
        return ownerService.getPets();
    }

    @RequestMapping(value = "/pets/{username}", method = RequestMethod.GET)
    @PreAuthorize("authenticated and principal.username == #username and hasRole('ROLE_OWNER')")
    public List<Pet> getPetsForOwner(@PathVariable("username") String username) {
        return ownerService.getPetsForOwner(username);
    }

    @RequestMapping(value = "/pet/{petId}", method = RequestMethod.GET)
    @PostAuthorize("returnObject.owner.domainUser.username == principal.username or hasRole('ROLE_VET')")
    public Pet getPet(@PathVariable("petId") Long petId) {
        return ownerService.getPet(petId);
    }

    @RequestMapping(value = "/disease", method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_VET')")
    public ResponseEntity createDisease(@RequestBody Disease disease) {
        ownerService.saveDisease(disease);
        return new ResponseEntity(HttpStatus.CREATED);
    }

}
