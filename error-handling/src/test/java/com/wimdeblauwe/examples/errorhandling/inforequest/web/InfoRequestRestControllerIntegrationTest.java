package com.wimdeblauwe.examples.errorhandling.inforequest.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wimdeblauwe.examples.errorhandling.inforequest.InfoRequestNotFoundException;
import com.wimdeblauwe.examples.errorhandling.inforequest.InfoRequestService;
import com.wimdeblauwe.examples.errorhandling.supportagent.SupportAgentNotFoundException;
import com.wimdeblauwe.examples.errorhandling.supportagent.SupportAgentService;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;

import static io.restassured.RestAssured.given;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class InfoRequestRestControllerIntegrationTest {

    @LocalServerPort
    int port;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private InfoRequestService requestService;
    @MockBean
    private SupportAgentService supportAgentService;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }

    @Test
    void testGetInfoRequestWhenNotFoundException() throws Exception {
        long id = 1;
        when(requestService.getInfoRequest(id))
                .thenThrow(new InfoRequestNotFoundException(id));

        given().get("/api/info-requests/{id}", id)
               .prettyPeek()
               .then()
               .statusCode(HttpStatus.NOT_FOUND.value());
    }

    @Test
    void testLinkAgentToRequestWhenRequestNotFound() throws Exception {
        long requestId = 1;
        long agentId = 345;
        when(requestService.getInfoRequest(requestId))
                .thenThrow(new InfoRequestNotFoundException(requestId));
        when(supportAgentService.getSupportAgent(agentId))
                .thenThrow(new SupportAgentNotFoundException(agentId));

        given().put("/api/info-requests/{requestId}/link-to-agent/{agentId}", requestId, agentId)
               .prettyPeek()
               .then()
               .statusCode(HttpStatus.NOT_FOUND.value());
    }

    @Test
    void testCreateInfoRequestWithEmptyRequestBody() throws Exception {
        given().post("/api/info-requests")
               .prettyPeek()
               .then()
               .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    void testCreateInfoRequestWithInvalidRequestBody() throws Exception {
        given().contentType(ContentType.JSON)
               .body(objectMapper.writeValueAsString(new CreateInfoRequestRequestBody()))
               .post("/api/info-requests")
               .prettyPeek()
               .then()
               .statusCode(HttpStatus.BAD_REQUEST.value());
    }
}
