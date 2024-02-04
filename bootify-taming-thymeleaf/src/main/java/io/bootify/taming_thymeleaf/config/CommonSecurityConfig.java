package io.bootify.taming_thymeleaf.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.AnonymousAuthenticationProvider;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;


@Configuration
@EnableMethodSecurity(prePostEnabled = true)
public class CommonSecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        // please note: existing hashes must contain {bcrypt} prefix
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    @Primary
    public AuthenticationManager noopAuthenticationManager() {
        return new ProviderManager(new AnonymousAuthenticationProvider("noop"));
    }

}
