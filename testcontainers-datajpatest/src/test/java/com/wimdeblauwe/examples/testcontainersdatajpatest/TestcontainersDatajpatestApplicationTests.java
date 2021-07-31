package com.wimdeblauwe.examples.testcontainersdatajpatest;

import com.wimdeblauwe.examples.testcontainersdatajpatest.atlete.Atlete;
import com.wimdeblauwe.examples.testcontainersdatajpatest.atlete.AtleteRepository;
import com.wimdeblauwe.examples.testcontainersdatajpatest.infrastructure.test.DatabaseBaseTest;
import com.wimdeblauwe.examples.testcontainersdatajpatest.team.Team;
import com.wimdeblauwe.examples.testcontainersdatajpatest.team.TeamRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class TestcontainersDatajpatestApplicationTests extends DatabaseBaseTest {

    @Autowired
    private AtleteRepository atleteRepository;
    @Autowired
    private TeamRepository teamRepository;

    @Test
    void contextLoads() {
        atleteRepository.save(new Atlete("Wout Van Aert", 0, 1, 0));
        assertThat(atleteRepository.count()).isEqualTo(1);

        teamRepository.save(new Team("Team Belgium"));
        assertThat(teamRepository.count()).isEqualTo(1);
    }

}
