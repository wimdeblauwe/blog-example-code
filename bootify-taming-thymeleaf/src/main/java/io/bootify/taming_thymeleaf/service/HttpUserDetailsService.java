package io.bootify.taming_thymeleaf.service;

import io.bootify.taming_thymeleaf.model.HttpUserDetails;
import io.bootify.taming_thymeleaf.model.UserRole;
import io.bootify.taming_thymeleaf.user.domain.User;
import io.bootify.taming_thymeleaf.user.repos.UserRepository;
import java.util.Collections;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
public class HttpUserDetailsService implements UserDetailsService {

    private static final Logger log = LoggerFactory.getLogger(HttpUserDetailsService.class);

    private final UserRepository userRepository;

    public HttpUserDetailsService(final UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public HttpUserDetails loadUserByUsername(final String username) {
        final User user = userRepository.findByEmailIgnoreCase(username);
        if (user == null) {
            log.warn("user not found: {}", username);
            throw new UsernameNotFoundException("User " + username + " not found");
        }
        final List<SimpleGrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority(UserRole.USER.name()));
        return new HttpUserDetails(user.getId(), username, user.getPassword(), authorities);
    }

}
