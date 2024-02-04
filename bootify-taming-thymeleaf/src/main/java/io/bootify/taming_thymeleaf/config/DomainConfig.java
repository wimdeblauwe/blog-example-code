package io.bootify.taming_thymeleaf.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;


@Configuration
@EntityScan("io.bootify.taming_thymeleaf")
@EnableJpaRepositories("io.bootify.taming_thymeleaf")
@EnableTransactionManagement
public class DomainConfig {
}
