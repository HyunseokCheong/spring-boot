package org.hyunseokcheong.purchaseservice.dto;

import lombok.Data;

@Data
public class PurchaseDto {
	
	private String productId;
	private Integer quantity;
	private Integer unitPrice;
	private Integer totalPrice;
	
	private String purchaseId;
	private String userId;
}
