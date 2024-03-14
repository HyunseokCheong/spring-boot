package org.hyunseokcheong.orderservice.jpa;

import java.util.Date;

import org.springframework.data.annotation.CreatedDate;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "order")
public class OrderEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false)
	private String userId;
	
	@Column(nullable = false, unique = true)
	private String orderId;
	
	@Column(nullable = false, length = 120)
	private String productId;
	
	@Column(nullable = false)
	private Integer quantity;
	
	@Column(nullable = false)
	private Integer unitPrice;
	
	@Column(nullable = false)
	private Integer totalPrice;
	
	@CreatedDate
	private Date createdAt;
}
