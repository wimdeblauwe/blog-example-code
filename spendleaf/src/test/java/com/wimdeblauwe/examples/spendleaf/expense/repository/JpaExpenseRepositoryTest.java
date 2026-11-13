package com.wimdeblauwe.examples.spendleaf.expense.repository;

import com.wimdeblauwe.examples.spendleaf.expense.Amount;
import com.wimdeblauwe.examples.spendleaf.expense.Expense;
import com.wimdeblauwe.examples.spendleaf.expense.ExpenseCategory;
import com.wimdeblauwe.examples.spendleaf.expense.ExpenseId;
import com.wimdeblauwe.examples.spendleaf.expense.ExpenseNotFoundException;
import com.wimdeblauwe.examples.spendleaf.expense.ExpenseStatus;
import com.wimdeblauwe.examples.spendleaf.infrastructure.test.SpendleafDataJpaTest;
import com.wimdeblauwe.examples.spendleaf.project.Project;
import com.wimdeblauwe.examples.spendleaf.project.ProjectId;
import com.wimdeblauwe.examples.spendleaf.project.repository.ProjectRepository;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.jdbc.core.simple.JdbcClient;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Map;
import java.util.UUID;

import static com.wimdeblauwe.examples.spendleaf.expense.ExpenseMother.expense;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

@SpendleafDataJpaTest
class JpaExpenseRepositoryTest {

    @Autowired
    private JpaExpenseRepository repository;
    @Autowired
    private EntityManager entityManager;
    @Autowired
    private JdbcClient jdbcClient;
    @Autowired
    private ProjectRepository projectRepository;

    @Test
    void testSave() {
        ProjectId projectId = saveProject("Atlas");
        ExpenseId id = repository.nextId();
        repository.save(expense()
                                .id(id)
                                .description("Hotel Ghent, 2 nights")
                                .amount(Amount.ofEuros("238.00"))
                                .date(LocalDate.of(2026, 11, 10))
                                .category(ExpenseCategory.TRAVEL)
                                .project(projectId)
                                .billable(true)
                                .notes("Workshop at the client")
                                .build());
        entityManager.flush();
        entityManager.clear();

        Map<String, Object> row = jdbcClient.sql("SELECT * FROM expense").query().singleRow();
        assertThat(row.get("id")).isEqualTo(id.getId());
        assertThat(row.get("description")).isEqualTo("Hotel Ghent, 2 nights");
        assertThat(row.get("amount")).isEqualTo(new BigDecimal("238.00"));
        assertThat(row.get("date")).isEqualTo(java.sql.Date.valueOf(LocalDate.of(2026, 11, 10)));
        assertThat(row.get("category")).isEqualTo("TRAVEL");
        assertThat(row.get("project")).isEqualTo(projectId.getId());
        assertThat(row.get("billable")).isEqualTo(true);
        assertThat(row.get("notes")).isEqualTo("Workshop at the client");
        assertThat(row.get("status")).isEqualTo("DRAFT");
    }

    @Test
    void testGetById() {
        ExpenseId id = repository.nextId();
        repository.save(expense().id(id).amount(Amount.ofEuros("42.50")).build());
        entityManager.flush();
        entityManager.clear();

        Expense expense = repository.getById(id);
        assertThat(expense.getId()).isEqualTo(id);
        assertThat(expense.getAmount()).isEqualTo(Amount.ofEuros("42.50"));
        assertThat(expense.getStatus()).isEqualTo(ExpenseStatus.DRAFT);
    }

    @Test
    void testGetById_unknownId() {
        ExpenseId id = new ExpenseId(UUID.randomUUID());

        assertThatExceptionOfType(ExpenseNotFoundException.class)
                .isThrownBy(() -> repository.getById(id));
    }

    @Test
    void testFindAll_pagedAndSortedByAmount() {
        repository.save(expense().id(repository.nextId()).description("Taxi").amount(Amount.ofEuros("42.50")).build());
        repository.save(expense().id(repository.nextId()).description("Hotel").amount(Amount.ofEuros("238.00")).build());
        repository.save(expense().id(repository.nextId()).description("Lunch").amount(Amount.ofEuros("18.00")).build());
        entityManager.flush();
        entityManager.clear();

        Page<Expense> page = repository.findAll(PageRequest.of(0, 2, Sort.by("amount").descending()));

        assertThat(page.getTotalElements()).isEqualTo(3);
        assertThat(page.getTotalPages()).isEqualTo(2);
        assertThat(page.getContent())
                .extracting(Expense::getDescription)
                .containsExactly("Hotel", "Taxi");
    }

    @Test
    void testFindAll_sortedByProjectName() {
        // Saved in another order than their names, so sorting on the id would give another result
        ProjectId draco = saveProject("Draco");
        ProjectId atlas = saveProject("Atlas");
        ProjectId cygnus = saveProject("Cygnus");
        repository.save(expense().id(repository.nextId()).description("Taxi").project(cygnus).build());
        repository.save(expense().id(repository.nextId()).description("Hotel").project(draco).build());
        repository.save(expense().id(repository.nextId()).description("Lunch").project(null).build());
        repository.save(expense().id(repository.nextId()).description("Train").project(atlas).build());
        entityManager.flush();
        entityManager.clear();

        Page<Expense> page = repository.findAll(PageRequest.of(0, 10, Sort.by("project")));

        assertThat(page.getTotalElements()).isEqualTo(4);
        assertThat(page.getContent())
                .extracting(Expense::getDescription)
                .containsExactly("Train", "Taxi", "Hotel", "Lunch");
    }

    @Test
    void testFindAll_sortedByProjectNameDescending() {
        ProjectId atlas = saveProject("Atlas");
        ProjectId borealis = saveProject("Borealis");
        repository.save(expense().id(repository.nextId()).description("Train").project(atlas).build());
        repository.save(expense().id(repository.nextId()).description("Lunch").project(null).build());
        repository.save(expense().id(repository.nextId()).description("Hotel").project(borealis).build());
        entityManager.flush();
        entityManager.clear();

        Page<Expense> page = repository.findAll(PageRequest.of(0, 10, Sort.by("project").descending()));

        assertThat(page.getContent())
                .extracting(Expense::getDescription)
                .containsExactly("Lunch", "Hotel", "Train");
    }

    private ProjectId saveProject(String name) {
        ProjectId id = projectRepository.nextId();
        projectRepository.save(new Project(id, name));
        return id;
    }
}
