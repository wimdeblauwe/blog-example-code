package io.bootify.taming_thymeleaf.service;

import io.bootify.taming_thymeleaf.util.UserRoles;
import java.util.Collections;
import java.util.List;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
public class HttpUserDetailsService implements UserDetailsService {

    @Override
    public UserDetails loadUserByUsername(final String username) {
        if ("bootify".equals(username)) {
            final List<SimpleGrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority(UserRoles.ROLE_USER));
            return User.withUsername(username)
                    .password("{bcrypt}$2a$10$FMzmOkkfbApEWxS.4XzCKOR7EbbiwzkPEyGgYh6uQiPxurkpzRMa6")
                    .authorities(authorities)
                    .build();
        }
        throw new UsernameNotFoundException("User " + username + " not found");
    }

}
