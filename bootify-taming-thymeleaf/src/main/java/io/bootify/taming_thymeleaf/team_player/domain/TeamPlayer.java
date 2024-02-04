package io.bootify.taming_thymeleaf.team_player.domain;

import io.bootify.taming_thymeleaf.team.domain.Team;
import io.bootify.taming_thymeleaf.team_player.model.PlayerPosition;
import io.bootify.taming_thymeleaf.user.domain.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;


@Entity
public class TeamPlayer {

    @Id
    @Column(nullable = false, updatable = false)
    @SequenceGenerator(
            name = "primary_sequence",
            sequenceName = "primary_sequence",
            allocationSize = 1,
            initialValue = 10000
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "primary_sequence"
    )
    private Long id;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private PlayerPosition position;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "player_id", nullable = false, unique = true)
    private User player;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id", nullable = false)
    private Team team;

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public PlayerPosition getPosition() {
        return position;
    }

    public void setPosition(final PlayerPosition position) {
        this.position = position;
    }

    public User getPlayer() {
        return player;
    }

    public void setPlayer(final User player) {
        this.player = player;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(final Team team) {
        this.team = team;
    }

}
