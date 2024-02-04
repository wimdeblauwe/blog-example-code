package io.bootify.taming_thymeleaf.config;

import io.bootify.taming_thymeleaf.service.ActuatorUserDetailsService;
import org.springframework.boot.actuate.autoconfigure.security.servlet.EndpointRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@Order(20)
public class ActuatorSecurityConfig {

    private final ActuatorUserDetailsService actuatorUserDetailsService;
    private final PasswordEncoder passwordEncoder;

    public ActuatorSecurityConfig(final ActuatorUserDetailsService actuatorUserDetailsService,
            final PasswordEncoder passwordEncoder) {
        this.actuatorUserDetailsService = actuatorUserDetailsService;
        this.passwordEncoder = passwordEncoder;
    }

    @Bean
    public AuthenticationManager actuatorAuthenticationManager() {
        final DaoAuthenticationProvider actuatorAuthenticationManager = new DaoAuthenticationProvider(passwordEncoder);
        actuatorAuthenticationManager.setUserDetailsService(actuatorUserDetailsService);
        return new ProviderManager(actuatorAuthenticationManager);
    }

    @Bean
    public SecurityFilterChain actuatorFilterChain(final HttpSecurity http) throws Exception {
        return http.csrf(csrf -> csrf.disable())
                .securityMatcher(EndpointRequest.toAnyEndpoint())
                .authenticationManager(actuatorAuthenticationManager())
                .authorizeHttpRequests(requests -> requests.anyRequest().hasAuthority(ActuatorUserDetailsService.ACTUATOR_ADMIN))
                .httpBasic(basic -> basic.realmName("actuator realm"))
                .build();
    }

}
