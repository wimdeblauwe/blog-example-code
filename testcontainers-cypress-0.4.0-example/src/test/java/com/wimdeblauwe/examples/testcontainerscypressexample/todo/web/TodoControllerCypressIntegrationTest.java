package com.wimdeblauwe.examples.testcontainerscypressexample.todo.web;

import io.github.wimdeblauwe.testcontainers.cypress.CypressContainer;
import io.github.wimdeblauwe.testcontainers.cypress.CypressTest;
import io.github.wimdeblauwe.testcontainers.cypress.CypressTestResults;
import io.github.wimdeblauwe.testcontainers.cypress.CypressTestSuite;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.DynamicContainer;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;
import org.junit.jupiter.params.ParameterizedTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.testcontainers.Testcontainers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeoutException;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class TodoControllerCypressIntegrationTest {
    @LocalServerPort
    private int port;

    @TestFactory
    List<DynamicContainer> runCypressTests() throws InterruptedException, IOException, TimeoutException {
        Testcontainers.exposeHostPorts(port);

        DynamicContainer chromeTests = DynamicContainer.dynamicContainer("Chrome", runCypressTestsOnBrowser("chrome"));
        DynamicContainer firefoxTests = DynamicContainer.dynamicContainer("Firefox", runCypressTestsOnBrowser("firefox"));
        return List.of(chromeTests, firefoxTests);
    }

    @NotNull
    private List<DynamicContainer> runCypressTestsOnBrowser(String browser) throws InterruptedException, TimeoutException, IOException {
        try (CypressContainer container = new CypressContainer().withLocalServerPort(port)
                                                                .withBrowser(browser)
                                                                .withSpec("cypress/integration/todos.spec.js")) {
            container.start();
            CypressTestResults testResults = container.getTestResults();

            return convertToJUnitDynamicTests(testResults);
        }
    }

    @NotNull
    private List<DynamicContainer> convertToJUnitDynamicTests(CypressTestResults testResults) {
        List<DynamicContainer> dynamicContainers = new ArrayList<>();
        List<CypressTestSuite> suites = testResults.getSuites();
        for (CypressTestSuite suite : suites) {
            createContainerFromSuite(dynamicContainers, suite);
        }
        return dynamicContainers;
    }

    private void createContainerFromSuite(List<DynamicContainer> dynamicContainers, CypressTestSuite suite) {
        List<DynamicTest> dynamicTests = new ArrayList<>();
        for (CypressTest test : suite.getTests()) {
            dynamicTests.add(DynamicTest.dynamicTest(test.getDescription(), () -> assertTrue(test.isSuccess())));
        }
        dynamicContainers.add(DynamicContainer.dynamicContainer(suite.getTitle(), dynamicTests));
    }
}