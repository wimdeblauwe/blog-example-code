package com.wimdeblauwe.examples.value_objects_presentation;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.json.JsonContent;

import java.io.IOException;
import java.util.UUID;

@JsonTest
class UserTest {

  @Autowired
  private JacksonTester<User> tester;

  @Test
  void testSerilaize() throws IOException {
    JsonContent<User> content = tester.write(new User(new UserId(UUID.randomUUID()),
                                                      "John Doe",
                                                      Email.of("john.doe@company.com")));
    System.out.println(content.getJson());
  }
}