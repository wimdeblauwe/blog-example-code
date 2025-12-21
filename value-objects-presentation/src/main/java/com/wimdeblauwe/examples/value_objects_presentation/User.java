package com.wimdeblauwe.examples.value_objects_presentation;

import jakarta.persistence.*;

@Entity
@Table(name = "t_user")
public class User {

  @EmbeddedId
  @AttributeOverride(name = "value", column = @Column(name = "id"))
  private UserId id;

  private String name;

  private Email email;

  protected User() {
  }

  public User(UserId id,
              String name,
              Email email) {
    this.id = id;
    this.name = name;
    this.email = email;
  }

  public UserId getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public Email getEmail() {
    return email;
  }
}
