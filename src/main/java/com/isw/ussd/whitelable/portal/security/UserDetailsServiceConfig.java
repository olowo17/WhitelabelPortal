package com.isw.ussd.whitelable.portal.security;

import com.isw.ussd.whitelable.portal.dtos.CustomUserDetails;
import com.isw.ussd.whitelable.portal.entities.portal.PortalUser;
import com.isw.ussd.whitelable.portal.repositories.portal.PortalUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

@Configuration
@RequiredArgsConstructor
public class UserDetailsServiceConfig {
private final PortalUserRepository userRepository;
//    @Bean
//    public UserDetailsService userDetailsService() {
//        return username -> {
//            Optional<PortalUser> user = userRepository.findByUsername(username);
//            if (!user.isPresent()) {
//                throw new UsernameNotFoundException("User not found");
//            }
//            return new org.springframework.security.core.userdetails.User(user.get().getUsername(), user.get().getPassword(),
//                    user.get().getRoles().stream()
//                            .map(role -> new SimpleGrantedAuthority(role.getName()))
//                            .collect(Collectors.toList()));
//        };
//    }

    @Bean
    public UserDetailsService userDetailsService() {
        return username -> {
            Optional<PortalUser> user = userRepository.findByUsername(username);
            if (!user.isPresent()) {
                throw new UsernameNotFoundException("User not found");
            }
            // Return the CustomUserDetails with the PortalUser entity
            return new CustomUserDetails(user.get());
        };
    }
}

