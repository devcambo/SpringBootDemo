package com.devcambo.backendapi.repository;

import com.devcambo.backendapi.entity.PwdResetToken;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;

public interface PwdResetTokenRepository extends CrudRepository<PwdResetToken, Long> {
  Optional<PwdResetToken> findByToken(String token);
}
