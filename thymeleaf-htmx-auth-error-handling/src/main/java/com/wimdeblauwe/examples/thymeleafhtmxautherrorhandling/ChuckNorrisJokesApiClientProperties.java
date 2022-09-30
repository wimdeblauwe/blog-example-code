package com.wimdeblauwe.examples.thymeleafhtmxautherrorhandling;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.time.Duration;

@ConfigurationProperties(prefix = "chuck-norris-api-client")
public class ChuckNorrisJokesApiClientProperties {
    private Duration connectionTimeout = Duration.ofSeconds(10);

    private Duration readWriteTimeout = Duration.ofSeconds(10);


    private String baseUrl;

    public Duration getConnectionTimeout() {
        return connectionTimeout;
    }

    public void setConnectionTimeout(Duration connectionTimeout) {
        this.connectionTimeout = connectionTimeout;
    }

    public Duration getReadWriteTimeout() {
        return readWriteTimeout;
    }

    public void setReadWriteTimeout(Duration readWriteTimeout) {
        this.readWriteTimeout = readWriteTimeout;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }
}
