package com.home.spring.controller;

import com.home.spring.dto.Pet;
import com.home.spring.service.OwnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class OwnerController {

    @Autowired
    private OwnerService ownerService;

    @RequestMapping(value = "/pets")
    @PreAuthorize("hasRole('VET')")
    public List<Pet> getPets() {
        return ownerService.getPets();
    }

    @RequestMapping(value = "/pets/{username}", method = RequestMethod.GET)
//    @PreAuthorize("hasRole('OWNER') and principal.username == #username")
    @PreAuthorize("authenticated and principal.username == #username and hasRole('ROLE_OWNER')")
    public List<Pet> getPetsForOwner(@PathVariable("username") String username) {
        return ownerService.getPetsForOwner(username);
    }

    @RequestMapping(value = "/pet/{petId}", method = RequestMethod.GET)
    @PostAuthorize("returnObject.owner.domainUser.username == principal.username or hasRole('VET')")
    public Pet getPet(@PathVariable("petId") Long petId) {
        return ownerService.getPet(petId);
    }

}
