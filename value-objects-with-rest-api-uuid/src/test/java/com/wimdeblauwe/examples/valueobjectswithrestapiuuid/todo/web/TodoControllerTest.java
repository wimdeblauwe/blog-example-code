package com.wimdeblauwe.examples.valueobjectswithrestapiuuid.todo.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wimdeblauwe.examples.valueobjectswithrestapiuuid.todo.CreateTodoParameters;
import com.wimdeblauwe.examples.valueobjectswithrestapiuuid.user.UserId;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
class TodoControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testAddTodo() throws Exception {
        String content = objectMapper.writeValueAsString(new CreateTodoParameters(new UserId(UUID.randomUUID()), "Item 1"));
        System.out.println("content = " + content);
        mockMvc.perform(post("/api/todos")
                                .content(content)
                                .contentType(MediaType.APPLICATION_JSON))
               .andExpect(status().isCreated());
    }
}