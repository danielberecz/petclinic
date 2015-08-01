package com.home.spring.controller;

import com.home.spring.dto.Disease;
import com.home.spring.service.DiseaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DiseaseController {

    @Autowired
    private DiseaseService diseaseService;

    @RequestMapping(value = "/disease", method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_VET')")
    public ResponseEntity createDisease(@RequestBody Disease disease) {
        diseaseService.saveDisease(disease);
        return new ResponseEntity(HttpStatus.CREATED);
    }

}
