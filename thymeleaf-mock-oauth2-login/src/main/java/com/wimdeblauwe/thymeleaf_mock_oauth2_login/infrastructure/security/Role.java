package com.wimdeblauwe.thymeleaf_mock_oauth2_login.infrastructure.security;

public enum Role {
  USER(Constants.USER),
  ADMIN(Constants.ADMIN);

  private final String keycloakRole;

  Role(String keycloakRole) {
    this.keycloakRole = keycloakRole;
  }

  public String getKeycloakRole() {
    return keycloakRole;
  }

  public static class Constants {

    public static final String USER = "USER";
    public static final String ADMIN = "ADMIN";

    private Constants() {
    }
  }
}
