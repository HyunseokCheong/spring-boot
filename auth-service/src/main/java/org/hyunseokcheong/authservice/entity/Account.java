package org.hyunseokcheong.authservice.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Account {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long accountId;

	@Column(nullable = false)
	private String email;

	@Column(nullable = false)
	private String password;

	public Account(String email, String password) {
		this.email = email;
		this.password = password;
	}
}
