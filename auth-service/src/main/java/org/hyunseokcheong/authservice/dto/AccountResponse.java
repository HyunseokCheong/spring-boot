package org.hyunseokcheong.authservice.dto;

import org.hyunseokcheong.authservice.entity.Account;

public record AccountResponse(
	Long accountId,
	String email
) {

	public static AccountResponse of(Account account) {
		return new AccountResponse(account.getAccountId(), account.getEmail());
	}
}
