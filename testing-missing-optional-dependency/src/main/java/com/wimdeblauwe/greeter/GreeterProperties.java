package com.wimdeblauwe.greeter;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "greeter")
public class GreeterProperties {

  /**
   * The greeting to use.
   */
  private String greeting = "Hello";

  public String getGreeting() {
    return greeting;
  }

  public void setGreeting(String greeting) {
    this.greeting = greeting;
  }
}
