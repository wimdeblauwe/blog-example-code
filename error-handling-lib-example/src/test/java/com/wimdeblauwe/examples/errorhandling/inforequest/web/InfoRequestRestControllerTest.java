package com.wimdeblauwe.examples.errorhandling.inforequest.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wimdeblauwe.examples.errorhandling.inforequest.InfoRequestNotFoundException;
import com.wimdeblauwe.examples.errorhandling.inforequest.InfoRequestService;
import com.wimdeblauwe.examples.errorhandling.supportagent.SupportAgentNotFoundException;
import com.wimdeblauwe.examples.errorhandling.supportagent.SupportAgentService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.nio.charset.StandardCharsets;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
class InfoRequestRestControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private InfoRequestService requestService;
    @MockBean
    private SupportAgentService supportAgentService;

    @Test
    void testGetInfoRequestWhenNotFoundException() throws Exception {
        long id = 1;
        when(requestService.getInfoRequest(id))
                .thenThrow(new InfoRequestNotFoundException(id));

        mockMvc.perform(get("/api/info-requests/{id}", id))
               .andExpect(status().isNotFound())
               .andDo(print());
    }

    @Test
    void testLinkAgentToRequestWhenRequestNotFound() throws Exception {
        long requestId = 1;
        long agentId = 345;
        when(requestService.getInfoRequest(requestId))
                .thenThrow(new InfoRequestNotFoundException(requestId));
        when(supportAgentService.getSupportAgent(agentId))
                .thenThrow(new SupportAgentNotFoundException(agentId));

        mockMvc.perform(put("/api/info-requests/{requestId}/link-to-agent/{agentId}", requestId, agentId))
               .andExpect(status().isNotFound())
               .andDo(print());
    }

    @Test
    void testCreateInfoRequestWithEmptyRequestBody() throws Exception {
        mockMvc.perform(post("/api/info-requests")
                                .characterEncoding(StandardCharsets.UTF_8.name())
                                .contentType(MediaType.APPLICATION_JSON))
               .andExpect(status().isBadRequest())
               .andDo(print());
    }

    @Test
    void testCreateInfoRequestWithInvalidRequestBody() throws Exception {
        mockMvc.perform(post("/api/info-requests")
                                .characterEncoding(StandardCharsets.UTF_8.name())
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(new CreateInfoRequestRequestBody())))
               .andExpect(status().isBadRequest())
               .andDo(print());
    }
}
