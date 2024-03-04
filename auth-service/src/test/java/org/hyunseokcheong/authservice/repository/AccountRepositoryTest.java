package org.hyunseokcheong.authservice.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.hyunseokcheong.authservice.entity.Account;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class AccountRepositoryTest {

	@Autowired
	private AccountRepository accountRepository;

	@BeforeEach
	void setUp() {
		accountRepository.deleteAll();
	}

	@Test
	void saveTest() {
		String email = "hyunseokcheong@gmail.com";
		String password = "Password1234@@";
		Account account = new Account(email, password);
		accountRepository.save(account);

		List<Account> allAccount = accountRepository.findAll();
		assertThat(allAccount.size()).isEqualTo(1);
	}

	@Test
	void existsByEmailTest() {
		String email = "hyunseokcheong@gmail.com";
		String password = "Password1234@@";
		Account account = new Account(email, password);

		assertThat(accountRepository.existsByEmail(email)).isFalse();

		accountRepository.save(account);

		assertThat(accountRepository.existsByEmail(email)).isTrue();
	}

	@Test
	void findByEmailTest() {
		String email = "hyunseokcheong@gmail.com";
		String password = "Password1234@@";
		Account account = new Account(email, password);
		accountRepository.save(account);

		Account findAccount = accountRepository.findByEmail(email).orElse(null);

		assertThat(findAccount).isNotNull();
		assertThat(findAccount.getEmail()).isEqualTo(email);
		assertThat(findAccount.getPassword()).isEqualTo(password);
	}
}
