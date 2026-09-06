package com.wimdeblauwe.greeter;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.ClientHttpRequestInterceptor;

/**
 * The broken variant of {@link GreeterAutoConfiguration}, kept here to demonstrate the problem.
 * <p>
 * The return type of {@link #greeterRequestInterceptor(Greeter)} comes from the optional
 * {@code spring-web} dependency. Introspecting this class fails with a {@code NoClassDefFoundError}
 * when {@code spring-web} is not on the classpath, even though the {@code @ConditionalOnClass}
 * would never let the bean be created.
 */
@AutoConfiguration
@EnableConfigurationProperties(GreeterProperties.class)
public class BrokenGreeterAutoConfiguration {

  @Bean
  @ConditionalOnMissingBean
  public Greeter greeter(GreeterProperties properties) {
    return new Greeter(properties.getGreeting());
  }

  @Bean
  @ConditionalOnClass(ClientHttpRequestInterceptor.class)
  public ClientHttpRequestInterceptor greeterRequestInterceptor(Greeter greeter) {
    return new GreeterRequestInterceptor(greeter);
  }
}
