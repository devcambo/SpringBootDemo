package com.devcambo.api.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.LinkedHashSet;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "roles")
public class Role extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "role_id", nullable = false)
  private Long roleId;

  @Size(max = 50)
  @NotNull
  @Column(name = "name", nullable = false, length = 50)
  private String name;

  @ManyToMany(mappedBy = "roles")
  private Set<User> users = new LinkedHashSet<>();
}
