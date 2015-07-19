package com.home.spring.security;

import com.home.spring.security.dao.DomainUserRepository;
import com.home.spring.security.dto.DomainUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DomainUserDetailsService implements UserDetailsService {

    @Autowired
    private DomainUserRepository domainUserRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<DomainUser> user = Optional.ofNullable(domainUserRepository.findByUsername(username));

        if (!user.isPresent()) {
            throw new UsernameNotFoundException(username);
        }

        return new User(username, user.get().getPassword(),
                AuthorityUtils.createAuthorityList(user.get().getRole().toString()));
    }

}
