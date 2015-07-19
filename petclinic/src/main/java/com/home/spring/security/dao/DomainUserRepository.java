package com.home.spring.security.dao;

import com.home.spring.security.dto.DomainUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DomainUserRepository extends JpaRepository<DomainUser, Long> {

    DomainUser findByUsername(String username);

}
