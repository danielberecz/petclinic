package com.home.spring.dao;

import com.home.spring.dto.Owner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OwnerRepository extends JpaRepository<Owner, Long> {
    Owner findByDomainUserUsername(String username);
}
