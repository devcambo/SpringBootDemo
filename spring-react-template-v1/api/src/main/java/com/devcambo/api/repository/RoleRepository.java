package com.devcambo.api.repository;

import com.devcambo.api.entity.Role;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;

public interface RoleRepository extends CrudRepository<Role, Long> {
  Optional<Role> findByName(String name);
}
