package org.hyunseokcheong.authservice.repository;

import java.util.Optional;

import org.hyunseokcheong.authservice.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {

	Boolean existsByEmail(String email);

	Optional<Account> findByEmail(String email);
}
