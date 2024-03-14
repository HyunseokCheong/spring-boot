package org.hyunseokcheong.userservice.vo;

import lombok.Data;

@Data
public class ResponsePurchase {
	
	private String productId;
	private Integer quantity;
	private Integer unitPrice;
	private Integer totalPrice;
	
	private String purchaseId;
}
