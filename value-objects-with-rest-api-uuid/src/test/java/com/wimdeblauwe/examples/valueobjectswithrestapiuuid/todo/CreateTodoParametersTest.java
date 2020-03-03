package com.wimdeblauwe.examples.valueobjectswithrestapiuuid.todo;

import com.wimdeblauwe.examples.valueobjectswithrestapiuuid.user.UserId;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.json.JsonContent;

import java.io.IOException;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@JsonTest
class CreateTodoParametersTest {

    @Autowired
    private JacksonTester<CreateTodoParameters> tester;

    @Test
    void testSerialization() throws IOException {
        UUID uuid = UUID.randomUUID();
        CreateTodoParameters parameters = new CreateTodoParameters(new UserId(uuid),
                                                                   "Test Description");
        JsonContent<CreateTodoParameters> content = tester.write(parameters);
        assertThat(content).hasJsonPathStringValue("userId", uuid.toString());
        assertThat(content).hasJsonPathStringValue("description", "Test Description");
    }

    @Test
    void testDeserialization() throws IOException {
        CreateTodoParameters parameters = tester.parseObject("{\n" +
                                                                     "  \"userId\": \"a434e065-6bc6-490e-9e26-ea1b348b3877\",\n" +
                                                                     "  \"description\": \"Test Description\"\n" +
                                                                     "}");
        assertThat(parameters).isNotNull();
        assertThat(parameters.getUserId()).isNotNull()
                                          .extracting(UserId::getId)
                                          .isEqualTo(UUID.fromString("a434e065-6bc6-490e-9e26-ea1b348b3877"));
        assertThat(parameters.getDescription()).isEqualTo("Test Description");
    }
}