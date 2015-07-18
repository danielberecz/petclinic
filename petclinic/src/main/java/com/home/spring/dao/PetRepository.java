package com.home.spring.dao;

import com.home.spring.dto.Owner;
import com.home.spring.dto.Pet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PetRepository extends JpaRepository<Pet, Long> {
    List<Pet> findByOwner(Owner owner);
}
