package io.bootify.taming_thymeleaf.team_player.repos;

import io.bootify.taming_thymeleaf.team.domain.Team;
import io.bootify.taming_thymeleaf.team_player.domain.TeamPlayer;
import io.bootify.taming_thymeleaf.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;


public interface TeamPlayerRepository extends JpaRepository<TeamPlayer, Long> {

    TeamPlayer findFirstByPlayer(User user);

    TeamPlayer findFirstByTeam(Team team);

}
