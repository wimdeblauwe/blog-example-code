package com.wimdeblauwe.examples.valueobjectswithrestapi.user.web;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void testGetUserInfo() throws Exception {
        mockMvc.perform(get("/api/users/{id}", 1L))
               .andExpect(status().isOk());
    }
}