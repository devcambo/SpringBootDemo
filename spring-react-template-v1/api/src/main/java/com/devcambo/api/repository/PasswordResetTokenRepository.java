package com.devcambo.api.repository;

import com.devcambo.api.entity.PasswordResetToken;
import com.devcambo.api.entity.User;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;

public interface PasswordResetTokenRepository
  extends CrudRepository<PasswordResetToken, Long> {
  Optional<PasswordResetToken> findByToken(String token);
  void deleteByUser(User user);
}
