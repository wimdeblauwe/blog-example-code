package io.bootify.taming_thymeleaf.config;

import static org.springframework.security.config.Customizer.withDefaults;

import io.bootify.taming_thymeleaf.service.HttpUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;


@Configuration
public class HttpSecurityConfig {

    private final PasswordEncoder passwordEncoder;
    private final HttpUserDetailsService httpUserDetailsService;

    public HttpSecurityConfig(final PasswordEncoder passwordEncoder,
            final HttpUserDetailsService httpUserDetailsService) {
        this.passwordEncoder = passwordEncoder;
        this.httpUserDetailsService = httpUserDetailsService;
    }

    @Bean
    public AuthenticationManager httpAuthenticationManager() {
        final DaoAuthenticationProvider httpAuthenticationManager = new DaoAuthenticationProvider(passwordEncoder);
        httpAuthenticationManager.setUserDetailsService(httpUserDetailsService);
        return new ProviderManager(httpAuthenticationManager);
    }

    @Bean
    public SecurityFilterChain httpFilterChain(final HttpSecurity http) throws Exception {
        return http.cors(withDefaults())
                .csrf(withDefaults())
                .authorizeHttpRequests(authorize -> authorize.anyRequest().permitAll())
                .authenticationManager(httpAuthenticationManager())
                .formLogin(form -> form
                    .loginPage("/login")
                    .usernameParameter("login")
                    .failureUrl("/login?loginError=true"))
                .logout(logout -> logout
                    .logoutSuccessUrl("/login?logoutSuccess=true")
                    .deleteCookies("JSESSIONID"))
                .exceptionHandling(exception -> exception
                    .authenticationEntryPoint(new LoginUrlAuthenticationEntryPoint("/login?loginRequired=true")))
                .build();
    }

}
