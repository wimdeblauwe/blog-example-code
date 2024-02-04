package io.bootify.taming_thymeleaf.team.service;

import io.bootify.taming_thymeleaf.model.SimplePage;
import io.bootify.taming_thymeleaf.team.domain.Team;
import io.bootify.taming_thymeleaf.team.model.TeamDTO;
import io.bootify.taming_thymeleaf.team.repos.TeamRepository;
import io.bootify.taming_thymeleaf.team_player.domain.TeamPlayer;
import io.bootify.taming_thymeleaf.team_player.repos.TeamPlayerRepository;
import io.bootify.taming_thymeleaf.user.domain.User;
import io.bootify.taming_thymeleaf.user.repos.UserRepository;
import io.bootify.taming_thymeleaf.util.NotFoundException;
import io.bootify.taming_thymeleaf.util.WebUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


@Service
public class TeamService {

    private final TeamRepository teamRepository;
    private final UserRepository userRepository;
    private final TeamPlayerRepository teamPlayerRepository;

    public TeamService(final TeamRepository teamRepository, final UserRepository userRepository,
            final TeamPlayerRepository teamPlayerRepository) {
        this.teamRepository = teamRepository;
        this.userRepository = userRepository;
        this.teamPlayerRepository = teamPlayerRepository;
    }

    public SimplePage<TeamDTO> findAll(final String filter, final Pageable pageable) {
        Page<Team> page;
        if (filter != null) {
            Long longFilter = null;
            try {
                longFilter = Long.parseLong(filter);
            } catch (final NumberFormatException numberFormatException) {
                // keep null - no parseable input
            }
            page = teamRepository.findAllById(longFilter, pageable);
        } else {
            page = teamRepository.findAll(pageable);
        }
        return new SimplePage<>(page.getContent()
                .stream()
                .map(team -> mapToDTO(team, new TeamDTO()))
                .toList(),
                page.getTotalElements(), pageable);
    }

    public TeamDTO get(final Long id) {
        return teamRepository.findById(id)
                .map(team -> mapToDTO(team, new TeamDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final TeamDTO teamDTO) {
        final Team team = new Team();
        mapToEntity(teamDTO, team);
        return teamRepository.save(team).getId();
    }

    public void update(final Long id, final TeamDTO teamDTO) {
        final Team team = teamRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(teamDTO, team);
        teamRepository.save(team);
    }

    public void delete(final Long id) {
        teamRepository.deleteById(id);
    }

    private TeamDTO mapToDTO(final Team team, final TeamDTO teamDTO) {
        teamDTO.setId(team.getId());
        teamDTO.setName(team.getName());
        teamDTO.setCoach(team.getCoach() == null ? null : team.getCoach().getId());
        return teamDTO;
    }

    private Team mapToEntity(final TeamDTO teamDTO, final Team team) {
        team.setName(teamDTO.getName());
        final User coach = teamDTO.getCoach() == null ? null : userRepository.findById(teamDTO.getCoach())
                .orElseThrow(() -> new NotFoundException("coach not found"));
        team.setCoach(coach);
        return team;
    }

    public String getReferencedWarning(final Long id) {
        final Team team = teamRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        final TeamPlayer teamTeamPlayer = teamPlayerRepository.findFirstByTeam(team);
        if (teamTeamPlayer != null) {
            return WebUtils.getMessage("team.teamPlayer.team.referenced", teamTeamPlayer.getId());
        }
        return null;
    }

}
