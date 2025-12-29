package com.adminplatform.los_auth.authorize.service;

import com.adminplatform.los_auth.authorize.entity.UserAccount;
import com.adminplatform.los_auth.authorize.repository.UserAccountRepository;
import com.adminplatform.los_auth.authorize.security.CustomUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserAccountRepository userAccountRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<UserAccount> optionalUser = userAccountRepository.findByEmailIgnoreCase(email);
        if (optionalUser.isEmpty()) {
            throw new UsernameNotFoundException("User not found");
        }

        UserAccount user = optionalUser.get();

        // Add tenant ID and other attributes
        Map<String, Object> attributes = new HashMap<>();
        attributes.put("tenantId", user.getTenantId().toString());

        return new CustomUserDetails(
                user.getEmail(),
                user.getPasswordHash(),
                attributes,
                Collections.emptyList() // Replace with actual roles/authorities
        );
    }
}