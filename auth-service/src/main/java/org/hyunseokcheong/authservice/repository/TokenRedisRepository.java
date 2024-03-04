package org.hyunseokcheong.authservice.repository;

import java.util.List;
import java.util.Optional;

import org.hyunseokcheong.authservice.entity.Token;
import org.springframework.data.repository.CrudRepository;

public interface TokenRedisRepository extends CrudRepository<Token, String> {

	Optional<Token> findByAccessToken(String accessToken);

	List<Token> findAllByAccountId(Long accountId);
}
