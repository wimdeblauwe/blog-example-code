package com.wimdeblauwe.examples.laravelintermediatetasklist;

import com.wimdeblauwe.examples.laravelintermediatetasklist.user.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Profile("seed-db")
@Component
public class SeedDatabaseCommandRunner implements CommandLineRunner {
    private final UserService userService;

    public SeedDatabaseCommandRunner(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void run(String... args) throws Exception {
        userService.createUser("User", "user", "password");
    }
}
