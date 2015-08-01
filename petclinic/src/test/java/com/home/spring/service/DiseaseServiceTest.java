package com.home.spring.service;

import com.home.spring.dao.DiseaseRepository;
import com.home.spring.dto.Disease;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(value = SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = ServiceTestConfiguration.class)
@Transactional
public class DiseaseServiceTest {

    @Autowired
    private DiseaseService diseaseService;

    @Autowired
    private DiseaseRepository diseaseRepository;

    @Test
    public void testSaveDisease() throws Exception {
        Disease disease = new Disease("disease", "treatment");
        diseaseService.saveDisease(disease);
        Assert.assertTrue(diseaseRepository.findAll().stream().anyMatch(d -> d.getName().equals(disease.getName()) &&
                d.getTreatment().equals(disease.getTreatment())));
    }

    @After
    public void tearDown() throws Exception {
        diseaseRepository.deleteAll();
    }
}