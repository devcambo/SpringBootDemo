package com.devcambo.crudjpa.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "users")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class User extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long userId;

  @Column(length = 100, nullable = false)
  private String username;

  @Column(length = 100, nullable = false, unique = true)
  private String email;

  @Column(length = 100, nullable = false)
  private String password;
}
