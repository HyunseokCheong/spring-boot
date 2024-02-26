package org.hyunseokcheong.authservice.repository;

import org.hyunseokcheong.authservice.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {

	Boolean existsByEmail(String email);
}
