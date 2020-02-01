package com.wimdeblauwe.examples.testcontainerscypressexample.todo.web;

import io.github.wimdeblauwe.testcontainers.cypress.CypressContainer;
import io.github.wimdeblauwe.testcontainers.cypress.CypressTestResults;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.testcontainers.Testcontainers;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import static org.junit.jupiter.api.Assertions.fail;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class TodoControllerCypressIntegrationTest {
    @LocalServerPort
    private int port;

    @Test
    void runCypressTests() throws InterruptedException, IOException, TimeoutException {

        Testcontainers.exposeHostPorts(port);

        try (CypressContainer container = new CypressContainer().withLocalServerPort(port)) {
            container.start();
            CypressTestResults testResults = container.getTestResults();

            if (testResults.getNumberOfFailingTests() > 0) {
                fail("There was a failure running the Cypress tests!\n\n" + testResults);
            }
        }
    }
}