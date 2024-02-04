package io.bootify.taming_thymeleaf.service;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
public class ActuatorUserDetailsService implements UserDetailsService {

    public static final String ACTUATOR_ADMIN = "ACTUATOR_ADMIN";

    @Override
    public UserDetails loadUserByUsername(final String username) {
        if ("actuator".equals(username)) {
            return User.withUsername(username)
                    .password("{bcrypt}$2a$12$yHivLG1T/cQbdiOa2nG5K.Zz.KmobXV3UpGffvkh5myFewYxna1Ma")
                    .authorities(ACTUATOR_ADMIN)
                    .build();
        }
        throw new UsernameNotFoundException("User " + username + " not found");
    }

}
