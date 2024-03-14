package org.hyunseokcheong.purchaseservice.jpa;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "purchase")
public class PurchaseEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false)
	private String userId;
	
	@Column(nullable = false, unique = true)
	private String purchaseId;
	
	@Column(nullable = false, length = 120)
	private String productId;
	
	@Column(nullable = false)
	private Integer quantity;
	
	@Column(nullable = false)
	private Integer unitPrice;
	
	@Column(nullable = false)
	private Integer totalPrice;
}
