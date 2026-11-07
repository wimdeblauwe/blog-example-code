package com.wimdeblauwe.examples.spendleaf.infrastructure.demo;

import com.wimdeblauwe.examples.spendleaf.expense.Amount;
import com.wimdeblauwe.examples.spendleaf.expense.Expense;
import com.wimdeblauwe.examples.spendleaf.expense.ExpenseCategory;
import com.wimdeblauwe.examples.spendleaf.expense.ExpenseStatus;
import com.wimdeblauwe.examples.spendleaf.expense.repository.ExpenseRepository;
import net.datafaker.Faker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Random;

/**
 * Fills an empty database with realistic looking expenses.
 */
@Component
@Profile("init")
class DemoDataSeeder implements ApplicationRunner {
    private static final Logger LOGGER = LoggerFactory.getLogger(DemoDataSeeder.class);
    private static final int NUMBER_OF_EXPENSES = 120;
    private static final String[] PROJECTS = {"Atlas", "Borealis", "Cygnus", "Draco"};

    private final ExpenseRepository repository;
    // A fixed seed gives the same demo data on every start
    private final Faker faker = new Faker(new Random(42));

    DemoDataSeeder(ExpenseRepository repository) {
        this.repository = repository;
    }

    @Override
    public void run(ApplicationArguments args) {
        if (!repository.findAll(PageRequest.of(0, 1)).isEmpty()) {
            return;
        }

        LOGGER.info("Seeding {} demo expenses", NUMBER_OF_EXPENSES);
        for (int i = 0; i < NUMBER_OF_EXPENSES; i++) {
            repository.save(createExpense());
        }
    }

    private Expense createExpense() {
        ExpenseCategory category = faker.options().option(ExpenseCategory.class);
        boolean forProject = category != ExpenseCategory.TRAINING && faker.bool().bool();
        Expense expense = new Expense(repository.nextId(),
                                      description(category),
                                      amount(category),
                                      LocalDate.now().minusDays(faker.number().numberBetween(0, 180)),
                                      category,
                                      forProject ? faker.options().option(PROJECTS) : null,
                                      forProject && faker.bool().bool(),
                                      faker.number().numberBetween(0, 4) == 0 ? faker.lorem().sentence() : null);
        expense.setStatus(status());
        return expense;
    }

    private String description(ExpenseCategory category) {
        return switch (category) {
            case TRAVEL -> faker.options().option(
                    "Hotel %s, %d nights".formatted(faker.address().city(), faker.number().numberBetween(1, 5)),
                    "Train to " + faker.address().city(),
                    "Taxi to the airport",
                    "Parking at the client");
            case MEALS -> faker.options().option("Lunch at ", "Dinner at ", "Team lunch at ")
                          + faker.restaurant().name();
            case TRAINING -> faker.options().option(
                    "Book: " + faker.book().title(),
                    "Conference ticket",
                    "Online course");
            case EQUIPMENT -> faker.commerce().productName();
            case OTHER -> faker.options().option("Office supplies", "Phone subscription", "Courier");
        };
    }

    private Amount amount(ExpenseCategory category) {
        int max = switch (category) {
            case TRAVEL -> 600;
            case MEALS -> 200;
            case TRAINING -> 900;
            case EQUIPMENT -> 400;
            case OTHER -> 80;
        };
        return new Amount(BigDecimal.valueOf(faker.number().randomDouble(2, 5, max)));
    }

    private ExpenseStatus status() {
        int percentage = faker.number().numberBetween(0, 100);
        if (percentage < 15) {
            return ExpenseStatus.DRAFT;
        } else if (percentage < 35) {
            return ExpenseStatus.SUBMITTED;
        } else if (percentage < 90) {
            return ExpenseStatus.APPROVED;
        } else {
            return ExpenseStatus.REJECTED;
        }
    }
}
