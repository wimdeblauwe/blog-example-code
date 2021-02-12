package com.wimdeblauwe.examples.junit5testorder.user.web;

import com.wimdeblauwe.examples.junit5testorder.user.User;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import com.wimdeblauwe.examples.junit5testorder.user.UserRepository;

import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private UserRepository repository;

    @Test
    void test() throws Exception {
        Mockito.when(repository.findById(1L))
               .thenReturn(Optional.of(new User(1L, "Wim")));

        mockMvc.perform(get("/users/{id}", 1L))
               .andExpect(status().isOk());
    }
}
