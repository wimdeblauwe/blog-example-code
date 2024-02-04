package io.bootify.taming_thymeleaf.team.repos;

import io.bootify.taming_thymeleaf.team.domain.Team;
import io.bootify.taming_thymeleaf.user.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


public interface TeamRepository extends JpaRepository<Team, Long> {

    Page<Team> findAllById(Long id, Pageable pageable);

    Team findFirstByCoach(User user);

}
