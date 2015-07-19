package com.home.spring.controller;

import com.home.spring.service.OwnerService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import static org.mockito.Mockito.mock;

@Configuration
public class OwnerControllerTestConfiguration {

    @Bean
    @Primary
    public OwnerService getOwnerService() {
        return mock(OwnerService.class);
    }

}
