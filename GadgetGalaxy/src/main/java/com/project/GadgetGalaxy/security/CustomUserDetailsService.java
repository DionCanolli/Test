package com.project.GadgetGalaxy.security;

import com.project.GadgetGalaxy.model.Users;
import com.project.GadgetGalaxy.service.ModelService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private static final Logger logger = LoggerFactory.getLogger(CustomUserDetailsService.class);

    private final ModelService modelService;

    public CustomUserDetailsService(ModelService modelService) {
        this.modelService = modelService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users user = modelService.getUsersRepository().findByUserEmail(username);
        if (user == null) {
            logger.error("User not found with email: {}", username);
            throw new UsernameNotFoundException("User not found");
        }

        logger.info("User found with email: {}", username);
        return new CustomUserDetails(user);
    }


}
