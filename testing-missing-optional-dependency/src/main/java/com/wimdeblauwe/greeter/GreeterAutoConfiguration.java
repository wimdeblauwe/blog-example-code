package com.wimdeblauwe.greeter;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestInterceptor;

@AutoConfiguration
@EnableConfigurationProperties(GreeterProperties.class)
public class GreeterAutoConfiguration {

  @Bean
  @ConditionalOnMissingBean
  public Greeter greeter(GreeterProperties properties) {
    return new Greeter(properties.getGreeting());
  }

  @Configuration(proxyBeanMethods = false)
  @ConditionalOnClass(ClientHttpRequestInterceptor.class)
  public static class RestClientConfiguration {

    @Bean
    public ClientHttpRequestInterceptor greeterRequestInterceptor(Greeter greeter) {
      return new GreeterRequestInterceptor(greeter);
    }
  }
}
