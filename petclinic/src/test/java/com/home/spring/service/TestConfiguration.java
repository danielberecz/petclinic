package com.home.spring.service;

import com.home.spring.dao.DiseaseRepository;
import com.home.spring.dao.OwnerRepository;
import com.home.spring.dao.PetRepository;
import com.home.spring.dao.VetRepository;
import com.home.spring.dto.Disease;
import com.home.spring.dto.Owner;
import com.home.spring.dto.Pet;
import com.home.spring.dto.Vet;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackageClasses = {OwnerRepository.class, PetRepository.class, VetRepository.class, DiseaseRepository.class})
@EntityScan(basePackageClasses = {Disease.class, Owner.class, Pet.class, Vet.class})
@Import({DataSourceAutoConfiguration.class, HibernateJpaAutoConfiguration.class})
public class TestConfiguration {

    @Bean
    public OwnerService getOwnerService() {
        return new OwnerService();
    }
}
