package io.bootify.taming_thymeleaf.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;


public class AuthenticationRequest {

    @NotNull
    @Size(max = 255)
    private String login;

    @NotNull
    @Size(max = 255)
    private String password;

    public String getLogin() {
        return login;
    }

    public void setLogin(final String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(final String password) {
        this.password = password;
    }

}
