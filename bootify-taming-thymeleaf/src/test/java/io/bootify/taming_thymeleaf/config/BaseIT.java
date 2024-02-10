package io.bootify.taming_thymeleaf.config;

import io.bootify.taming_thymeleaf.TamingThymeleafApplication;
import io.bootify.taming_thymeleaf.team.repos.TeamRepository;
import io.bootify.taming_thymeleaf.team_player.repos.TeamPlayerRepository;
import io.bootify.taming_thymeleaf.user.repos.UserRepository;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlMergeMode;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.util.StreamUtils;
import org.testcontainers.containers.PostgreSQLContainer;


/**
 * Abstract base class to be extended by every IT test. Starts the Spring Boot context with a
 * Datasource connected to the Testcontainers Docker instance. The instance is reused for all tests,
 * with all data wiped out before each test.
 */
@SpringBootTest(
        classes = TamingThymeleafApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
@AutoConfigureMockMvc
@ActiveProfiles("it")
@Sql({"/data/clearAll.sql", "/data/userData.sql"})
@SqlMergeMode(SqlMergeMode.MergeMode.MERGE)
public abstract class BaseIT {

    @ServiceConnection
    private static final PostgreSQLContainer postgreSQLContainer = new PostgreSQLContainer("postgres:16.1");
    private static MockHttpSession authenticatedSession = null;

    static {
        postgreSQLContainer.withReuse(true)
                .start();
    }

    @Autowired
    public MockMvc mockMvc;

    @Autowired
    public TeamPlayerRepository teamPlayerRepository;

    @Autowired
    public TeamRepository teamRepository;

    @Autowired
    public UserRepository userRepository;

    public String readResource(final String resourceName) {
        try {
            return StreamUtils.copyToString(getClass().getResourceAsStream(resourceName), StandardCharsets.UTF_8);
        } catch (final IOException io) {
            throw new UncheckedIOException(io);
        }
    }

    public MockHttpSession authenticatedSession() throws Exception {
        if (authenticatedSession == null) {
            final MvcResult mvcResult = mockMvc.perform(
                    SecurityMockMvcRequestBuilders.formLogin().user("email", "bootify").password("password", "Bootify!")).andReturn();
            authenticatedSession = ((MockHttpSession)mvcResult.getRequest().getSession(false));
        }
        return authenticatedSession;
    }

}
