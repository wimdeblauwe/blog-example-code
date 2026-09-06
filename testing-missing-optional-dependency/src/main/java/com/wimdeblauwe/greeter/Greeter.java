package com.wimdeblauwe.greeter;

public class Greeter {

  private final String greeting;

  public Greeter(String greeting) {
    this.greeting = greeting;
  }

  public String greet(String name) {
    return "%s, %s!".formatted(greeting, name);
  }
}
