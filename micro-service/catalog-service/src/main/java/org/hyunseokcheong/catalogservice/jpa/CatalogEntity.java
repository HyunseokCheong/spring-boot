package org.hyunseokcheong.catalogservice.jpa;

import java.util.Date;

import org.springframework.data.annotation.CreatedDate;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "catalog")
public class CatalogEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false, length = 120, unique = true)
	private String productId;
	
	@Column(nullable = false)
	private String productName;
	
	@Column(nullable = false)
	private Integer stock;
	
	@Column(nullable = false)
	private Integer unitPrice;
	
	@CreatedDate
	private Date createdAt;
}
