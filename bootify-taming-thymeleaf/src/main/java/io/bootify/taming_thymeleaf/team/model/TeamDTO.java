package io.bootify.taming_thymeleaf.team.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;


public class TeamDTO {

    private Long id;

    @NotNull
    @Size(max = 255)
    private String name;

    @NotNull
    private Long coach;

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public Long getCoach() {
        return coach;
    }

    public void setCoach(final Long coach) {
        this.coach = coach;
    }

}
