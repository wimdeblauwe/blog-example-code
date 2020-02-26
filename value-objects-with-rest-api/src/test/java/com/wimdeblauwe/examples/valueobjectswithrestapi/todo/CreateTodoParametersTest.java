package com.wimdeblauwe.examples.valueobjectswithrestapi.todo;

import com.wimdeblauwe.examples.valueobjectswithrestapi.user.UserId;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.json.JsonContent;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

@JsonTest
class CreateTodoParametersTest {

    @Autowired
    private JacksonTester<CreateTodoParameters> tester;

    @Test
    void testSerialization() throws IOException {
        CreateTodoParameters parameters = new CreateTodoParameters(new UserId(3L),
                                                                   "Test Description");
        JsonContent<CreateTodoParameters> content = tester.write(parameters);
        assertThat(content).hasJsonPathNumberValue("userId", 3L);
        assertThat(content).hasJsonPathStringValue("description", "Test Description");
    }

    @Test
    void testDeserialization() throws IOException {
        CreateTodoParameters parameters = tester.parseObject("{\n" +
                                                                     "  \"userId\": 2,\n" +
                                                                     "  \"description\": \"Test Description\"\n" +
                                                                     "}");
        assertThat(parameters).isNotNull();
        assertThat(parameters.getUserId()).isNotNull().extracting(UserId::getId).isEqualTo(2L);
        assertThat(parameters.getDescription()).isEqualTo("Test Description");
    }
}