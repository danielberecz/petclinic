package com.home.spring.service;

import com.home.spring.dao.DiseaseRepository;
import com.home.spring.dto.Disease;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DiseaseService {

    @Autowired
    private DiseaseRepository diseaseRepository;

    public void saveDisease(Disease disease) {
        diseaseRepository.save(disease);
    }

}
