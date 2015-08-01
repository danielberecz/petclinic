package com.home.spring.controller;

import com.home.spring.service.DiseaseService;
import com.home.spring.service.OwnerService;
import com.home.spring.service.PetService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import static org.mockito.Mockito.mock;

@Configuration
public class ControllerTestConfiguration {

    @Bean
    @Primary
    public DiseaseService getDiseaseService() {
        return mock(DiseaseService.class);
    }

    @Bean
    @Primary
    public OwnerService getOwnerService() {
        return mock(OwnerService.class);
    }

    @Bean
    @Primary
    public PetService getPetService() {
        return mock(PetService.class);
    }

}
