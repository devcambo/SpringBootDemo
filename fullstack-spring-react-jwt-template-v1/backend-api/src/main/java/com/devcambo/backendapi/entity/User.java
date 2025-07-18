package com.devcambo.backendapi.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "users")
public class User extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", nullable = false)
  private Long id;

  @Column(name = "username", length = 100, nullable = false)
  private String username;

  @Column(name = "email", length = 100, nullable = false, unique = true)
  private String email;

  @Column(name = "profile_picture", length = 100)
  private String profilePicture;

  @Column(name = "password", length = 100, nullable = false)
  private String password;

  @Column(name = "roles", length = 30, nullable = false)
  private String roles;

  @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
  @JoinColumn(name = "pwd_reset_token_id", referencedColumnName = "id")
  private PwdResetToken passwordResetToken;
}
