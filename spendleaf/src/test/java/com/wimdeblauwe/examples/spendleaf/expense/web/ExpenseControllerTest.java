package com.wimdeblauwe.examples.spendleaf.expense.web;

import com.wimdeblauwe.examples.spendleaf.expense.Amount;
import com.wimdeblauwe.examples.spendleaf.expense.ExpenseCategory;
import com.wimdeblauwe.examples.spendleaf.expense.repository.ExpenseRepository;
import com.wimdeblauwe.examples.spendleaf.expense.repository.InMemoryExpenseRepository;
import com.wimdeblauwe.examples.spendleaf.expense.usecase.GetExpenses;
import com.wimdeblauwe.examples.spendleaf.project.Project;
import com.wimdeblauwe.examples.spendleaf.project.ProjectId;
import com.wimdeblauwe.examples.spendleaf.project.repository.InMemoryProjectRepository;
import com.wimdeblauwe.examples.spendleaf.project.repository.ProjectRepository;
import com.wimdeblauwe.examples.spendleaf.project.usecase.GetProjects;
import org.htmlunit.WebClient;
import org.htmlunit.html.HtmlPage;
import org.htmlunit.html.HtmlTable;
import org.htmlunit.html.HtmlTableRow;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Bean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;

import static com.wimdeblauwe.examples.spendleaf.expense.ExpenseMother.expense;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@WebMvcTest(ExpenseController.class)
class ExpenseControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private WebClient webClient;
    @Autowired
    private ExpenseRepository repository;
    @Autowired
    private ProjectRepository projectRepository;

    @BeforeEach
    void setUp() {
        // The in-memory repository is shared by all tests of this class
        repository.deleteAll();
        projectRepository.deleteAll();
        webClient.getOptions().setCssEnabled(false);
        webClient.getOptions().setJavaScriptEnabled(false);
    }

    @Test
    void testIndex() throws Exception {
        repository.save(expense().id(repository.nextId())
                                .description("Hotel Ghent, 2 nights")
                                .date(LocalDate.of(2026, 11, 10))
                                .category(ExpenseCategory.TRAVEL)
                                .project(null)
                                .amount(Amount.ofEuros("238"))
                                .build());

        mockMvc.perform(get("/expenses"))
                .andExpect(status().isOk())
                .andExpect(view().name("expenses/index"))
                .andExpect(model().attributeExists("page"));

        HtmlPage page = webClient.getPage("/expenses");
        HtmlTable table = page.getHtmlElementById("expenses-table");
        HtmlTableRow row = table.getRow(1);
        assertThat(row.getCell(0).asNormalizedText()).isEqualTo("Hotel Ghent, 2 nights");
        assertThat(row.getCell(1).asNormalizedText()).isEqualTo("10 Nov 2026");
        assertThat(row.getCell(2).asNormalizedText()).isEqualTo("Travel");
        assertThat(row.getCell(3).asNormalizedText()).isEqualTo("—");
        assertThat(row.getCell(4).asNormalizedText()).isEqualTo("€238.00");
        assertThat(row.getCell(5).asNormalizedText()).isEqualTo("Draft");
        assertThat(page.getElementById("no-expenses")).isNull();
    }

    @Test
    void testIndex_showsProjectName() throws Exception {
        ProjectId projectId = projectRepository.nextId();
        projectRepository.save(new Project(projectId, "Atlas"));
        repository.save(expense().id(repository.nextId()).project(projectId).build());

        HtmlPage page = webClient.getPage("/expenses");

        HtmlTable table = page.getHtmlElementById("expenses-table");
        assertThat(table.getRow(1).getCell(3).asNormalizedText()).isEqualTo("Atlas");
    }

    @Test
    void testIndex_sortedByProjectName() throws Exception {
        ProjectId borealis = projectRepository.nextId();
        projectRepository.save(new Project(borealis, "Borealis"));
        ProjectId atlas = projectRepository.nextId();
        projectRepository.save(new Project(atlas, "Atlas"));
        repository.save(expense().id(repository.nextId()).description("Hotel").project(borealis).build());
        repository.save(expense().id(repository.nextId()).description("Lunch").project(null).build());
        repository.save(expense().id(repository.nextId()).description("Train").project(atlas).build());

        HtmlPage page = webClient.getPage("/expenses?sort=project");

        assertThat(descriptions(page)).containsExactly("Train", "Hotel", "Lunch");
    }

    @Test
    void testIndex_noExpenses() throws Exception {
        HtmlPage page = webClient.getPage("/expenses");

        assertThat(page.getElementById("no-expenses")).isNotNull();
        assertThat(page.getElementById("expenses-table")).isNull();
    }

    @Test
    void testIndex_sortedByDateDescendingByDefault() throws Exception {
        saveExpense("Oldest", LocalDate.of(2026, 9, 1), "10.00");
        saveExpense("Newest", LocalDate.of(2026, 11, 1), "20.00");
        saveExpense("Middle", LocalDate.of(2026, 10, 1), "30.00");

        HtmlPage page = webClient.getPage("/expenses");

        assertThat(descriptions(page)).containsExactly("Newest", "Middle", "Oldest");
    }

    @Test
    void testIndex_sortedByAmount() throws Exception {
        saveExpense("Medium", LocalDate.of(2026, 9, 1), "42.50");
        saveExpense("Expensive", LocalDate.of(2026, 11, 1), "238.00");
        saveExpense("Cheap", LocalDate.of(2026, 10, 1), "18.00");

        HtmlPage page = webClient.getPage("/expenses?sort=amount,desc");

        assertThat(descriptions(page)).containsExactly("Expensive", "Medium", "Cheap");
    }

    @Test
    void testIndex_pagedBy10ByDefault() throws Exception {
        for (int i = 1; i <= 12; i++) {
            saveExpense("Expense " + i, LocalDate.of(2026, 11, i), "10.00");
        }

        assertThat(descriptions(webClient.getPage("/expenses"))).hasSize(10);
        assertThat(descriptions(webClient.getPage("/expenses?page=1")))
                .containsExactly("Expense 2", "Expense 1");
    }

    @Test
    void testIndex_invalidSortRedirectsWithoutSortAndPage() throws Exception {
        mockMvc.perform(get("/expenses?sort=password&size=20&page=3"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("http://localhost/expenses?size=20"));
    }

    @Test
    void testIndex_invalidSortWithValidSortRedirects() throws Exception {
        mockMvc.perform(get("/expenses?sort=amount,desc&sort=password"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("http://localhost/expenses"));
    }

    private void saveExpense(String description, LocalDate date, String amount) {
        repository.save(expense().id(repository.nextId())
                                .description(description)
                                .date(date)
                                .amount(Amount.ofEuros(amount))
                                .build());
    }

    private static List<String> descriptions(HtmlPage page) {
        HtmlTable table = page.getHtmlElementById("expenses-table");
        return table.getBodies().getFirst().getRows().stream()
                .map(row -> row.getCell(0).asNormalizedText())
                .toList();
    }

    @TestConfiguration
    static class TestConfig {
        @Bean
        ProjectRepository projectRepository() {
            return new InMemoryProjectRepository();
        }

        @Bean
        ExpenseRepository expenseRepository(ProjectRepository projectRepository) {
            return new InMemoryExpenseRepository(projectRepository);
        }

        @Bean
        GetExpenses getExpenses(ExpenseRepository expenseRepository) {
            return new GetExpenses(expenseRepository);
        }

        @Bean
        GetProjects getProjects(ProjectRepository projectRepository) {
            return new GetProjects(projectRepository);
        }
    }
}
