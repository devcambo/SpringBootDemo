package com.devcambo.backendapi.entity;

import jakarta.persistence.*;
import java.time.Instant;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "password_reset_tokens")
public class PwdResetToken {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", nullable = false)
  private Integer id;

  @Column(name = "token", length = 128, nullable = false, unique = true)
  private String token;

  @Column(name = "token_expiry", nullable = false)
  private Instant tokenExpiry;
}
