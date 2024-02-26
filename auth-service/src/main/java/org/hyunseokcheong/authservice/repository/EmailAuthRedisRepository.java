package org.hyunseokcheong.authservice.repository;

import java.util.Optional;

import org.hyunseokcheong.authservice.entity.EmailAuth;
import org.springframework.data.repository.CrudRepository;

public interface EmailAuthRedisRepository extends CrudRepository<EmailAuth, String> {

	Optional<EmailAuth> findByEmail(String email);
}
