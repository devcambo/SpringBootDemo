package com.devcambo.backendapi.repository;

import com.devcambo.backendapi.entity.PwdResetToken;
import com.devcambo.backendapi.entity.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
  boolean existsByEmail(String email);
  Optional<User> findByEmail(String email);

  // TODO: improve performance sql later
  Optional<User> findByPasswordResetToken(PwdResetToken passwordResetToken);
}
